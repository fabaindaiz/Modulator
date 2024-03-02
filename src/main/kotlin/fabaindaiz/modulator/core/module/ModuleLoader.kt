package fabaindaiz.modulator.core.module

import fabaindaiz.modulator.Modulator
import fabaindaiz.modulator.modules.zanakik.Zanakik

class ModuleLoader (val modulator: Modulator) {
    var modules: List<BaseModule>

    init {
        createFolders(modulator.dataFolder, listOf("config"))
        modules = loadAll()
    }

    private fun loadAll(): List<BaseModule> {
        return listOf(Zanakik)
    }

}