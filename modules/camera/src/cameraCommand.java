import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.langLoader;
import fabaindaiz.modulator.modules.IModule;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cameraCommand implements CommandExecutor {
    private final Modulator plugin;
    private final IModule module;
    private final langLoader lang;
    private final String key = "camera.command";
    private final cameraRenderer renderer;

    protected cameraCommand(Modulator modulator, cameraRenderer renderer, IModule module) {
        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLang();
        this.renderer = renderer;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("modulator.camera")){
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
                    case "take":
                        this.take(Bukkit.getPlayer(sender.getName()));
                        return true;
                    default:
                        sender.sendMessage(lang.get(key, "error1"));
                        return true;
                }
            case 2:
                switch (args[0]) {
                    case "take":
                        this.take(Bukkit.getPlayer(args[1]));
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

    private void take(Player player) {
        if (!player.isOnline()) {
            return;
        }
        renderer.render(player);
    }

}