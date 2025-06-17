package model

import java.time.LocalDate


data class Pedido(
    val id: Int = 0,
    val codigo: String,
    val dataCompra: LocalDate,
    val empresaId: Int,
    val itens: List<Int> = emptyList()
)