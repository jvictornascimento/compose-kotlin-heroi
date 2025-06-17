package data

import org.jetbrains.exposed.sql.Table

object Empresas : Table("empresa") {
    val id = integer("id").autoIncrement()
    val nome = varchar("nome", 255)

    override val primaryKey = PrimaryKey(Itens.id)
}