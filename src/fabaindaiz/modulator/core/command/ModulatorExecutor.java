package fabaindaiz.modulator.core.command;

import fabaindaiz.modulator.Modulator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a class which contains a single method for executing commands for Modulator
 */
public class ModulatorExecutor implements CommandExecutor {

    private final Modulator plugin;
    private final ModulatorCommand modCommand;

    /**
     * @param plugin Modulator main class
     * @param modCommand Modules register
     */
    public ModulatorExecutor(Modulator plugin, ModulatorCommand modCommand) {
        this.plugin = plugin;
        this.modCommand = modCommand;
    }

    /**
     * Executes the given command, returning its success
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if (modCommand.moduleList.containsKey(label.toLowerCase())) {
            return modCommand.moduleList.get(label).getExecutor().onCommand(sender, command, label, args);
        }

        return true;
    }

}
