package model

data class Item(
    val id: Int = 0,
    val codigoRusso: Int?,
    val codigoMali: Int?,
    val descricao: String,
    val ip: Int?,
    val temperatura: String?,
    val ledPorMetro: Int?,
    val modelo: String?,
    val amper: Double?,
    val watts: Double?,
    val blindada: Boolean?,
    val gtin: Long?,
    val tipoLed: String?,
    val fluxoLuminoso: String?,
    val volt: Int?
)
