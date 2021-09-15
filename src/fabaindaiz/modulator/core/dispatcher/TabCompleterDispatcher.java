package fabaindaiz.modulator.core.dispatcher;

import com.google.common.collect.Lists;
import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.modules.IModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.Callable;

public class TabCompleterDispatcher implements TabCompleter {

    public final Modulator plugin;
    public final IModule module;

    private final HashMap<String, Callable<List<String>>> dispatcher = new HashMap<>();
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

        this.args = Lists.newArrayList(args);
        if (args.length == 0){
            this.args.add("");
        }
        try {
            return dispatcher.getOrDefault(this.args.get(0), this::defaultList).call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void register(String command, Callable<List<String>> method) {
        dispatcher.put(command, method);
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    private List<String> defaultList() throws Exception {
        return dispatcher.get("").call();
    }

    public List<String> emptyList() {
        return Collections.emptyList();
    }

}
