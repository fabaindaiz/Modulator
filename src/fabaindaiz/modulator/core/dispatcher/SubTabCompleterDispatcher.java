package fabaindaiz.modulator.core.dispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class SubTabCompleterDispatcher {

    private final TabCompleterDispatcher tabCompleterDispatcher;

    private final HashMap<String, Function<ArrayList<String>, List<String>>> dispatcher = new HashMap<>();

    public SubTabCompleterDispatcher(TabCompleterDispatcher tabCompleterDispatcher) {
        this.tabCompleterDispatcher = tabCompleterDispatcher;
    }

    public List<String> dispatch(ArrayList<String> args, int num) {
        return dispatcher.getOrDefault(args.get(num), tabCompleterDispatcher::emptyList).apply(args);
    }

    public void register(String command, Function<ArrayList<String>, List<String>> method) {
        dispatcher.put(command, method);
    }

}
