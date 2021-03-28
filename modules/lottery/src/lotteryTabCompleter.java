import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import fabaindaiz.modulator.modules.IModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lotteryTabCompleter implements TabCompleter {
    static final ArrayList<String> emptyList = new ArrayList<>();
    final String[] modules1 = {"help", "buy", "collect", "results"};
    private final Modulator plugin;
    private final IModule module;
    private final languageLoader lang;

    protected lotteryTabCompleter(Modulator modulator, IModule module) {

        this.plugin = modulator;
        this.module = module;
        this.lang = modulator.getConfiguration().getMainLang();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (command.getName().equalsIgnoreCase("lottery") && args.length <= 1) {
                return Arrays.asList(modules1);
            }

        }
        return emptyList;
    }

}
