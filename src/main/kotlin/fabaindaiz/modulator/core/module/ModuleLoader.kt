package fabaindaiz.modulator.core.module

import fabaindaiz.modulator.Modulator
import fabaindaiz.modulator.modules.zkik.Zanakik

class ModuleLoader (val modulator: Modulator) {
    private var modules: List<Module>

    init {
        createFolders(modulator.dataFolder, listOf("config"))
        modules = loadAll()
    }

    private fun loadAll(): List<Module> {
        return listOf(Zanakik)
    }

}