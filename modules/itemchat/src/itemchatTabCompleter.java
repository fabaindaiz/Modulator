import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.TabCompleterDispatcher;
import fabaindaiz.modulator.core.modules.IModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class itemchatTabCompleter extends TabCompleterDispatcher {

    final List<String> info = Arrays.asList(new String[]{"help", "show", "owner"});

    protected itemchatTabCompleter(Modulator modulator, IModule module) {
        super(modulator, module);

        register("", this::info);
        register("show", super::emptyList);
        register("owner", super::emptyList);
    }

    private List<String> info(ArrayList<String> args) {
        return info;
    }

}
