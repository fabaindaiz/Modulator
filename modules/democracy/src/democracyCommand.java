import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.configuration.LanguageLoader;
import fabaindaiz.modulator.core.modules.IModule;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static fabaindaiz.modulator.core.util.playersUtil.isOnlinePlayer;

public class democracyCommand implements CommandExecutor {
    private final boolean enabled;
    private final boolean noname;
    private final Modulator plugin;
    private final IModule module;
    private final LanguageLoader lang;
    private final String key = "democracy.command";
    private final String ansKey = "democracy.answers";
    private final HashMap<String, democracyStorage> consider = new HashMap<>();
    private String[][] answers;

    protected democracyCommand(Modulator modulator, IModule module) {

        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLanguageLoader();
        this.enabled = module.getConfiguration().getBoolean("democracy.enable");
        this.noname = module.getConfiguration().getBoolean("democracy.noname");

        List<String[]> tempList = new ArrayList<>();
        for (String[] s : new String[][]{{"11", "12"}, {"21", "22"}, {"31", "32"}, {"41", "42"}, {"51", "52"}}) {
            tempList.add(new String[]{lang.get(ansKey, s[0]), lang.get(ansKey, s[1])});
        }
        this.answers = new String[tempList.size()][2];
        this.answers = tempList.toArray(answers);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!this.enabled) {
            sender.sendMessage(lang.get(key, "disable1"));
            return true;
        }
        if (!sender.hasPermission("modulator.democracy")) {
            sender.sendMessage(lang.get("error.noper"));
            return true;
        }

        switch (args.length) {
            case 0:
                sender.sendMessage(lang.get(key, "info1"));
                sender.sendMessage(lang.get(key, "info2"));
                sender.sendMessage(lang.get(key, "info3"));
                // TODO Complete this method
                return true;
            case 1:
                switch (args[0]) {
                    case "help":
                        this.help(sender);
                        return true;
                    case "done":
                        this.done(sender);
                        return true;
                    case "cancel":
                        sender.sendMessage(lang.get(key, "interact4"));
                        return true;
                    default:
                        sender.sendMessage(lang.get(key, "error1"));
                        return true;
                }
            case 2:
                switch (args[0]) {
                    case "createConfirm":
                        this.createConfirm(sender, Integer.parseInt(args[1]));
                        return true;
                    case "cancel":
                        if (args[1].equals("confirm")) {
                            this.cancel(sender);
                            return true;
                        }
                        sender.sendMessage(lang.get(key, "interact4"));
                        return true;
                    default:
                        sender.sendMessage(lang.get(key, "error1"));
                        return true;
                }
            case 4:
                switch (args[0]) {
                    case "vote":
                        this.vote(args);
                        return true;
                    case "create":
                        this.create(sender, args);
                        return true;
                    default:
                        sender.sendMessage(lang.get(key, "error1"));
                        return true;
                }
            default:
                switch (args[0]) {
                    case "create":
                        this.create(sender, args);
                        return true;
                    default:
                        sender.sendMessage(lang.get(key, "error1"));
                        return true;
                }
        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage(lang.get(key, "help1"));
        sender.sendMessage(lang.get(key, "help2"));
        sender.sendMessage(lang.get(key, "help3"));
        sender.sendMessage(lang.get(key, "help4"));
    }

    private void done(CommandSender sender) {
        String senderName = sender.getName();
        StringBuilder text = new StringBuilder();

        if (!consider.containsKey(senderName)) {
            sender.sendMessage(lang.get(key, "error2"));
            return;
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
    }

    private void cancel(CommandSender sender) {
        String senderName = sender.getName();

        if (consider.containsKey(senderName)) {
            consider.remove(senderName);
            Bukkit.broadcastMessage(lang.get(key, "interact2"));
            return;
        }
        sender.sendMessage(lang.get(key, "error2"));
    }

    private void vote(String[] args) {
        Player player = Bukkit.getPlayer(args[2]);
        int vote = Integer.parseInt(args[3]);

        if (consider.size() == 0) {
            player.sendMessage(lang.get(key, "error2"));
            return;
        }

        if (!consider.containsKey(args[1]) || vote >= 2) {
            player.sendMessage(lang.get(key, "error1"));
            return;
        }

        democracyStorage storage = consider.get(args[1]);
        if (storage.vote(player.getName(), vote)) {
            player.sendMessage(lang.get(key, "interact5") + answers[storage.getAnswers()][vote]);
            return;
        }
        player.sendMessage(lang.get(key, "error4") + answers[storage.getAnswers()][vote]);
    }

    private void create(CommandSender sender, String[] args) {
        String senderName = sender.getName();

        if (consider.containsKey(senderName)) {
            sender.sendMessage(lang.get(key, "error3"));
            return;
        }

        StringBuilder text = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            text.append(args[i]).append(" ");
        }

        consider.put(senderName, new democracyStorage(sender, text.toString(), lang));

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
            sender.sendMessage(message1.toPlainText());
            sender.sendMessage(message2.toPlainText());
            sender.sendMessage(message3.toPlainText());
            sender.sendMessage(message4.toPlainText());
            sender.sendMessage(message5.toPlainText());
        }
    }

    private void createConfirm(CommandSender sender, int option) {
        String senderName = sender.getName();
        democracyStorage storage = consider.get(senderName);

        if (storage.getAnswers() != -1) {
            return;
        }
        storage.setAnswers(option);

        BaseComponent message1 = new TextComponent(answers[option][0]);
        BaseComponent message2 = new TextComponent(answers[option][1]);
        message1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/modinput vote " + senderName + " 0"));
        message2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/modinput vote " + senderName + " 1"));

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
    }


}
