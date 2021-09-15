import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.modules.IModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class testTabCompleter extends TabCompleterDispatcher {

    final List<String> info = Arrays.asList(new String[]{"help"});

    protected testTabCompleter(Modulator modulator, IModule module) {
        super(modulator, module);

        register("", this::info);
        register("help", super::emptyList);
    }

    private List<String> info(ArrayList<String> args) {
        return info;
    }

}