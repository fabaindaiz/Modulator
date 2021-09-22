package fabaindaiz.modulator.core.loader;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dependencies.libHandler;
import fabaindaiz.modulator.core.dependencies.sqlHandler;
import fabaindaiz.modulator.core.dependencies.vaultHandler;

/**
 * Represents a class which load all Modulator dependencies
 */
public class ModulatorDependencies {

    private final sqlHandler sqlHandler;
    private final vaultHandler vaultHandler;
    private /*final*/ libHandler libHandler;
    private final Modulator plugin;

    /**
     * @param plugin Modulator main class
     */
    public ModulatorDependencies(Modulator plugin) {

        this.plugin = plugin;

        //libHandler = new libHandler(plugin);
        sqlHandler = new sqlHandler(plugin);
        vaultHandler = new vaultHandler(plugin);
    }

    /**
     * Gets Lib handler for modules
     * @return A class which handle libs
     */
    public libHandler getLibHandler() {
        return libHandler;
    }

    /**
     * Gets Sql handler for modules
     * @return A class which handle sql
     */
    public sqlHandler getSqlHandler() {
        return sqlHandler;
    }

    /**
     * Gets Vault handler for modules
     * @return A class which handle vault
     */
    public vaultHandler getVaultHandler() {
        return vaultHandler;
    }
}
