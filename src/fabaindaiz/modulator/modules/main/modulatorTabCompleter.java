package fabaindaiz.modulator.modules.main;

import fabaindaiz.modulator.modules.IModule;
import fabaindaiz.modulator.Modulator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class modulatorTabCompleter implements TabCompleter {
    static final ArrayList<String> emptyList = new ArrayList<>();
    final String[] modules1 = {"help", "modules", "reload"};
    private final Modulator plugin;
    private final IModule module;

    protected modulatorTabCompleter(Modulator modulator, IModule module) {
        this.plugin = modulator;
        this.module = module;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (plugin.commandList.containsKey(label.toLowerCase())) {
            return plugin.commandList.get(label).getTabCompleter().onTabComplete(sender, command, label, args);
        }

        if (sender instanceof Player) {
            if (label.equalsIgnoreCase("fabaindaiz/modulator") && args.length <= 1) {
                return Arrays.asList(modules1);
            }

        }
        return emptyList;
    }

}
