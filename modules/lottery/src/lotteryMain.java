import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleConfig;
import fabaindaiz.modulator.modules.AModule;

public class lotteryMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;
    private lotteryStorage storage;

    public lotteryMain(Modulator modulator) {
        setName("lottery");
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, getName(), jarName);

        storage = new lotteryStorage(plugin, this);
        setExecutor(new lotteryCommand(plugin, storage, this));
        setTabCompleter(new lotteryTabCompleter(plugin, this));

        plugin.getServer().getPluginManager().registerEvents(new lotteryListener(plugin, this, storage), plugin);
    }

    @Override
    public void onDisable() {

    }
}
