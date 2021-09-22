package fabaindaiz.modulator.core.dispatcher;

import com.google.common.collect.Lists;
import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.modules.IModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * Represents a class which can suggest tab completions for modules
 */
public class TabCompleterDispatcher implements TabCompleter {

    public final Modulator plugin;
    public final IModule module;

    private final HashMap<String, Function<ArrayList<String>, List<String>>> dispatcher = new HashMap<>();

    /**
     * @param modulator Modulator main class
     * @param module Module main class
     */
    public TabCompleterDispatcher(Modulator modulator, IModule module) {
        this.plugin = modulator;
        this.module = module;
    }

    /**
     * Requests a list of possible completions for modules
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return A List of possible completions for the final argument
     */
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!sender.hasPermission(module.getPermission())) {
            return Collections.emptyList();
        }

        ArrayList<String> argsList = Lists.newArrayList(args);
        if (args.length == 0) {
            argsList.add("");
        }
        return dispatcher.getOrDefault(argsList.get(0), this::defaultList).apply(argsList);
    }

    /**
     * Register a command tab completer for modules
     * @param command Command which was executed
     * @param method Method to dispatch
     */
    public void register(String command, Function<ArrayList<String>, List<String>> method) {
        dispatcher.put(command, method);
    }

    /**
     * Gets the default commands list
     * @param args Passed command arguments
     * @return The default comands list
     */
    private List<String> defaultList(ArrayList<String> args) {
        return dispatcher.get("").apply(args);
    }

    /**
     * Gets an empty list
     * @param args Passed command arguments
     * @return An empty list
     */
    public List<String> emptyList(ArrayList<String> args) {
        return Collections.emptyList();
    }

}
