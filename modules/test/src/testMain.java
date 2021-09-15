import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loaderOld.moduleConfig;
import fabaindaiz.modulator.modules.AModule;

public class testMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;

    public testMain(Modulator modulator) {
        setName("test");
        setDescription("\u00A7e/Test \u00A7fTest");
        setPermission("modulator.adm");

        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, getName(), jarName);

        setExecutor(new testCommand(plugin, this));
        setTabCompleter(new testTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {

    }
}