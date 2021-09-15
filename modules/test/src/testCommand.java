import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.langLoader;
import fabaindaiz.modulator.modules.IModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class testCommand extends CommandDispatcher {

    private final String key = "test.command";

    protected testCommand(Modulator modulator, IModule module) {
        super(modulator, module);

        register("", this::info);
        register("help", this::help);
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


}