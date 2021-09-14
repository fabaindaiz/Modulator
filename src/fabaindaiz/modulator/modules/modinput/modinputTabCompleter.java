package fabaindaiz.modulator.modules.modinput;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.modules.IModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class modinputTabCompleter implements TabCompleter {
    private final Modulator plugin;
    private final IModule module;

    protected modinputTabCompleter(Modulator modulator, IModule module) {
        this.plugin = modulator;
        this.module = module;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player) {
            if (label.equalsIgnoreCase("modinput") && args.length <= 1) {
            }

        }
        return Collections.emptyList();
    }
}
