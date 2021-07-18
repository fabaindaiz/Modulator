import fabaindaiz.modulator.core.config.langLoader;
import fabaindaiz.modulator.modules.IModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class zanakikUtil {

    private static String key = "zanakik.util";

    static protected Player[] getKickablePlayers(IModule module) {
        ArrayList<Player> playerList = new ArrayList<>();
        boolean ignorebypass = module.getConfig().getBoolean("zanakik.ignorebypass");

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (ignorebypass || !player.hasPermission("modulator.kickbypass")) {
                playerList.add(player);
            }
        });

        return playerList.toArray(new Player[0]);
    }

    static protected ItemStack getZanakik(IModule module, int num) {
        langLoader lang = module.getLang();

        ItemStack carrot = new ItemStack(Material.CARROT, num);
        ItemMeta meta = carrot.getItemMeta();
        meta.setDisplayName("Zanakik");

        List<String> loresList = new ArrayList<>();
        loresList.add(lang.get(key, "lore1"));
        loresList.add(lang.get(key, "lore2"));
        loresList.add(lang.get(key, "lore3"));
        loresList.add(lang.get(key, "lore4"));
        loresList.add(lang.get(key, "lore5"));
        meta.setLore(loresList);

        carrot.setItemMeta(meta);
        return carrot;
    }

    static protected Date getZkikExpiresDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.SECOND, 30);
        return cal.getTime();
    }

}

