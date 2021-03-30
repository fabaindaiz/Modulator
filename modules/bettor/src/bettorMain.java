import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleConfig;
import fabaindaiz.modulator.modules.AModule;

public class bettorMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;

    public bettorMain(Modulator modulator) {
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, "bettor", jarName);

        plugin.getCommand("bettor").setExecutor(new bettorCommand(plugin, this));
        plugin.getCommand("bettor").setTabCompleter(new bettorTabCompleter(plugin, this));
        plugin.getCommand("bettor").setPermissionMessage(getLang().get("error.noper"));
    }

    @Override
    public void onDisable() {

    }
}
