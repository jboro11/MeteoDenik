package cz.utb.meteodenik

data class Mesto(
    val id: Int,
    val nazev: String,
    val latitude: Double?,
    val longitude: Double?,
    val teplota: Double? = null
)

val vychoziMesta = listOf(
    Mesto(1, "Zlín", 49.2265, 17.6688, 21.4),
    Mesto(2, "Brno", 49.1951, 16.6068, 19.6),
    Mesto(3, "Ostrava", 49.8209, 18.2625, 18.2)
)
