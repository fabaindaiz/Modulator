import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleConfig;
import fabaindaiz.modulator.modules.AModule;

public class updaterMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;

    public updaterMain(Modulator modulator) {
        setName("updater");
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, getName(), jarName);

        setExecutor(new updaterCommand(plugin, this));
        setTabCompleter(new updaterTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {

    }
}