import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.CommandDispatcher;
import fabaindaiz.modulator.core.dispatcher.SubCommandDispatcher;
import fabaindaiz.modulator.core.modules.IModule;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static fabaindaiz.modulator.core.util.playersUtil.isOnlinePlayer;

public class democracyCommand extends CommandDispatcher {
    final Random rnd = new Random();

    private final boolean noname;
    private final String key = "democracy.command";
    private final String ansKey = "democracy.answers";
    private final HashMap<String, democracyStorage> consider = new HashMap<>();
    private String[][] answers;

    private final SubCommandDispatcher cancelDispatcher = new SubCommandDispatcher(this);

    protected democracyCommand(Modulator modulator, IModule module) {
        super(modulator, module);

        this.noname = module.getConfiguration().getBoolean("democracy.noname");

        List<String[]> tempList = new ArrayList<>();
        for (String[] s : new String[][]{{"11", "12"}, {"21", "22"}, {"31", "32"}, {"41", "42"}, {"51", "52"}}) {
            tempList.add(new String[]{lang.get(ansKey, s[0]), lang.get(ansKey, s[1])});
        }
        this.answers = new String[tempList.size()][2];
        this.answers = tempList.toArray(answers);
        setEnabled(module.getConfiguration().getBoolean("democracy.enable"));

        register("", this::info);
        register("help", this::help);
        register("done", this::done);
        register("cancel", this::cancel);
        register("vote", this::vote);
        register("create", this::create);
        register("createConfirm", this::createConfirm);

        cancelDispatcher.register(1, this::cancel1);
        cancelDispatcher.register(2, this::cancel2);

    }

    private boolean info(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        sender.sendMessage(lang.get(key, "info1"));
        sender.sendMessage(lang.get(key, "info2"));
        sender.sendMessage(lang.get(key, "info3"));
        return true;
    }

    private boolean help(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        sender.sendMessage(lang.get(key, "help1"));
        sender.sendMessage(lang.get(key, "help2"));
        sender.sendMessage(lang.get(key, "help3"));
        sender.sendMessage(lang.get(key, "help4"));
        return true;
    }

    private boolean done(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 1) {
            return error(sender, args);
        }
        String senderName = sender.getName();
        StringBuilder text = new StringBuilder();

        if (!consider.containsKey(senderName)) {
            sender.sendMessage(lang.get(key, "error2"));
            return true;
        }

        democracyStorage storage = consider.get(senderName);
        int[] votes = storage.getVotes();

        text.append(lang.get(key, "done2"));
        for (int i = 0; i < 2; i++) {
            text.append(answers[storage.getAnswers()][i]).append(" ");
            text.append(votes[i]).append("   ");
        }

