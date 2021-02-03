package fabaindaiz.modulator.module.zanakik;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
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

    static protected Player[] getKickablePlayers(Modulator modulator) {
        ArrayList<Player> playerList = new ArrayList<>();
        boolean ignorebypass = modulator.getConfiguration().getConfig().getBoolean("zanakik.ignorebypass");

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (ignorebypass || !player.hasPermission("modulator.kickbypass")) {
                playerList.add(player);
            }
        });

        return playerList.toArray(new Player[0]);
    }

    static protected ItemStack getZanakik(Modulator modulator, int num) {
        languageLoader lang = modulator.getConfiguration().getLang();

        ItemStack carrot = new ItemStack(Material.CARROT, num);
        ItemMeta meta = carrot.getItemMeta();
        meta.setDisplayName("Zanakik");

        List<String> loresList = new ArrayList<>();
        loresList.add(lang.get("zanakik.lore1"));
        loresList.add(lang.get("zanakik.lore2"));
        loresList.add(lang.get("zanakik.lore3"));
        loresList.add(lang.get("zanakik.lore4"));
        loresList.add(lang.get("zanakik.lore5"));
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

