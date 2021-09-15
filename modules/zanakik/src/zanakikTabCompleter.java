import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.TabCompleterDispatcher;
import fabaindaiz.modulator.core.modules.IModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class zanakikTabCompleter extends TabCompleterDispatcher {

    final List<String> info = Arrays.asList(new String[]{"help", "get", "player"});
    final List<String> get = Arrays.asList(new String[]{"<num>"});

    protected zanakikTabCompleter(Modulator modulator, IModule module) {
        super(modulator, module);

        register("", this::info);
        register("help", super::emptyList);
        register("get", this::get);
        register("player", this::player);
    }

    private List<String> info(ArrayList<String> args) {
        return info;
    }

    private List<String> get(ArrayList<String> args) {
        return get;
    }

    private List<String> player(ArrayList<String> args) {
        return null;
    }

}
