package fabaindaiz.modulator.core.command;

import fabaindaiz.modulator.Modulator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Represents a class which can suggest tab completions for Modulator
 */
public class ModulatorTabCompleter implements TabCompleter {

    private final Modulator plugin;
    private final ModulatorCommand modCommand;

    /**
     * @param plugin Modulator main class
     * @param modCommand Modules register
     */
    public ModulatorTabCompleter(Modulator plugin, ModulatorCommand modCommand) {
        this.plugin = plugin;
        this.modCommand = modCommand;
    }

    /**
     * Requests a list of possible completions for a command argument
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return A List of possible completions for the final argument
     */
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if (modCommand.moduleList.containsKey(label.toLowerCase())) {
            return modCommand.moduleList.get(label).getTabCompleter().onTabComplete(sender, command, label, args);
        }

        return Collections.emptyList();
    }

}
