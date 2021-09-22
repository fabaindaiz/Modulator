package fabaindaiz.modulator.core.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class playersUtil {

    /**
     * Return if player name corresponds a online player
     * @param playerName Player name
     * @return true if a online player
     */
    static public boolean isOnlinePlayer(String playerName) {
        ArrayList<String> onlinePlayers = new ArrayList<>();

        Bukkit.getOnlinePlayers().forEach(player -> onlinePlayers.add(player.getName()));

        return onlinePlayers.contains(playerName);
    }

    /**
     * Return a player list from a player array
     * @param players Player array
     * @return Player list
     */
    static public List<String> getPlayerNameList(Player[] players) {
        List<String> playerNameList = new ArrayList<>();

        for (Player p : players) {
            playerNameList.add(p.getName());
        }

        return playerNameList;
    }
}
