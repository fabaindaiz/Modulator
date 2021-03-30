import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleConfig;
import fabaindaiz.modulator.modules.AModule;

public class itemchatMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;

    public itemchatMain(Modulator modulator) {
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, "itemchat", jarName);

        plugin.getCommand("itemchat").setExecutor(new itemchatCommand(plugin, this));
        plugin.getCommand("itemchat").setTabCompleter(new itemchatTabCompleter(plugin, this));
        plugin.getCommand("itemchat").setPermissionMessage(getLang().get("error.noper"));
    }

    @Override
    public void onDisable() {
    }

}