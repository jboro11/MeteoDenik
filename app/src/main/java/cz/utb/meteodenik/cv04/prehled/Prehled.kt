package cz.utb.meteodenik.cv04.prehled

import java.util.Locale

data class Mesto(val nazev: String, val teplota: Double)

fun Double.jakoCelsia(): String = String.format(Locale.ROOT, "%.1f C", this)

sealed interface Stav {
    data object Nacita : Stav
    data object Chyba : Stav
    data object Prazdno : Stav
    data class Hotovo(val mesta: List<Mesto>) : Stav
}

fun prehled(stav: Stav): String = when (stav) {
    Stav.Nacita -> "Načítám…"
    Stav.Chyba -> "Chyba"
    Stav.Prazdno -> "Nic tu není"
    is Stav.Hotovo -> buildString {
        stav.mesta.forEach { appendLine("${it.nazev}: ${it.teplota.jakoCelsia()}") }
        append("Měst: ${stav.mesta.size}")
        stav.mesta.maxByOrNull(Mesto::teplota)?.let { nej ->
            append("\nNejtepleji: ${nej.nazev} (${nej.teplota.jakoCelsia()})")
        }
    }
}

fun main() {
    val mesta = listOf(
        Mesto("Zlín", 21.4),
        Mesto("Brno", 23.0),
        Mesto("Otrokovice", 20.9)
    )
    println(prehled(Stav.Hotovo(mesta)))
}
