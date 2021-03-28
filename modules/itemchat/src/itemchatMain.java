import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import fabaindaiz.modulator.modules.AModule;
import fabaindaiz.modulator.core.loader.moduleConfig;

public class itemchatMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;
    private languageLoader mainLang;

    public itemchatMain(Modulator modulator) {
        this.plugin = modulator;
        this.mainLang = plugin.getConfiguration().getMainLang();
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, "itemchat", jarName);

        plugin.getCommand("itemchat").setExecutor(new itemchatCommand(plugin, this));
        plugin.getCommand("itemchat").setTabCompleter(new itemchatTabCompleter(plugin, this));
        plugin.getCommand("itemchat").setPermissionMessage(mainLang.get("modulator.error2"));
    }

    @Override
    public void onDisable() {
    }

}