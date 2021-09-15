import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.TabCompleterDispatcher;
import fabaindaiz.modulator.core.modules.IModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class updaterTabCompleter extends TabCompleterDispatcher {

    final List<String> info = Arrays.asList(new String[]{"help", "update"});

    protected updaterTabCompleter(Modulator modulator, IModule module) {
        super(modulator, module);

        register("", this::info);
        register("help", super::emptyList);
        register("update", super::emptyList);
    }

    private List<String> info(ArrayList<String> args) {
        return info;
    }

}