package fabaindaiz.modulator.module.modinput;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class modinputTabCompleter implements TabCompleter {
    static final ArrayList<String> emptyList = new ArrayList<>();
    private final Modulator plugin;
    private final languageLoader lang;

    protected modinputTabCompleter(Modulator modulator) {
        this.plugin = modulator;
        this.lang = modulator.getConfiguration().getLang();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (command.getName().equalsIgnoreCase("modinput") && args.length <= 1) {
            }

        }
        return emptyList;
    }
}
