package fabaindaiz.modulator.core.loader;

import fabaindaiz.modulator.Modulator;
import fabaindaiz.modulator.core.dependencies.libHandler;
import fabaindaiz.modulator.core.dependencies.sqlHandler;
import fabaindaiz.modulator.core.dependencies.vaultHandler;

public class ModulatorDependencies {

    private /*final*/ libHandler libHandler;
    private final sqlHandler sqlHandler;
    private final vaultHandler vaultHandler;

    private final Modulator plugin;

    public ModulatorDependencies(Modulator plugin) {

        this.plugin = plugin;

        //libHandler = new libHandler(plugin);
        sqlHandler = new sqlHandler(plugin);
        vaultHandler = new vaultHandler(plugin);
    }

    public libHandler getLibHandler() {
        return libHandler;
    }

    public sqlHandler getSqlHandler() {
        return sqlHandler;
    }

    public vaultHandler getVaultHandler() {
        return vaultHandler;
    }
}
