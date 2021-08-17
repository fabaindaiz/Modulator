import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.LanguageLoader;
import fabaindaiz.modulator.core.modules.IModule;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

import static fabaindaiz.modulator.core.util.playersUtil.isOnlinePlayer;

public class zanakikCommand implements CommandExecutor {
    final Random rnd = new Random();
    private final boolean enabled;
    private final boolean noplayerzkik;
    private final boolean ignorebypass;
    private final Modulator plugin;
    private final IModule module;
    private final LanguageLoader lang;
    private final String key = "zanakik.command";

    protected zanakikCommand(Modulator modulator, IModule module) {

        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLanguageLoader();
        this.enabled = module.getConfiguration().getBoolean("zanakik.enable");
        this.noplayerzkik = module.getConfiguration().getBoolean("zanakik.noplayerzkik");
        this.ignorebypass = module.getConfiguration().getBoolean("zanakik.ignorebypass");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!this.enabled) {
            sender.sendMessage(lang.get(key, "disable1"));
            return true;
        } else if (this.noplayerzkik && sender instanceof Player) {
            sender.sendMessage(lang.get(key, "error5"));
            return true;
        }

        if (!sender.hasPermission("modulator.zkik")) {
            sender.sendMessage(lang.get("error.noper"));
            return true;
        }

        switch (args.length) {
            case 0:
                this.zkik(sender);
                return true;
            case 1:
                switch (args[0]) {
                    case "help":
                        this.help(sender);
                        return true;
                    case "getzkik":
                        this.getzkik(sender, 1);
                        return true;
                    default:
                        this.zkik(sender, args[0]);
                        return true;
                }
            case 2:
                switch (args[0]) {
                    case "getzkik":
                        this.getzkik(sender, Integer.parseInt(args[1]));
                        return true;
                    default:
                        sender.sendMessage(lang.get(key, "error1"));
                        return true;
                }
            case 3:
                switch (args[0]) {
                    case "getzkik":
                        this.getzkik(Bukkit.getPlayer(args[2]), Integer.parseInt(args[1]));
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

    private void zkik(CommandSender sender) {

        Player[] players = zanakikUtil.getKickablePlayers(module);
        if (players.length < 1) {
            Bukkit.broadcastMessage(lang.get(key, "error2"));
            return;
        }

        int rndindex = rnd.nextInt(players.length);
        Player rndplayer = players[rndindex];
        kik(rndplayer);
    }

    private void getzkik(CommandSender sender, int num) {
        String senderName = sender.getName();
        if (isOnlinePlayer(senderName)) {
            Bukkit.getPlayer(senderName).getInventory().addItem(zanakikUtil.getZanakik(module, num < 64 ? num : 64));
        }
    }

    private void zkik(CommandSender sender, String playerName) {

        if (!isOnlinePlayer(playerName)) {
            sender.sendMessage(lang.get(key, "error3"));
            return;
        }
        Player selP = Bukkit.getPlayer(playerName);
        if (selP.hasPermission("modulator.kickbypass") && !this.ignorebypass) {
            sender.sendMessage(lang.get(key, "error4"));
            return;
        }

        sender.sendMessage(lang.get(key, "kikmessage2") + playerName);
        kik(selP);
    }


    protected void kik(Player player) {
        player.getInventory().addItem(zanakikUtil.getZanakik(module, 1));

        Bukkit.getBanList(Type.NAME).addBan(player.getName(), "Zanakik", zanakikUtil.getZkikExpiresDate(), "zanakik");
        Bukkit.broadcastMessage(player.getName() + lang.get(key, "kikmessage1"));

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> player.kickPlayer("Zanakik"), 300L);
    }


}