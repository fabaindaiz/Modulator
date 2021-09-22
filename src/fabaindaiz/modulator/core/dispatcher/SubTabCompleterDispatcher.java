package fabaindaiz.modulator.core.dispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * Represents a class which can suggest sub tab completions for modules
 */
public class SubTabCompleterDispatcher {

    private final TabCompleterDispatcher tabCompleterDispatcher;
    private final HashMap<String, Function<ArrayList<String>, List<String>>> dispatcher = new HashMap<>();

    /**
     * @param tabCompleterDispatcher Tab completer dispatcher main class
     */
    public SubTabCompleterDispatcher(TabCompleterDispatcher tabCompleterDispatcher) {
        this.tabCompleterDispatcher = tabCompleterDispatcher;
    }

    /**
     * Dispatch the given sub tab completer for modules, returning the required method
     * @param args Passed command arguments
     * @param num Number of argument to dispatch
     * @return A List of possible completions for the final argument
     */
    public List<String> dispatch(ArrayList<String> args, int num) {
        return dispatcher.getOrDefault(args.get(num), tabCompleterDispatcher::emptyList).apply(args);
    }

    /**
     * Register a sub tab completer dispatch for modules
     * @param command Command which was executed
     * @param method Method to dispatch
     */
    public void register(String command, Function<ArrayList<String>, List<String>> method) {
        dispatcher.put(command, method);
    }

}
