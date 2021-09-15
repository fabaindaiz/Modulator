import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.TabCompleterDispatcher;
import fabaindaiz.modulator.core.modules.IModule;

import java.util.Arrays;
import java.util.List;

public class lotteryTabCompleter extends TabCompleterDispatcher {

    final List<String> info = Arrays.asList(new String[]{"help", "buy", "draw", "collect", "results"});

    protected lotteryTabCompleter(Modulator modulator, IModule module) {
        super(modulator, module);

        register("", this::info);
        register("help", super::emptyList);
        register("buy", super::emptyList);
        register("draw", super::emptyList);
        register("collect", super::emptyList);
        register("results", super::emptyList);
    }

    private List<String> info() {
        return info;
    }

}
