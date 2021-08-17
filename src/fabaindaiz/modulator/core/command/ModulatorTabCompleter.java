package fabaindaiz.modulator.core.command;

import fabaindaiz.modulator.Modulator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ModulatorTabCompleter implements TabCompleter {

    private final Modulator plugin;
    private final ModulatorCommand modCommand;

    public ModulatorTabCompleter(Modulator plugin, ModulatorCommand modCommand) {

        this.plugin = plugin;
        this.modCommand = modCommand;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {

        if (modCommand.moduleList.containsKey(label.toLowerCase())) {
            return modCommand.moduleList.get(label).getTabCompleter().onTabComplete(sender, command, label, args);
        }

        return Collections.emptyList();
    }

}
