package dao



import data.PedidoItens
import data.Pedidos
import model.Pedido
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insertAndGetId

object PedidoDao {

    fun inserir(pedido: Pedido) {
        println("PedidoDao.inserir chamado com: $pedido")
        transaction {
            val pedidoId = Pedidos.insertAndGetId {
                it[codigo] = pedido.codigo
                it[dataCompra] = pedido.dataCompra
                it[empresaId] = pedido.empresaId
            }.value

            pedido.itens.forEach { itemId ->
                PedidoItens.insert {
                    it[PedidoItens.pedidoId] = pedidoId
                    it[PedidoItens.itemId] = itemId
                }
            }
        }
        println("Pedido inserido com sucesso")
    }

    fun listarTodos(): List<Pedido> = transaction {
        Pedidos.selectAll().map { row ->
            val pedidoIdEntity = row[Pedidos.id]
            val pedidoId = pedidoIdEntity.value

            val itensIds = PedidoItens.select { PedidoItens.pedidoId eq pedidoId }
                .map { it[PedidoItens.itemId] }

            Pedido(
                id = pedidoId,
                codigo = row[Pedidos.codigo],
                dataCompra = row[Pedidos.dataCompra],
                empresaId = row[Pedidos.empresaId],
                itens = itensIds
            )
        }
    }
//
//    fun atualizar(pedido: Pedido) {
//        transaction {
//            Pedidos.update({ Pedidos.id eq pedido.id }) {
//                it[codigo] = pedido.codigo
//                it[dataCompra] = pedido.dataCompra
//                it[empresaId] = pedido.empresaId
//            }
//
//            PedidoItens.deleteWhere { PedidoItens.pedidoId eq pedido.id }
//
//            pedido.itens.forEach { itemId ->
//                PedidoItens.insert {
//                    it[PedidoItens.pedidoId] = pedido.id
//                    it[PedidoItens.itemId] = itemId
//                }
//            }
//        }
//    }
}

