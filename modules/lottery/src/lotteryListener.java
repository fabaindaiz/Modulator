import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.config.languageLoader;
import fabaindaiz.modulator.core.loader.moduleLang;
import fabaindaiz.modulator.modules.IModule;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class lotteryListener implements Listener {

    private final Modulator plugin;
    private final IModule module;
    private final moduleLang lang;
    private final lotteryStorage storage;

    protected lotteryListener(Modulator modulator, lotteryStorage storage, IModule module) {

        this.plugin = modulator;
        this.module = module;
        this.lang = module.getLang();
        this.storage = storage;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        HumanEntity clicker = event.getWhoClicked();
        Inventory inventory = event.getInventory();
        String senderName = clicker.getName();

        if (!storage.containsPlayer(clicker.getName()) || !storage.getInventory(clicker.getName()).equals(inventory)) {
            return;
        }

        event.setCancelled(true);
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        storage.click(senderName, event.getRawSlot());
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent event) {
        String senderName = event.getWhoClicked().getName();

        if (storage.containsPlayer(senderName) && storage.getInventory(senderName).equals(event.getInventory())) {
            event.setCancelled(true);
        }
    }


}