import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleConfig;
import fabaindaiz.modulator.modules.AModule;

public class lotteryMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;
    private lotteryStorage storage;

    public lotteryMain(Modulator modulator) {
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, "lottery", jarName);

        storage = new lotteryStorage(plugin, this);
        plugin.getCommand("lottery").setExecutor(new lotteryCommand(plugin, storage, this));
        plugin.getCommand("lottery").setTabCompleter(new lotteryTabCompleter(plugin, this));
        plugin.getCommand("lottery").setPermissionMessage(getLang().get("error.noper"));

        plugin.getServer().getPluginManager().registerEvents(new lotteryListener(plugin, this, storage), plugin);
    }

    @Override
    public void onDisable() {

    }
}
