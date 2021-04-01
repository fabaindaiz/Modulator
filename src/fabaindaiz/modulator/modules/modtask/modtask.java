package fabaindaiz.modulator.modules.modtask;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.modules.AModule;

public class modtask extends AModule {
    private final Modulator plugin;

    public modtask(Modulator modulator) {
        this.plugin = modulator;
        setLang(plugin.getConfiguration().getMainLang());
    }

    @Override
    public void onEnable() {
        setExecutor(new modtaskCommand(plugin, this));
        setTabCompleter(new modtaskTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {

    }
}
