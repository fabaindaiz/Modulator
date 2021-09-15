import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.TabCompleterDispatcher;
import fabaindaiz.modulator.core.modules.IModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class democracyTabCompleter extends TabCompleterDispatcher {

    final List<String> info = Arrays.asList(new String[]{"help", "done", "cancel", "create"});
    final List<String> create = Arrays.asList(new String[]{"<question>"});

    protected democracyTabCompleter(Modulator modulator, IModule module) {
        super(modulator, module);

        register("", this::info);
        register("help", super::emptyList);
        register("done", super::emptyList);
        register("cancel", super::emptyList);
        register("create", this::create);
    }

    private List<String> info(ArrayList<String> args) {
        return info;
    }

    private List<String> create(ArrayList<String> args) {
        return create;
    }

}
