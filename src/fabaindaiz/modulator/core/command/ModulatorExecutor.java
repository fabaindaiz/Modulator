package fabaindaiz.modulator.core.command;

import fabaindaiz.modulator.Modulator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ModulatorExecutor implements CommandExecutor {

    private final Modulator plugin;
    private final ModulatorCommand modCommand;

    public ModulatorExecutor(Modulator plugin, ModulatorCommand modCommand) {

        this.plugin = plugin;
        this.modCommand = modCommand;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {

        if (modCommand.moduleList.containsKey(label.toLowerCase())) {
            return modCommand.moduleList.get(label).getExecutor().onCommand(sender, command, label, args);
        }

        return true;
    }

}
