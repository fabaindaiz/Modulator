import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.loader.moduleConfig;
import fabaindaiz.modulator.modules.AModule;
import org.bukkit.command.Command;

public class democracyMain extends AModule {
    private final Modulator plugin;
    private moduleConfig moduleConfig;
    private Command command;

    // Democracy depends on modinput module
    public democracyMain(Modulator modulator) {
        setName("democracy");
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, getName(), jarName);

        setExecutor(new democracyCommand(plugin, this));
        setTabCompleter(new democracyTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {
    }

}
