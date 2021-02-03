package fabaindaiz.modulator.module.modtask;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.module.IModule;
import fabaindaiz.modulator.core.config.languageLoader;

public class modtask implements IModule {
    private final Modulator plugin;
    private languageLoader lang;

    public modtask(Modulator modulator) {

        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        this.lang = plugin.getConfiguration().getLang();
        plugin.getCommand("modtask").setExecutor(new modtaskCommand(plugin));
        plugin.getCommand("modtask").setTabCompleter(new modtaskTabCompleter(plugin));
        plugin.getCommand("modtask").setPermissionMessage(lang.get("modulator.error2"));
    }

    @Override
    public void onDisable() {

    }
}
