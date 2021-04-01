import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleConfig;
import fabaindaiz.modulator.modules.AModule;

public class hunterMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;

    public hunterMain(Modulator modulator) {
        setName("hunter");
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, getName(), jarName);

        setExecutor(new hunterCommand(plugin, this));
        setTabCompleter(new hunterTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {

    }
}