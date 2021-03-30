package fabaindaiz.modulator.modules.modtask;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.langLoader;
import fabaindaiz.modulator.modules.IModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class modtaskCommand implements CommandExecutor {
    private final Modulator plugin;
    private final IModule module;
    private final langLoader lang;

    protected modtaskCommand(Modulator modulator, IModule module) {
        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLang();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        switch (args.length) {
            default:
                return true;
        }
    }
}