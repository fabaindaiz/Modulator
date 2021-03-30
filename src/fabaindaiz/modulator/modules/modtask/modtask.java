package fabaindaiz.modulator.modules.modtask;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.modules.AModule;

public class modtask extends AModule {
    private final Modulator plugin;

    public modtask(Modulator modulator) {
        this.plugin = modulator;
        setLang(plugin.getConfiguration().getMainLang());
    }

    @Override
    public void onEnable() {
        plugin.getCommand("modtask").setExecutor(new modtaskCommand(plugin, this));
        plugin.getCommand("modtask").setTabCompleter(new modtaskTabCompleter(plugin, this));
        plugin.getCommand("modtask").setPermissionMessage(getLang().get("error.noper"));
    }

    @Override
    public void onDisable() {

    }
}
