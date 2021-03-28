import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class testCommand implements CommandExecutor {
    private final Modulator plugin;
    private final languageLoader lang;

    protected testCommand(Modulator modulator) {

        this.plugin = modulator;
        this.lang = modulator.getConfiguration().getMainLang();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

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
    }


}