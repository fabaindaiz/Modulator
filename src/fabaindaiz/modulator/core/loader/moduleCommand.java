package fabaindaiz.modulator.core.loader;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class moduleCommand extends BukkitCommand {

    public moduleCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        return false;
    }
}
