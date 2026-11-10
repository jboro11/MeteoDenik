package cz.utb.meteodenik

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MestaUiState(
    val mesta: List<Mesto> = emptyList(),
    val rozepsane: String = "",
    val duplicita: Boolean = false,
    val pridava: Boolean = false,
    val nacita: Boolean = false,
    val chyba: ChybaPocasi? = null
)

enum class ChybaPocasi { PRIPOJENI, TIMEOUT, HTTP, ODPOVED, NENALEZENO, OSTATNI }
class PocasiException(val duvod: ChybaPocasi, cause: Throwable? = null) : Exception(cause)

class MestaViewModel(
    private val saved: SavedStateHandle,
    private val repository: MestaRepository = PametovaMestaRepository()
) : ViewModel() {
    private val _stav = MutableStateFlow(MestaUiState(rozepsane = saved["vstup"] ?: ""))
    val stav: StateFlow<MestaUiState> = _stav.asStateFlow()

    init {
        viewModelScope.launch {
            repository.mesta.collect { seznam -> _stav.update { it.copy(mesta = seznam) } }
        }
    }

    fun zmenaVstupu(text: String) {
        saved["vstup"] = text
        _stav.update { it.copy(rozepsane = text, duplicita = false) }
    }

    fun pridat() {
        val stav = _stav.value
        val nazev = stav.rozepsane.trim()
        if (nazev.isEmpty() || stav.pridava) return
        if (stav.mesta.any { it.nazev.equals(nazev, ignoreCase = true) }) {
            _stav.update { it.copy(duplicita = true) }
            return
        }
        _stav.update { it.copy(pridava = true, chyba = null) }
        viewModelScope.launch {
            try {
                repository.pridat(nazev)
                if (_stav.value.rozepsane == stav.rozepsane) zmenaVstupu("")
            } catch (chyba: CancellationException) {
                throw chyba
            } catch (chyba: PocasiException) {
                _stav.update { it.copy(chyba = chyba.duvod) }
            } finally {
                _stav.update { it.copy(pridava = false) }
            }
        }
    }

    fun odebrat(id: Int) {
        viewModelScope.launch { repository.odebrat(id) }
    }

    fun obnovit() {
        if (_stav.value.nacita) return
        _stav.update { it.copy(nacita = true, chyba = null) }
        viewModelScope.launch {
            try {
                repository.obnovit()
            } catch (chyba: CancellationException) {
                throw chyba
            } catch (chyba: PocasiException) {
                _stav.update { it.copy(chyba = chyba.duvod) }
            } finally {
                _stav.update { it.copy(nacita = false) }
            }
        }
    }
}
