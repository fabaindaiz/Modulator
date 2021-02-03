package fabaindaiz.modulator.module.test;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.module.IModule;
import fabaindaiz.modulator.core.config.languageLoader;

public class test implements IModule {
    private final Modulator plugin;
    private languageLoader lang;

    public test(Modulator modulator) {

        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        this.lang = plugin.getConfiguration().getLang();

        plugin.getCommand("test").setExecutor(new testCommand(plugin));
        plugin.getCommand("test").setTabCompleter(new testTabCompleter(plugin));
        plugin.getCommand("test").setPermissionMessage(lang.get("modulator.error2"));
    }

    @Override
    public void onDisable() {

    }
}