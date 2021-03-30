import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleConfig;
import fabaindaiz.modulator.modules.AModule;

public class testMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;

    public testMain(Modulator modulator) {
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, "test", jarName);

        plugin.getCommand("test").setExecutor(new testCommand(plugin, this));
        plugin.getCommand("test").setTabCompleter(new testTabCompleter(plugin, this));
        plugin.getCommand("test").setPermissionMessage(getLang().get("error.noper"));
    }

    @Override
    public void onDisable() {

    }
}