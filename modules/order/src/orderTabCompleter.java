import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.TabCompleterDispatcher;
import fabaindaiz.modulator.core.modules.IModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class orderTabCompleter extends TabCompleterDispatcher {

    final List<String> info = Arrays.asList(new String[]{"help", "inv", "sort", "stack"});
    final List<String> sort = Arrays.asList(new String[]{"amount", "durability", "material", "name", "rarity"});

    protected orderTabCompleter(Modulator modulator, IModule module) {
        super(modulator, module);

        register("", this::info);
        register("help", super::emptyList);
        register("inv", this::sort);
        register("sort", this::sort);
        register("stack", super::emptyList);
    }

    private List<String> info(ArrayList<String> args) {
        return info;
    }

    private List<String> sort(ArrayList<String> args) {
        return sort;
    }

}