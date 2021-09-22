package fabaindaiz.modulator.core.dispatcher;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;

/**
 * Represents a class which dispatch sub commands for modules
 */
public class SubCommandDispatcher {

    private final CommandDispatcher commandDispatcher;
    private final HashMap<Integer, BiFunction<CommandSender, ArrayList<String>, Boolean>> dispatcher = new HashMap<>();

    /**
     * @param commandDispatcher Command dispatcher main class
     */
    public SubCommandDispatcher(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    /**
     * Dispatch the given sub command for modules, returning the required method for sub command
     * @param sender Source of the command
     * @param args Passed command arguments
     * @return required method for sub command
     */
    public BiFunction<CommandSender, ArrayList<String>, Boolean> dispatch (CommandSender sender, ArrayList<String> args) {
        return dispatcher.getOrDefault(args.size(), commandDispatcher::error);
    }

    /**
     * Register a sub command dispatch for modules
     * @param num Number of args
     * @param method Method to dispatch
     */
    public void register(int num, BiFunction<CommandSender, ArrayList<String>, Boolean> method) {
        dispatcher.put(num, method);
    }


}
