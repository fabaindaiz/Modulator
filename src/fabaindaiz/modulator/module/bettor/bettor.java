package fabaindaiz.modulator.module.bettor;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.module.IModule;
import fabaindaiz.modulator.core.config.languageLoader;

public class bettor implements IModule {
    private final Modulator plugin;
    private languageLoader lang;

    public bettor(Modulator modulator) {

        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        this.lang = plugin.getConfiguration().getLang();

        plugin.getCommand("bettor").setExecutor(new bettorCommand(plugin));
        plugin.getCommand("bettor").setTabCompleter(new bettorTabCompleter(plugin));
        plugin.getCommand("bettor").setPermissionMessage(lang.get("modulator.error2"));
    }

    @Override
    public void onDisable() {

    }
}
