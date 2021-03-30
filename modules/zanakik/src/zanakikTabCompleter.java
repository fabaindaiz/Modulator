import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.modules.IModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static fabaindaiz.modulator.util.playersUtil.getPlayerNameList;

public class zanakikTabCompleter implements TabCompleter {
    static final ArrayList<String> emptyList = new ArrayList<>();
    final String[] modules1 = {"getzkik", "help"};
    private final Modulator plugin;
    private final IModule module;

    protected zanakikTabCompleter(Modulator modulator, IModule module) {
        this.plugin = modulator;
        this.module = module;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (command.getName().equalsIgnoreCase("zkik") && args.length <= 1) {
                return getPlayerNameList(zanakikUtil.getKickablePlayers(plugin));
            }
            if (command.getName().equalsIgnoreCase("zanakik") && args.length <= 1) {
                return Arrays.asList(modules1);
            }
        }
        return emptyList;
    }

}
