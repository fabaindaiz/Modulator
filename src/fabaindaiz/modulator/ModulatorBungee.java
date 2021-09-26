package fabaindaiz.modulator;

import fabaindaiz.modulator.core.handler.ModulatorException;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Represents the Modulator Bungee main class
 */
public class ModulatorBungee extends Plugin {

    /**
     * Called when this plugin is enabled
     */
    @Override
    public void onEnable() {
        try {

        } catch (Exception e) {
            throw new ModulatorException.ModulatorLoadException(e);
        }
    }

    /**
     * Called when this plugin is disabled
     */
    @Override
    public void onDisable() {

    }

    /**
     * Called when this plugin is reloaded
     */
    public void reload() {
        onDisable();
        onEnable();
    }

}
