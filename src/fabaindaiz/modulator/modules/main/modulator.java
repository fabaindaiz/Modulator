package fabaindaiz.modulator.modules.main;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.modules.AModule;

public class modulator extends AModule {

    private final Modulator plugin;

    public modulator(Modulator modulator) {
        setName("modulator");
        this.plugin = modulator;
        setLanguageLoader(plugin.getConfiguration().getLanguageLoader());
    }

    @Override
    public void onEnable() {
        setExecutor(new modulatorCommand(plugin, this));
        setTabCompleter(new modulatorTabCompleter(plugin, this));

        plugin.getCommand("modulator").setExecutor(getExecutor());
        plugin.getCommand("modulator").setTabCompleter(getTabCompleter());
    }

    @Override
    public void onDisable() {

    }
}
