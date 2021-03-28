package fabaindaiz.modulator.modules.modtask;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class modtaskCommand implements CommandExecutor {
    private final Modulator plugin;
    private final languageLoader lang;

    protected modtaskCommand(Modulator modulator) {

        this.plugin = modulator;
        this.lang = modulator.getConfiguration().getMainLang();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        switch (args.length) {
            default:
                return true;
        }
    }
}