package fabaindaiz.modulator.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class playersUtil {

    static public boolean isOnlinePlayer(String playerName) {
        ArrayList<String> onlinePlayers = new ArrayList<>();

        Bukkit.getOnlinePlayers().forEach(player -> onlinePlayers.add(player.getName()));

        return onlinePlayers.contains(playerName);
    }

    static public List<String> getPlayerNameList(Player[] players) {
        List<String> playerNameList = new ArrayList<>();

        for (Player p : players) {
            playerNameList.add(p.getName());
        }

        return playerNameList;
    }
}
