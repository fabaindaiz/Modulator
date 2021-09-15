package fabaindaiz.modulator.core.dispatcher;

import com.google.common.collect.Lists;
import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.LanguageLoader;
import fabaindaiz.modulator.core.modules.IModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;

public class CommandDispatcher implements CommandExecutor {

    public final Modulator plugin;
    public final IModule module;
    public final LanguageLoader lang;
    private final HashMap<String, BiFunction<CommandSender, ArrayList<String>, Boolean>> dispatcher = new HashMap<>();

    private boolean enabled = true;
    private ArrayList<String> args;

    public CommandDispatcher(Modulator modulator, IModule module) {
        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLanguageLoader();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!sender.hasPermission(module.getPermission())) {
            sender.sendMessage(lang.get("error.noper"));
            return true;
        } else if (!this.enabled) {
            sender.sendMessage(lang.get("error.disabled"));
            return true;
        } else if (!conditions()) {
            return true;
        }

        ArrayList<String> argsList = Lists.newArrayList(args);
        if (args.length == 0) {
            argsList.add("");
        }
        try {
            return dispatcher.getOrDefault(argsList.get(0), this::error).apply(sender, argsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean conditions() {
        return true;
    }

    public void register(String command, BiFunction<CommandSender, ArrayList<String>, Boolean> method) {
        dispatcher.put(command, method);
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public boolean error(CommandSender sender, ArrayList<String> args) {
        sender.sendMessage(lang.get("error.error"));
        return true;
    }

}
