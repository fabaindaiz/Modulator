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
        this.plugin = modulator;
    }

    @Override
    public void onEnable() {
        moduleConfig = new moduleConfig(plugin, this, "democracy", jarName);

        plugin.getCommand("democracy").setExecutor(new democracyCommand(plugin, this));
        plugin.getCommand("democracy").setTabCompleter(new democracyTabCompleter(plugin, this));
        plugin.getCommand("democracy").setPermissionMessage(getLang().get("error.noper"));
    }

    @Override
    public void onDisable() {
    }

}
