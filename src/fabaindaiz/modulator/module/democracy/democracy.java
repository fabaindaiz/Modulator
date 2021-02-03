package fabaindaiz.modulator.module.democracy;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.module.IModule;
import fabaindaiz.modulator.core.config.languageLoader;

public class democracy implements IModule {
    private final Modulator plugin;
    private languageLoader lang;

    // Democracy depends on modinput module
    public democracy(Modulator modulator) {
        this.plugin = modulator;
        this.lang = plugin.getConfiguration().getLang();
    }

    @Override
    public void onEnable() {
        plugin.getCommand("democracy").setExecutor(new democracyCommand(plugin));
        plugin.getCommand("democracy").setTabCompleter(new democracyTabCompleter(plugin));
        plugin.getCommand("democracy").setPermissionMessage(lang.get("modulator.error2"));
    }

    @Override
    public void onDisable() { }

}
