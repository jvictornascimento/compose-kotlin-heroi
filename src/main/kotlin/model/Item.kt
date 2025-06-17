package model

data class Item(
    val id: Int = 0,
    val codigo: Int,
    val descricao: String,
    val ip: Int?,
    val temperatura: String?,
    val ledPorMetro: Int?,
    val modelo: String?,
    val amper: Double?,
    val watts: Int?,
    val blindada: Boolean?,
    val gtin: Long?,
    val tipoLed: String?,
    val fluxoLuminoso: String?,
    val volt: Int?
)
