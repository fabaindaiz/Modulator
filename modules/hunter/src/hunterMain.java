import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.ModuleConfiguration;
import fabaindaiz.modulator.core.modules.AModule;

public class hunterMain extends AModule {
    private final Modulator plugin;
    private ModuleConfiguration moduleConfiguration;

    public hunterMain(Modulator modulator) {
        setName("hunter");
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfiguration = new ModuleConfiguration(plugin, this, getName(), getJarName());

        setExecutor(new hunterCommand(plugin, this));
        setTabCompleter(new hunterTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {

    }
}