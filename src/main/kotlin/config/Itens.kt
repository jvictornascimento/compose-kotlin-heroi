package config

import org.jetbrains.exposed.sql.Table

object Itens : Table("item") {
    val id = integer("id").autoIncrement()
    val codigo = integer("codigo")
    val descricao = varchar("descricao", 100)
    val ip = integer("ip").nullable()
    val temperatura = varchar("temperatura", 50).nullable()
    val ledPorMetro = integer("ledPorMetro").nullable()
    val modelo = varchar("modelo", 50).nullable()
    val amper = double("amper").nullable()
    val watts = integer("watts").nullable()
    val blindada = bool("blindada").nullable()
    val gtin = long("gtin").nullable()
    val tipoLed = varchar("tipoLed",50).nullable()
    val fluxoLuminoso = varchar("fluxoLuminoso", 10).nullable()
    val volt = integer("volt").nullable()

    override val primaryKey = PrimaryKey(id)
}