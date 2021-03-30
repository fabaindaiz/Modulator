import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.langLoader;
import fabaindaiz.modulator.modules.IModule;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class zanakikListener implements Listener {

    private final Modulator plugin;
    private final IModule module;
    private final langLoader lang;
    private final String key = "zanakik.listener";

    protected zanakikListener(Modulator modulator, IModule module) {

        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLang();
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        int maxplayers = Bukkit.getServer().getMaxPlayers();
        int onlineplayers = Bukkit.getServer().getOnlinePlayers().size();
        if (onlineplayers < maxplayers) return;

        Bukkit.broadcastMessage(lang.get(key, "fullserver"));
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "zanakik");
    }
}
