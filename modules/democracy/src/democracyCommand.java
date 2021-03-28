import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import fabaindaiz.modulator.core.loader.moduleLang;
import fabaindaiz.modulator.modules.IModule;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static fabaindaiz.modulator.util.playersUtil.isOnlinePlayer;

public class democracyCommand implements CommandExecutor {
    final String[][] answers;
    private final boolean enabled;
    private final boolean noname;
    private final Modulator plugin;
    private final IModule module;
    private final moduleLang lang;
    private final HashMap<String, democracyStorage> consider = new HashMap<>();

    protected democracyCommand(Modulator modulator, IModule module) {

        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLang();
        this.enabled = module.getConfig().getBoolean("democracy.enable");
        this.noname = module.getConfig().getBoolean("democracy.noname");
        this.answers = new String[][]{{lang.get("democracy.ans11"), lang.get("democracy.ans12")},
                {lang.get("democracy.ans21"), lang.get("democracy.ans22")}, {lang.get("democracy.ans31"),
                lang.get("democracy.ans32")}, {lang.get("democracy.ans41"), lang.get("democracy.ans42")},
                {lang.get("democracy.ans51"), lang.get("democracy.ans52")}};
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!this.enabled) {
            sender.sendMessage(lang.get("democracy.disable1"));
            return true;
        }

        switch (args.length) {
            case 0:
                sender.sendMessage(lang.get("democracy.info1"));
                sender.sendMessage(lang.get("democracy.info2"));
                sender.sendMessage(lang.get("democracy.info3"));
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
                        sender.sendMessage(lang.get("democracy.interact4"));
                        return true;
                    default:
                        sender.sendMessage(lang.get("democracy.error1"));
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
                        sender.sendMessage(lang.get("democracy.interact4"));
                        return true;
                    default:
                        sender.sendMessage(lang.get("democracy.error1"));
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
                        sender.sendMessage(lang.get("democracy.error1"));
                        return true;
                }
            default:
                switch (args[0]) {
                    case "create":
                        this.create(sender, args);
                        return true;
                    default:
                        sender.sendMessage(lang.get("democracy.error1"));
                        return true;
                }
        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage(lang.get("democracy.help1"));
        sender.sendMessage(lang.get("democracy.help2"));
        sender.sendMessage(lang.get("democracy.help3"));
        sender.sendMessage(lang.get("democracy.help4"));
    }

    private void done(CommandSender sender) {
        String senderName = sender.getName();
        StringBuilder text = new StringBuilder();

        if (!consider.containsKey(senderName)) {
            sender.sendMessage(lang.get("democracy.error2"));
            return;
        }

        democracyStorage storage = consider.get(senderName);
        int[] votes = storage.getVotes();

        text.append(lang.get("democracy.done2"));
        for (int i = 0; i < 2; i++) {
            text.append(answers[storage.getAnswers()][i]).append(" ");
            text.append(votes[i]).append("   ");
        }

        Bukkit.broadcastMessage(lang.get("democracy.done1") + storage.getQuestion());
        Bukkit.broadcastMessage(text.toString());
        consider.remove(senderName);
    }

    private void cancel(CommandSender sender) {
        String senderName = sender.getName();

        if (consider.containsKey(senderName)) {
            consider.remove(senderName);
            Bukkit.broadcastMessage(lang.get("democracy.interact2"));
            return;
        }
        sender.sendMessage(lang.get("democracy.error2"));
    }

    private void vote(String[] args) {
        Player player = Bukkit.getPlayer(args[2]);
        int vote = Integer.parseInt(args[3]);

        if (consider.size() == 0) {
            player.sendMessage(lang.get("democracy.error2"));
            return;
        }

        if (!consider.containsKey(args[1]) || vote >= 2) {
            player.sendMessage(lang.get("democracy.error1"));
            return;
        }

        democracyStorage storage = consider.get(args[1]);
        if (storage.vote(player.getName(), vote)) {
            player.sendMessage(lang.get("democracy.interact5") + answers[storage.getAnswers()][vote]);
            return;
        }
        player.sendMessage(lang.get("democracy.error4") + answers[storage.getAnswers()][vote]);
    }

    private void create(CommandSender sender, String[] args) {
        String senderName = sender.getName();

        if (consider.containsKey(senderName)) {
            sender.sendMessage(lang.get("democracy.error3"));
            return;
        }

        StringBuilder text = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            text.append(args[i]).append(" ");
        }

        consider.put(senderName, new democracyStorage(sender, text.toString(), lang));

        BaseComponent message1 = new TextComponent("option '0': " + lang.get("democracy.ans11") + lang.get("democracy.ans12"));
        BaseComponent message2 = new TextComponent("option '1': " + lang.get("democracy.ans21") + lang.get("democracy.ans22"));
        BaseComponent message3 = new TextComponent("option '2': " + lang.get("democracy.ans31") + lang.get("democracy.ans32"));
        BaseComponent message4 = new TextComponent("option '3': " + lang.get("democracy.ans41") + lang.get("democracy.ans42"));
        BaseComponent message5 = new TextComponent("option '4': " + lang.get("democracy.ans51") + lang.get("democracy.ans52"));
        message1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/democracy createConfirm 0"));
        message2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/democracy createConfirm 1"));
        message3.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/democracy createConfirm 2"));
        message4.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/democracy createConfirm 3"));
        message5.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/democracy createConfirm 4"));

        if (isOnlinePlayer(senderName)) {
            sender.sendMessage(lang.get("democracy.click1"));
            Bukkit.getPlayer(senderName).spigot().sendMessage(message1);
            Bukkit.getPlayer(senderName).spigot().sendMessage(message2);
            Bukkit.getPlayer(senderName).spigot().sendMessage(message3);
            Bukkit.getPlayer(senderName).spigot().sendMessage(message4);
            Bukkit.getPlayer(senderName).spigot().sendMessage(message5);
        } else {
            sender.sendMessage(lang.get("democracy.click2"));
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
        message3.append(lang.get("democracy.info4"));
        message3.append(message1);
        message3.append(message2);

        String showName = senderName;
        if (this.noname || sender.hasPermission("modulator.anondemocracy")) {
            showName = lang.get("democracy.interact6");
        }
        Bukkit.broadcastMessage(lang.get("democracy.interact3") + showName);

        Bukkit.broadcastMessage(lang.get("democracy.done3") + consider.get(senderName).getQuestion());
        Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage(message3.create()));
    }


}
