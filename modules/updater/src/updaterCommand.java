import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.LanguageLoader;
import fabaindaiz.modulator.core.modules.IModule;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class updaterCommand implements CommandExecutor {
    private final Modulator plugin;
    private final IModule module;
    private final LanguageLoader lang;
    private final String key = "updater.command";
    private boolean enabled;
    private Map<String, Object> update;

    protected updaterCommand(Modulator modulator, IModule module) {
        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLanguageLoader();
        this.enabled = module.getConfiguration().getBoolean("updater.enable");
        try {
            new BukkitRunnable() {
                @Override
                public void run() {
                    loadConfig();
                }
            }.runTaskAsynchronously(plugin);
        } catch (Exception e) {
            loadConfig();
        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!this.enabled) {
            sender.sendMessage(lang.get(key, "disable1"));
            return true;
        }
        if (!sender.hasPermission("modulator.updater")) {
            sender.sendMessage(lang.get("error.noper"));
            return true;
        }

        switch (args.length) {
            case 0:
                sender.sendMessage(lang.get(key, "info1"));
                sender.sendMessage(lang.get(key, "info2"));
                return true;
            case 1:
                switch (args[0]) {
                    case "help":
                        this.help(sender);
                        return true;
                    case "update":
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                update(sender);
                            }
                        }.runTaskAsynchronously(plugin);
                        return true;
                    default:
                        sender.sendMessage(lang.get(key, "error1"));
                        return true;
                }
            default:
                sender.sendMessage(lang.get(key, "error1"));
                return true;
        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage(lang.get(key, "help1"));
        sender.sendMessage(lang.get(key, "help2"));
        sender.sendMessage(lang.get(key, "help3"));
    }

    public void update(CommandSender sender) {
        sender.sendMessage(lang.get(key, "update1"));
        sender.sendMessage(lang.get(key, "update2"));
        for (Plugin tPlugin : Bukkit.getPluginManager().getPlugins()) {
            String verFile = tPlugin.getDescription().getVersion();
            String code = String.valueOf(update.get(tPlugin.getName()));
            if (code.equals("spigotId")) {
                continue;
            }
            try {
                URLConnection con = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + code).openConnection();
                String verSpigot = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
                if (!(verSpigot.contains(verFile) || verFile.contains(verSpigot))) {
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(lang.get(key, "update3"));
                    buffer.append(tPlugin.getName());
                    buffer.append(" (");
                    buffer.append(verFile);
                    buffer.append(" -> ");
                    buffer.append(verSpigot);
                    buffer.append(" ) ");
                    buffer.append(lang.get(key, "update4"));

                    if (sender instanceof Player) {
                        BaseComponent message1 = new TextComponent(buffer.toString());
                        message1.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/" + code));
                        sender.sendMessage(message1);
                    } else {
                        sender.sendMessage(buffer.toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadConfig() {
        File file = new File(plugin.getDataFolder(), "config/updater/update.yml");
        Yaml yaml = new Yaml();
        try {
            if (!file.exists()) {
                update = new HashMap<>();
                for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                    String id = updaterUtil.getId(plugin.getName());
                    update.put(plugin.getName(), id);
                }
            } else {
                InputStream inputStream = new FileInputStream(file);
                update = yaml.load(inputStream);

                for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                    if (!update.containsKey(plugin.getName())) {
                        String id = updaterUtil.getId(plugin.getName());
                        update.put(plugin.getName(), id);
                    }
                }
            }
            FileWriter writer = new FileWriter(file);
            yaml.dump(update, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}