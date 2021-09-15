import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.ModuleConfiguration;
import fabaindaiz.modulator.core.modules.AModule;

public class bettorMain extends AModule {
    private final Modulator plugin;
    private ModuleConfiguration moduleConfiguration;

    public bettorMain(Modulator modulator) {
        setName("bettor");
        setDescription("\u00A7e/bettor \u00A7fSistema de apuestas en eventos");
        setPermission("modulator.use");

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
