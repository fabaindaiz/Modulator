package fabaindaiz.modulator.modules.modtask;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class modtaskTabCompleter implements TabCompleter {
    static final ArrayList<String> emptyList = new ArrayList<>();
    private final Modulator plugin;
    private final languageLoader lang;

    protected modtaskTabCompleter(Modulator modulator) {
        this.plugin = modulator;
        this.lang = modulator.getConfiguration().getMainLang();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (command.getName().equalsIgnoreCase("modtask") && args.length <= 1) {
            }

        }
        return emptyList;
    }
}
