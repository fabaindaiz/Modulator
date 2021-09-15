import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.ModuleConfiguration;
import fabaindaiz.modulator.core.modules.AModule;

public class updaterMain extends AModule {
    private final Modulator plugin;
    private ModuleConfiguration moduleConfiguration;

    public updaterMain(Modulator modulator) {
        setName("updater");
        setDescription("\u00A7e/updater \u00A7fActualiza plugins del servidor");
        setPermission("modulator.op");

        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfiguration = new ModuleConfiguration(plugin, this, getName(), getJarName());

        setExecutor(new updaterCommand(plugin, this));
        setTabCompleter(new updaterTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {

    }
}