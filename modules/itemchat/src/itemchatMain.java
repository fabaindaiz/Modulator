import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.ModuleConfiguration;
import fabaindaiz.modulator.core.modules.AModule;

public class itemchatMain extends AModule {
    private final Modulator plugin;
    private ModuleConfiguration moduleConfiguration;

    public itemchatMain(Modulator modulator) {
        setName("itemchat");
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfiguration = new ModuleConfiguration(plugin, this, getName(), getJarName());

        setExecutor(new itemchatCommand(plugin, this));
        setTabCompleter(new itemchatTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {
    }

}