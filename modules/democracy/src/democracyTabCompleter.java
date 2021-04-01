import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.modules.IModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class democracyTabCompleter implements TabCompleter {
    static final ArrayList<String> emptyList = new ArrayList<>();
    final String[] modules1 = {"help", "create", "done", "cancel"};
    private final Modulator plugin;
    private final IModule module;

    protected democracyTabCompleter(Modulator modulator, IModule module) {
        this.plugin = modulator;
        this.module = module;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("modulator.democracy")){
            return emptyList;
        }
        if (sender instanceof Player) {
            if (args.length <= 1) {
                return Arrays.asList(modules1);
            }

        }
        return emptyList;
    }

}
