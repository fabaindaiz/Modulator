import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.ModuleConfiguration;
import fabaindaiz.modulator.core.modules.AModule;

public class bettorMain extends AModule {
    private final Modulator plugin;
    private ModuleConfiguration moduleConfiguration;

    public bettorMain(Modulator modulator) {
        setName("bettor");
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfiguration = new ModuleConfiguration(plugin, this, getName(), getJarName());

        setExecutor(new bettorCommand(plugin, this));
        setTabCompleter(new bettorTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {

    }
}
