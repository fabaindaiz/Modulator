import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleConfig;
import fabaindaiz.modulator.modules.AModule;

public class bettorMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;

    public bettorMain(Modulator modulator) {
        setName("bettor");
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, getName(), jarName);

        setExecutor(new bettorCommand(plugin, this));
        setTabCompleter(new bettorTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {

    }
}
