package data


import org.jetbrains.exposed.sql.Table

object PedidoItens : Table("pedido_itens") {
    val pedidoId = integer("pedido_id").references(Pedidos.id)
    val itemId = integer("item_id").references(Itens.id)
}