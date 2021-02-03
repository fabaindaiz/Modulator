package fabaindaiz.modulator.module.hunter;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class hunterCommand implements CommandExecutor {
    private final Modulator plugin;
    private final languageLoader lang;

    protected hunterCommand(Modulator modulator) {

        this.plugin = modulator;
        this.lang = modulator.getConfiguration().getLang();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        switch (args.length) {
            case 0:
                sender.sendMessage(lang.get("hunter.info1"));
                sender.sendMessage(lang.get("hunter.info2"));
                return true;
            case 1:
                switch (args[0]) {
                    case "help":
                        this.help(sender);
                        return true;
                    default:
                        sender.sendMessage(lang.get("hunter.error1"));
                        return true;
                }
            default:
                sender.sendMessage(lang.get("hunter.error1"));
                return true;
        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage(lang.get("hunter.help1"));
        sender.sendMessage(lang.get("hunter.help2"));
        sender.sendMessage(lang.get("hunter.help3"));
    }


}