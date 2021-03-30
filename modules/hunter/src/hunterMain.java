import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleConfig;
import fabaindaiz.modulator.modules.AModule;

public class hunterMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;

    public hunterMain(Modulator modulator) {
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, "hunter", jarName);

        plugin.getCommand("hunter").setExecutor(new hunterCommand(plugin, this));
        plugin.getCommand("hunter").setTabCompleter(new hunterTabCompleter(plugin, this));
        plugin.getCommand("hunter").setPermissionMessage(getLang().get("error.nopem"));
    }

    @Override
    public void onDisable() {

    }
}