import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import fabaindaiz.modulator.modules.AModule;
import fabaindaiz.modulator.core.loader.moduleConfig;

public class zanakikMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;
    private languageLoader lang;

    public zanakikMain(Modulator modulator) {
        this.plugin = modulator;
        this.lang = plugin.getConfiguration().getMainLang();
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, "zanakik", jarName);

        plugin.getCommand("zanakik").setExecutor(new zanakikCommand(plugin, this));
        plugin.getCommand("zanakik").setTabCompleter(new zanakikTabCompleter(plugin, this));
        plugin.getCommand("zanakik").setPermissionMessage(lang.get("modulator.error2"));

        plugin.getServer().getPluginManager().registerEvents(new zanakikListener(plugin, this), plugin);
    }

    @Override
    public void onDisable() {
    }

}