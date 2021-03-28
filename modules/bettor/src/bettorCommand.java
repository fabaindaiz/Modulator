import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class bettorCommand implements CommandExecutor {
    private final Modulator plugin;
    private final languageLoader lang;

    protected bettorCommand(Modulator modulator) {

        this.plugin = modulator;
        this.lang = modulator.getConfiguration().getMainLang();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        switch (args.length) {
            case 0:
                sender.sendMessage(lang.get("bettor.info1"));
                sender.sendMessage(lang.get("bettor.info2"));
                return true;
            case 1:
                switch (args[0]) {
                    case "help":
                        this.help(sender);
                        return true;
                    default:
                        sender.sendMessage(lang.get("bettor.error1"));
                        return true;
                }
            default:
                sender.sendMessage(lang.get("bettor.error1"));
                return true;
        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage(lang.get("bettor.help1"));
        sender.sendMessage(lang.get("bettor.help2"));
        sender.sendMessage(lang.get("bettor.help3"));
    }


}