package fabaindaiz.modulator.modules.modinput;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.CommandDispatcher;
import fabaindaiz.modulator.core.modules.IModule;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class modinputCommand extends CommandDispatcher {

    public modinputCommand(Modulator modulator, IModule module) {
        super(modulator, module);

        register("vote", this::vote);
    }

    private Boolean vote() {
        if (getArgs().size() != 3) {
            return error();
        }
        CommandSender sender = getSender();
        ArrayList args = getArgs();
        String string = "democracy vote " + args.get(1) + " " + sender.getName() + " " + args.get(2);
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), string);
        return true;
    }
}
