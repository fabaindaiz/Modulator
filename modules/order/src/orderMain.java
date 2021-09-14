import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.ModuleConfiguration;
import fabaindaiz.modulator.core.modules.AModule;

public class orderMain extends AModule {
    private final Modulator plugin;
    private ModuleConfiguration moduleConfiguration;

    public orderMain(Modulator modulator) {
        setName("order");
        setDescription("\u00A7e/order \u00A7fOrdena tu inventario");
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfiguration = new ModuleConfiguration(plugin, this, getName(), getJarName());

        setExecutor(new orderCommand(plugin, this));
        setTabCompleter(new orderTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {
    }

}