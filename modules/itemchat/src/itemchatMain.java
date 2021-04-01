import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleConfig;
import fabaindaiz.modulator.modules.AModule;

public class itemchatMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;

    public itemchatMain(Modulator modulator) {
        setName("itemchat");
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, getName(), jarName);

        setExecutor(new itemchatCommand(plugin, this));
        setTabCompleter(new itemchatTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {
    }

}