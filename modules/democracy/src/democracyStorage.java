import fabaindaiz.modulator.core.config.langLoader;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class democracyStorage {

    private final langLoader lang;
    private final String key = "democracy.storage";
    private final String question;
    private final int[] votes = {0, 0};
    private final ArrayList<String> voteName = new ArrayList<>();
    private final CommandSender sender;
    private int answers = -1;

    protected democracyStorage(CommandSender sender, String question, langLoader lang) {
        this.question = question;
        this.sender = sender;
        this.lang = lang;
    }

    protected boolean vote(String voteName, int option) {
        if (this.voteName.contains(voteName)) {
            return false;
        }
        if (option >= 5) {
            return false;
        }
        votes[option]++;
        this.voteName.add(voteName);

        BaseComponent message1 = new TextComponent(this.voteName.size() + lang.get(key, "click0"));
        message1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/democracy done"));
        sender.spigot().sendMessage(message1);

        return true;
    }

    protected String getQuestion() {
        return question;
    }

    protected int getAnswers() {
        return answers;
    }

    protected void setAnswers(int answers) {
        this.answers = answers;
    }

    protected int[] getVotes() {
        return votes;
    }

}
