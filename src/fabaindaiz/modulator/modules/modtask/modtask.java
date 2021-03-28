package fabaindaiz.modulator.modules.modtask;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import fabaindaiz.modulator.modules.AModule;

public class modtask extends AModule {
    private final Modulator plugin;
    private languageLoader lang;

    public modtask(Modulator modulator) {

        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        this.lang = plugin.getConfiguration().getMainLang();
        plugin.getCommand("modtask").setExecutor(new modtaskCommand(plugin));
        plugin.getCommand("modtask").setTabCompleter(new modtaskTabCompleter(plugin));
        plugin.getCommand("modtask").setPermissionMessage(lang.get("modulator.error2"));
    }

    @Override
    public void onDisable() {

    }
}
