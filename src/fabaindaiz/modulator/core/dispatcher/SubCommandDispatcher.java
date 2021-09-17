package fabaindaiz.modulator.core.dispatcher;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;

public class SubCommandDispatcher {

    private final CommandDispatcher commandDispatcher;

    private final HashMap<Integer, BiFunction<CommandSender, ArrayList<String>, Boolean>> dispatcher = new HashMap<>();

    public SubCommandDispatcher(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    public BiFunction<CommandSender, ArrayList<String>, Boolean> dispatch (CommandSender sender, ArrayList<String> args) {
        return dispatcher.getOrDefault(args.size(), commandDispatcher::error);
    }

    public void register(int num, BiFunction<CommandSender, ArrayList<String>, Boolean> method) {
        dispatcher.put(num, method);
    }


}
