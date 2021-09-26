package fabaindaiz.modulator.core.dispatcher;

import com.google.common.collect.Lists;
import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.LanguageLoader;
import fabaindaiz.modulator.core.handler.ModuleException;
import fabaindaiz.modulator.core.modules.IModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;

/**
 * Represents a class which dispatch commands for modules
 */
public class CommandDispatcher implements CommandExecutor {

    public final Modulator plugin;
    public final IModule module;
    public final LanguageLoader lang;
    private final HashMap<String, BiFunction<CommandSender, ArrayList<String>, Boolean>> dispatcher = new HashMap<>();

    private boolean enabled = true;
    private boolean reqperm = true;
    private boolean disableOnException = false;

    /**
     * @param modulator Modulator main class
     * @param module Module main class
     */
    public CommandDispatcher(Modulator modulator, IModule module) {
        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLanguageLoader();
    }

    /**
     * Dispatch the given command for modules, returning its success
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        try {
            if (reqperm && !sender.hasPermission(module.getPermission())) {
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
            return dispatcher.getOrDefault(argsList.get(0), this::error).apply(sender, argsList);
        } catch (Exception e) {
            if (disableOnException) {
                setEnabled(false);
            }
            throw new ModuleException.ModuleCommandException(module.getName(), e);
        }
    }

    /**
     * An overridable method which represent command condition
     * @return true if a valid conditional case, otherwise false
     */
    public boolean conditions() {
        return true;
    }

    /**
     * Register a command dispatch for modules
     * @param command Command which was executed
     * @param method Method to dispatch
     */
    public void register(String command, BiFunction<CommandSender, ArrayList<String>, Boolean> method) {
        dispatcher.put(command, method);
    }

    /**
     * Sets enabled condition for a module
     * @param enabled true if an enabled module
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Sets if a module require permissions
     * @param reqperm true if a module require permission
     */
    public void setReqPerm(boolean reqperm) {
        this.reqperm = reqperm;
    }

    /**
     * Sets if a module is deactivated in case of an exception
     * @param disableonexception true if a module requires deactivation
     */
    public void setDisableOnException(boolean disableonexception) {
        this.disableOnException = disableonexception;
    }

    /**
     * Return default error for command dispatch
     * @param sender Source of the command
     * @param args Passed command arguments
     * @return true if a valid command
     */
    public boolean error(CommandSender sender, ArrayList<String> args) {
        sender.sendMessage(lang.get("error.error"));
        return true;
    }

}
