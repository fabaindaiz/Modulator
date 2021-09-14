import com.google.common.collect.Lists;
import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dispatcher.CommandDispatcher;
import fabaindaiz.modulator.core.modules.IModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

import static fabaindaiz.modulator.core.util.playersUtil.isOnlinePlayer;

public class orderCommand extends CommandDispatcher {
    private final String key = "order.command";

    protected orderCommand(Modulator modulator, IModule module) {
        super(modulator, module);

        setPermission("modulator.don");
        register("", this::info);
        register("help", this::info);
        register("inv", this::order);
    }

    private boolean info() {
        if (getArgs().size() != 1) {
            return error();
        }
        CommandSender sender = getSender();
        sender.sendMessage(lang.get(key, "info1"));
        sender.sendMessage(lang.get(key, "info2"));
        return true;
    }

    private boolean help() {
        if (getArgs().size() != 1) {
            return error();
        }
        CommandSender sender = getSender();
        sender.sendMessage(lang.get(key, "help1"));
        sender.sendMessage(lang.get(key, "help2"));
        sender.sendMessage(lang.get(key, "help3"));
        return true;
    }

    private boolean order() {
        if (getArgs().size() != 1) {
            return error();
        }
        CommandSender sender = getSender();
        if (!isOnlinePlayer(sender.getName())){
            sender.sendMessage(lang.get(key, "error2"));
            return true;
        }
        Player player = Bukkit.getPlayer(sender.getName());
        List<ItemStack> list = Lists.newArrayList(player.getInventory().getStorageContents());
        Collections.sort(list, (t1, t2) -> {
            Material obj1 = (t1 == null)? Material.AIR : t1.getType();
            Material obj2 = (t2 == null)? Material.AIR : t2.getType();
            return obj1.compareTo(obj2);
        });
        player.getInventory().setStorageContents(list.toArray(new ItemStack[list.size()]));
        player.updateInventory();
        return true;
    }

}