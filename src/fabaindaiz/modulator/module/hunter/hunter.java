package fabaindaiz.modulator.module.hunter;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.module.IModule;
import fabaindaiz.modulator.core.config.languageLoader;

public class hunter implements IModule {
    private final Modulator plugin;
    private languageLoader lang;

    public hunter(Modulator modulator) {

        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        this.lang = plugin.getConfiguration().getLang();

        plugin.getCommand("hunter").setExecutor(new hunterCommand(plugin));
        plugin.getCommand("hunter").setTabCompleter(new hunterTabCompleter(plugin));
        plugin.getCommand("hunter").setPermissionMessage(lang.get("modulator.error2"));
    }

    @Override
    public void onDisable() {

    }
}