package fabaindaiz.modulator.core.main;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.modules.AModule;

public class modulator extends AModule {
    private final Modulator plugin;

    public modulator(Modulator modulator) {
        this.plugin = modulator;
        setLang(plugin.getConfiguration().getMainLang());
    }

    @Override
    public void onEnable() {
        plugin.getCommand("modulator").setExecutor(new modulatorCommand(plugin, this));
        plugin.getCommand("modulator").setTabCompleter(new modulatorTabCompleter(plugin, this));
        plugin.getCommand("modulator").setPermissionMessage(getLang().get("error.noper"));
    }

    @Override
    public void onDisable() {

    }
}
