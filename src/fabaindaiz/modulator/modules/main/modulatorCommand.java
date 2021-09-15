package fabaindaiz.modulator.modules.main;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.CommandDispatcher;
import fabaindaiz.modulator.core.modules.IModule;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Set;

public class modulatorCommand extends CommandDispatcher {

    protected modulatorCommand(Modulator modulator, IModule module) {
        super(modulator, module);

        register("", this::info);
        register("help", this::help);
        register("modules", this::modules);
        register("reload", this::reload);
    }

    private Boolean info(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        sender.sendMessage(lang.get("modulator.info1"));
        sender.sendMessage(lang.get("modulator.info2"));
        return true;
    }

    private Boolean help(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        sender.sendMessage(lang.get("modulator.help1"));
        sender.sendMessage(lang.get("modulator.help2"));
        plugin.getCommand().getModules().forEach((name, module) -> {
            if (module.getDescription() != null) {
                sender.sendMessage(module.getDescription());
            }
        });
        return true;
    }

    private Boolean modules(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        Set<String> moduleNames = plugin.getCommand().getModuleNames();

        StringBuilder string = new StringBuilder();
        string.append(lang.get("modulator.info3"));

        moduleNames.forEach((name) -> string.append(name).append(", "));
        sender.sendMessage(string.toString());
        return true;
    }

    private boolean reload(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        plugin.reload();
        sender.sendMessage(lang.get("modulator.reload1"));
        return true;
    }

}