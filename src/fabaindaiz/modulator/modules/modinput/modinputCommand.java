package fabaindaiz.modulator.modules.modinput;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.LanguageLoader;
import fabaindaiz.modulator.core.modules.IModule;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class modinputCommand implements CommandExecutor {
    private final Modulator plugin;
    private final IModule module;
    private final LanguageLoader lang;

    public modinputCommand(Modulator modulator, IModule module) {
        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLanguageLoader();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        switch (args.length) {
            case 3:
                switch (args[0]) {
                    case "vote":
                        String string = "democracy vote " + args[1] + " " + sender.getName() + " " + args[2];
                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), string);
                        return true;
                    default:
                        sender.sendMessage(lang.get("modinput.error1"));
                        sender.sendMessage(lang.get("modinput.error2"));
                        return true;
                }
            default:
                sender.sendMessage(lang.get("modinput.error1"));
                sender.sendMessage(lang.get("modinput.error2"));
                return true;
        }
    }
}
