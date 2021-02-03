package fabaindaiz.modulator.module.zanakik;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class zanakikListener implements Listener {

    private final Modulator plugin;
    private final languageLoader lang;

    protected zanakikListener(Modulator modulator) {

        this.plugin = modulator;
        this.lang = modulator.getConfiguration().getLang();
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        int maxplayers = Bukkit.getServer().getMaxPlayers();
        int onlineplayers = Bukkit.getServer().getOnlinePlayers().size();
        if (onlineplayers < maxplayers) return;

        Bukkit.broadcastMessage(lang.get("zanakik.fullserver"));
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "zanakik");
    }
}
