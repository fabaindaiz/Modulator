import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleConfig;
import fabaindaiz.modulator.modules.AModule;

public class zanakikMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;

    public zanakikMain(Modulator modulator) {
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, "zanakik", jarName);

        plugin.getCommand("zanakik").setExecutor(new zanakikCommand(plugin, this));
        plugin.getCommand("zanakik").setTabCompleter(new zanakikTabCompleter(plugin, this));
        plugin.getCommand("zanakik").setPermissionMessage(getLang().get("error.noper"));

        plugin.getServer().getPluginManager().registerEvents(new zanakikListener(plugin, this), plugin);
    }

    @Override
    public void onDisable() {
    }

}