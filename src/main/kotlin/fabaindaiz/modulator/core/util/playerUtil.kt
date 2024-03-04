package fabaindaiz.modulator.core.util

import org.bukkit.Bukkit
import org.bukkit.entity.Player

fun isOnlinePlayer (playerName: String): Boolean {
    return Bukkit.getPlayer(playerName)?.isOnline ?: false
}

fun getPlayersName (players: List<Player>): List<String> {
    return players.map { it.name }
}
