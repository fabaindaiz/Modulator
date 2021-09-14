import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.ModuleConfiguration;
import fabaindaiz.modulator.core.modules.AModule;

public class lotteryMain extends AModule {
    private final Modulator plugin;
    private ModuleConfiguration moduleConfiguration;
    private lotteryStorage storage;

    public lotteryMain(Modulator modulator) {
        setName("lottery");
        setDescription("\u00A7e/lottery \u00A7fCompra billetes de loter\u00eda");
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfiguration = new ModuleConfiguration(plugin, this, getName(), getJarName());

        storage = new lotteryStorage(plugin, this);
        setExecutor(new lotteryCommand(plugin, storage, this));
        setTabCompleter(new lotteryTabCompleter(plugin, this));

        plugin.getServer().getPluginManager().registerEvents(new lotteryListener(plugin, this, storage), plugin);
    }

    @Override
    public void onDisable() {

    }
}
