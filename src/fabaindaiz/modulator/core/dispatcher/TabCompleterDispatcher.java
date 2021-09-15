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

public class TabCompleterDispatcher implements TabCompleter {

    public final Modulator plugin;
    public final IModule module;

    private final HashMap<String, Function<ArrayList<String>, List<String>>> dispatcher = new HashMap<>();
    private ArrayList<String> args;

    public TabCompleterDispatcher(Modulator modulator, IModule module) {
        this.plugin = modulator;
        this.module = module;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!sender.hasPermission(module.getPermission())) {
            return Collections.emptyList();
        }

        ArrayList<String> argsList = Lists.newArrayList(args);
        if (args.length == 0) {
            argsList.add("");
        }
        try {
            return dispatcher.getOrDefault(argsList.get(0), this::defaultList).apply(argsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void register(String command, Function<ArrayList<String>, List<String>> method) {
        dispatcher.put(command, method);
    }

    private List<String> defaultList(ArrayList<String> args) {
        return dispatcher.get("").apply(args);
    }

    public List<String> emptyList(ArrayList<String> args) {
        return Collections.emptyList();
    }

}
