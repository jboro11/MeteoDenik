package cz.utb.meteodenik

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface MestaRepository {
    val mesta: Flow<List<Mesto>>
    suspend fun pridat(nazev: String)
    suspend fun odebrat(id: Int)
    suspend fun obnovit()
}

class PametovaMestaRepository : MestaRepository {
    override val mesta = MutableStateFlow(vychoziMesta)
    private var dalsiId = 4

    override suspend fun pridat(nazev: String) {
        mesta.update { it + Mesto(dalsiId++, nazev, null, null) }
    }

    override suspend fun odebrat(id: Int) {
        mesta.update { seznam -> seznam.filterNot { it.id == id } }
    }

    override suspend fun obnovit() = Unit
}
