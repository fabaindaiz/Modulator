import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.ModuleConfiguration;
import fabaindaiz.modulator.core.modules.AModule;
import org.bukkit.command.Command;

public class democracyMain extends AModule {
    private final Modulator plugin;
    private ModuleConfiguration moduleConfiguration;
    private Command command;

    // Democracy depends on modinput module
    public democracyMain(Modulator modulator) {
        setName("democracy");
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfiguration = new ModuleConfiguration(plugin, this, getName(), getJarName());

        setExecutor(new democracyCommand(plugin, this));
        setTabCompleter(new democracyTabCompleter(plugin, this));
    }

    @Override
    public void onDisable() {
    }

}