        Bukkit.broadcastMessage(lang.get(key, "done1") + storage.getQuestion());
        Bukkit.broadcastMessage(text.toString());
        consider.remove(senderName);
        return true;
    }

    private boolean cancel(CommandSender sender, ArrayList<String> args) {
        return cancelDispatcher.dispatch(sender, args).apply(sender, args);
    }

    private boolean cancel1(CommandSender sender, ArrayList<String> args) {
        sender.sendMessage(lang.get(key, "interact4"));
        return true;
    }

    private boolean cancel2(CommandSender sender, ArrayList<String> args) {
        if (!args.get(1).equals("confirm")) {
            return error(sender, args);
        }
        String senderName = sender.getName();
        if (consider.containsKey(senderName)) {
            consider.remove(senderName);
            Bukkit.broadcastMessage(lang.get(key, "interact2"));
            return true;
        }
        sender.sendMessage(lang.get(key, "error2"));
        return true;
    }

    private boolean vote(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 4) {
            return error(sender, args);
        }
        Player player = Bukkit.getPlayer(sender.getName());
        int vote = Integer.parseInt(args.get(2));

        if (consider.size() == 0) {
            player.sendMessage(lang.get(key, "error2"));
            return true;
        }
        if (!consider.containsKey(args.get(1)) || vote >= 2) {
            return error(sender, args);
        }
        democracyStorage storage = consider.get(args.get(1));

        if (!storage.getValCode().equals(args.get(3))) {
            return error(sender, args);
        }

        if (storage.vote(player.getName(), vote)) {
            player.sendMessage(lang.get(key, "interact5") + answers[storage.getAnswers()][vote]);
            return true;
        }
        player.sendMessage(lang.get(key, "error4") + answers[storage.getAnswers()][vote]);
        return true;
    }

    private boolean create(CommandSender sender, ArrayList<String> args) {
        String senderName = sender.getName();

        if (consider.containsKey(senderName)) {
            sender.sendMessage(lang.get(key, "error3"));
            return true;
        }

        StringBuilder text = new StringBuilder();
        for (int i = 1; i < args.size(); i++) {
            text.append(args.get(i)).append(" ");
        }

        String valCode = String.valueOf(rnd.nextInt());
        consider.put(senderName, new democracyStorage(sender, text.toString(), lang, valCode));

        BaseComponent message1 = new TextComponent("option '0': " + lang.get(ansKey, "11") + lang.get(ansKey, "12"));
        BaseComponent message2 = new TextComponent("option '1': " + lang.get(ansKey, "21") + lang.get(ansKey, "22"));
        BaseComponent message3 = new TextComponent("option '2': " + lang.get(ansKey, "31") + lang.get(ansKey, "32"));
        BaseComponent message4 = new TextComponent("option '3': " + lang.get(ansKey, "41") + lang.get(ansKey, "42"));
        BaseComponent message5 = new TextComponent("option '4': " + lang.get(ansKey, "51") + lang.get(ansKey, "52"));
        message1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/democracy createConfirm 0"));
        message2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/democracy createConfirm 1"));
        message3.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/democracy createConfirm 2"));
        message4.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/democracy createConfirm 3"));
        message5.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/democracy createConfirm 4"));

        if (isOnlinePlayer(senderName)) {
            sender.sendMessage(lang.get(key, "click1"));
            Bukkit.getPlayer(senderName).spigot().sendMessage(message1);
            Bukkit.getPlayer(senderName).spigot().sendMessage(message2);
            Bukkit.getPlayer(senderName).spigot().sendMessage(message3);
            Bukkit.getPlayer(senderName).spigot().sendMessage(message4);
            Bukkit.getPlayer(senderName).spigot().sendMessage(message5);
        } else {
            sender.sendMessage(lang.get(key, "click2"));
            sender.sendMessage("Validation Code: " + valCode);
            sender.sendMessage(message1.toPlainText());
            sender.sendMessage(message2.toPlainText());
            sender.sendMessage(message3.toPlainText());
            sender.sendMessage(message4.toPlainText());
            sender.sendMessage(message5.toPlainText());
        }
        return true;
    }

    private boolean createConfirm(CommandSender sender, ArrayList<String> args) {
        if (args.size() != 2) {
            return error(sender, args);
        }
        int option = Integer.parseInt(args.get(1));
        String senderName = sender.getName();
        democracyStorage storage = consider.get(senderName);

        if (storage.getAnswers() != -1) {
            return true;
        }
        storage.setAnswers(option);

        String valCode = storage.getValCode();

        BaseComponent message1 = new TextComponent(answers[option][0]);
        BaseComponent message2 = new TextComponent(answers[option][1]);
        message1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/democracy vote " + senderName + " 0 " + valCode));
        message2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/democracy vote " + senderName + " 1 " + valCode));

        ComponentBuilder message3 = new ComponentBuilder();
        message3.append(lang.get(key, "info4"));
        message3.append(message1);
        message3.append(message2);

        String showName = senderName;
        if (this.noname || sender.hasPermission("modulator.anondemocracy")) {
            showName = lang.get(key, "interact6");
        }
        Bukkit.broadcastMessage(lang.get(key, "interact3") + showName);

        Bukkit.broadcastMessage(lang.get(key, "done3") + consider.get(senderName).getQuestion());
        Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage(message3.create()));
        return true;
    }

}
