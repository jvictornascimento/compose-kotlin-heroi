package data

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.ReferenceOption

object Pedidos : IdTable<Int>("pedido") {
    override val id = integer("id").autoIncrement().entityId()

    val codigo = varchar("codigo", 50)
    val dataCompra = date("data_compra")
    val empresaId = integer("empresa_id").references(Empresas.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(id)
}
