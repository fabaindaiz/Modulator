package fabaindaiz.modulator.modules.modinput;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.TabCompleterDispatcher;
import fabaindaiz.modulator.core.modules.IModule;

import java.util.Arrays;
import java.util.List;

public class modinputTabCompleter extends TabCompleterDispatcher {

    final List<String> info = Arrays.asList(new String[]{});

    protected modinputTabCompleter(Modulator modulator, IModule module) {
        super(modulator, module);

        register("", this::info);
    }

    private List<String> info() {
        return info;
    }

}
