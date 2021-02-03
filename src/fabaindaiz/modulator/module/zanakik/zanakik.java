package fabaindaiz.modulator.module.zanakik;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.module.IModule;
import fabaindaiz.modulator.core.config.languageLoader;

public class zanakik implements IModule {
    private final Modulator plugin;
    private languageLoader lang;

    public zanakik(Modulator modulator) {
        this.plugin = modulator;
        this.lang = plugin.getConfiguration().getLang();
    }

    @Override
    public void onEnable() {
        plugin.getCommand("zanakik").setExecutor(new zanakikCommand(plugin));
        plugin.getCommand("zanakik").setTabCompleter(new zanakikTabCompleter(plugin));
        plugin.getCommand("zanakik").setPermissionMessage(lang.get("modulator.error2"));

        plugin.getServer().getPluginManager().registerEvents(new zanakikListener(plugin), plugin);
    }

    @Override
    public void onDisable() { }

}