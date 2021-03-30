import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleConfig;
import fabaindaiz.modulator.modules.AModule;

public class updaterMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;

    public updaterMain(Modulator modulator) {
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, "updater", jarName);

        plugin.getCommand("updater").setExecutor(new updaterCommand(plugin, this));
        plugin.getCommand("updater").setTabCompleter(new updaterTabCompleter(plugin, this));
        plugin.getCommand("updater").setPermissionMessage(getLang().get("error.noper"));
    }

    @Override
    public void onDisable() {

    }
}