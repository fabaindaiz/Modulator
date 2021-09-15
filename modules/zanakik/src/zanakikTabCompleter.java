import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.TabCompleterDispatcher;
import fabaindaiz.modulator.core.modules.IModule;

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

    private List<String> info() {
        return info;
    }

    private List<String> get() {
        return get;
    }

    private List<String> player() {
        return null;
    }

}
