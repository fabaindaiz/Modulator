package fabaindaiz.modulator.core.main;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.langLoader;
import fabaindaiz.modulator.modules.IModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Set;

public class modulatorCommand implements CommandExecutor {
    private final Modulator plugin;
    private final IModule module;
    private final langLoader lang;

    protected modulatorCommand(Modulator modulator, IModule module) {
        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLang();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (plugin.commandList.containsKey(label.toLowerCase())){
            return plugin.commandList.get(label).getExecutor().onCommand(sender, command, label, args);
        }

        if (!sender.hasPermission("modulator.main")) {
            sender.sendMessage(lang.get("error.noper"));
            return true;
        }

        switch (args.length) {
            case 0:
                sender.sendMessage(lang.get("modulator.info1"));
                sender.sendMessage(lang.get("modulator.info2"));
                return true;
            case 1:
                switch (args[0]) {
                    case "help":
                        this.help(sender);
                        return true;
                    case "modules":
                        this.modules(sender);
                        return true;
                    case "reload":
                        this.reload(sender);
                        return true;
                    default:
                        sender.sendMessage(lang.get("modulator.error1"));
                        return true;
                }
            default:
                sender.sendMessage(lang.get("modulator.error1"));
                return true;
        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage(lang.get("modulator.help1"));
        sender.sendMessage(lang.get("modulator.help2"));
        sender.sendMessage(lang.get("modulator.help3"));
        sender.sendMessage(lang.get("modulator.help4"));
        sender.sendMessage(lang.get("modulator.help5"));
        sender.sendMessage(lang.get("modulator.help6"));
        sender.sendMessage(lang.get("modulator.help7"));
    }

    private void modules(CommandSender sender) {
        Set<String> moduleNames = plugin.getModuleNames();

        StringBuilder string = new StringBuilder();
        string.append(lang.get("modulator.info3"));

        moduleNames.forEach((name) -> string.append(name).append(", "));

        sender.sendMessage(string.toString());
    }

    private void reload(CommandSender sender) {
        plugin.reload();
        sender.sendMessage(lang.get("modulator.reload1"));
    }

}