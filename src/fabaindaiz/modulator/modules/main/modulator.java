package fabaindaiz.modulator.modules.main;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.modules.AModule;

public class modulator extends AModule {

    private final Modulator plugin;

    public modulator(Modulator modulator) {

        setConfiguration(modulator.getConfiguration().getConfiguration());
        setLanguageLoader(modulator.getConfiguration().getLanguageLoader());

        setName("modulator");
        setPermission("modulator.op");

        setAliases(getConfiguration().getStringList("modulator.alias"));

        this.plugin = modulator;

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
