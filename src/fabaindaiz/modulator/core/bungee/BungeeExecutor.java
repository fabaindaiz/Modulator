package fabaindaiz.modulator.core.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BungeeExecutor extends Command {

    public BungeeExecutor(String name) {
        super(name);
    }

    public BungeeExecutor(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}