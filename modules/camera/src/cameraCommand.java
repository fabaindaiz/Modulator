import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.CommandDispatcher;
import fabaindaiz.modulator.core.modules.IModule;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

import static fabaindaiz.modulator.core.util.playersUtil.isOnlinePlayer;

public class cameraCommand extends CommandDispatcher {

    private final String key = "camera.command";
    private final cameraRenderer renderer;

    protected cameraCommand(Modulator modulator, cameraRenderer renderer, IModule module) {
        super(modulator, module);

        this.renderer = renderer;

        register("", this::info);
        register("help", this::help);
        register("take", this::take);
    }

    private boolean info(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        sender.sendMessage(lang.get(key, "info1"));
        sender.sendMessage(lang.get(key, "info2"));
        return true;
    }

    private boolean help(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        sender.sendMessage(lang.get(key, "help1"));
        sender.sendMessage(lang.get(key, "help2"));
        sender.sendMessage(lang.get(key, "help3"));
        return true;
    }

    private boolean take(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        if (!isOnlinePlayer(sender.getName())) {
            sender.sendMessage(lang.get(key, "error2"));
            return true;
        }
        renderer.render(Bukkit.getPlayer(sender.getName()));
        return true;
    }

}