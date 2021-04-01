import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleConfig;
import fabaindaiz.modulator.modules.AModule;

public class zanakikMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;

    public zanakikMain(Modulator modulator) {
        setName("zkik");
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, getName(), jarName);

        setExecutor(new zanakikCommand(plugin, this));
        setTabCompleter(new zanakikTabCompleter(plugin, this));

        plugin.getServer().getPluginManager().registerEvents(new zanakikListener(plugin, this), plugin);
    }

    @Override
    public void onDisable() {
    }

}