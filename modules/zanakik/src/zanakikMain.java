import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.ModuleConfiguration;
import fabaindaiz.modulator.core.modules.AModule;

public class zanakikMain extends AModule {
    private final Modulator plugin;
    private ModuleConfiguration moduleConfiguration;

    public zanakikMain(Modulator modulator) {
        setName("zkik");
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfiguration = new ModuleConfiguration(plugin, this, getName(), getJarName());

        setExecutor(new zanakikCommand(plugin, this));
        setTabCompleter(new zanakikTabCompleter(plugin, this));

        plugin.getServer().getPluginManager().registerEvents(new zanakikListener(plugin, this), plugin);
    }

    @Override
    public void onDisable() {
    }

}