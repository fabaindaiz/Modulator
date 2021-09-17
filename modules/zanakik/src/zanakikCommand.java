import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.CommandDispatcher;
import fabaindaiz.modulator.core.dispatcher.SubCommandDispatcher;
import fabaindaiz.modulator.core.modules.IModule;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

import static fabaindaiz.modulator.core.util.playersUtil.isOnlinePlayer;

public class zanakikCommand extends CommandDispatcher {
    final Random rnd = new Random();

    private final boolean noplayerzkik;
    private final boolean ignorebypass;
    private final String key = "zanakik.command";

    private final SubCommandDispatcher getzkikDispatcher = new SubCommandDispatcher(this);

    protected zanakikCommand(Modulator modulator, IModule module) {
        super(modulator, module);

        this.noplayerzkik = module.getConfiguration().getBoolean("zanakik.noplayerzkik");
        this.ignorebypass = module.getConfiguration().getBoolean("zanakik.ignorebypass");
        setEnabled(module.getConfiguration().getBoolean("zanakik.enable"));

        register("", this::zkik);
        register("help", this::help);
        register("player", this::zkikPlayer);
        register("get", this::getzkik);

        getzkikDispatcher.register(1, this::getzkik1);
        getzkikDispatcher.register(2, this::getzkik2);
        getzkikDispatcher.register(3, this::getzkik3);

    }

    private boolean help(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        sender.sendMessage(lang.get(key, "help1"));
        sender.sendMessage(lang.get(key, "help2"));
        sender.sendMessage(lang.get(key, "help3"));
        return true;
    }

    private boolean zkik(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        if (this.noplayerzkik && sender instanceof Player) {
            sender.sendMessage(lang.get(key, "error5"));
            return true;
        }
        Player[] players = zanakikUtil.getKickablePlayers(module);
        if (players.length < 1) {
            Bukkit.broadcastMessage(lang.get(key, "error2"));
            return true;
        }

        int rndindex = rnd.nextInt(players.length);
        Player rndplayer = players[rndindex];
        kik(rndplayer);
        return true;
    }

    private boolean zkikPlayer(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 2) {
            return error(sender, args);
        }
        String playerName = args.get(1);

        if (!isOnlinePlayer(playerName)) {
            sender.sendMessage(lang.get(key, "error3"));
            return true;
        }
        Player selP = Bukkit.getPlayer(playerName);
        if (selP.hasPermission("modulator.kickbypass") && !this.ignorebypass) {
            sender.sendMessage(lang.get(key, "error4"));
            return true;
        }

        sender.sendMessage(lang.get(key, "kikmessage2") + playerName);
        kik(selP);
        return true;
    }

    private boolean getzkik(CommandSender sender, ArrayList<String> args) {
        return getzkikDispatcher.dispatch(sender, args).apply(sender, args);
    }

    private boolean getzkik1(CommandSender sender, ArrayList<String> args) {
        if (isOnlinePlayer(sender.getName())) {
            Bukkit.getPlayer(sender.getName()).getInventory().addItem(zanakikUtil.getZanakik(module, 1));
        }
        return true;
    }

    private boolean getzkik2(CommandSender sender, ArrayList<String> args) {
        int num = Integer.parseInt(args.get(1));
        if (isOnlinePlayer(sender.getName())) {
            Bukkit.getPlayer(sender.getName()).getInventory().addItem(zanakikUtil.getZanakik(module, num < 64 ? num : 64));
        }
        return true;
    }

    private boolean getzkik3(CommandSender sender, ArrayList<String> args) {
        String name = args.get(2);
        int num = Integer.parseInt(args.get(1));
        if (isOnlinePlayer(name)) {
            Bukkit.getPlayer(name).getInventory().addItem(zanakikUtil.getZanakik(module, num < 64 ? num : 64));
        }
        return true;
    }

    protected void kik(Player player) {
        player.getInventory().addItem(zanakikUtil.getZanakik(module, 1));

        Bukkit.getBanList(Type.NAME).addBan(player.getName(), "Zanakik", zanakikUtil.getZkikExpiresDate(), "zanakik");
        Bukkit.broadcastMessage(player.getName() + lang.get(key, "kikmessage1"));

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> player.kickPlayer("Zanakik"), 300L);
    }


}