package dao

import model.Item
import config.Itens
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object ItemDao {
    fun inserir(item: Item) {
        transaction {
            Itens.insert {
                it[codigo] = item.codigo
                it[descricao] = item.descricao
                it[ip] = item.ip
                it[temperatura] = item.temperatura
                it[ledPorMetro] = item.ledPorMetro
                it[modelo] = item.modelo
                it[amper] = item.amper
                it[watts] = item.watts
                it[blindada] = item.blindada
                it[gtin] = item.gtin
                it[tipoLed] = item.tipoLed
                it[fluxoLuminoso] = item.fluxoLuminoso
                it[volt] = item.volt
            }
        }
    }

    fun listarTodos(): List<Item> = transaction {
        Itens.selectAll().map {
            Item(
                id = it[Itens.id],
                codigo = it[Itens.codigo],
                descricao = it[Itens.descricao],
                ip = it[Itens.ip],
                temperatura = it[Itens.temperatura],
                ledPorMetro = it[Itens.ledPorMetro],
                modelo = it[Itens.modelo],
                amper = it[Itens.amper],
                watts = it[Itens.watts],
                blindada = it[Itens.blindada],
                gtin = it[Itens.gtin],
                tipoLed = it[Itens.tipoLed],
                fluxoLuminoso = it[Itens.fluxoLuminoso],
                volt = it[Itens.volt]
            )
        }
    }

    fun buscarPorCodigo(codigoBusca: Int): Item? = transaction {
        Itens.select { Itens.codigo eq codigoBusca }
            .mapNotNull {
                Item(
                    id = it[Itens.id],
                    codigo = it[Itens.codigo],
                    descricao = it[Itens.descricao],
                    ip = it[Itens.ip],
                    temperatura = it[Itens.temperatura],
                    ledPorMetro = it[Itens.ledPorMetro],
                    modelo = it[Itens.modelo],
                    amper = it[Itens.amper],
                    watts = it[Itens.watts],
                    blindada = it[Itens.blindada],
                    gtin = it[Itens.gtin],
                    tipoLed = it[Itens.tipoLed],
                    fluxoLuminoso = it[Itens.fluxoLuminoso],
                    volt = it[Itens.volt]
                )
            }
            .singleOrNull()
    }

    fun atualizar(item: Item) {
        transaction {
            Itens.update({ Itens.id eq item.id }) { // filtra pelo id (único e fixo)
                it[codigo] = item.codigo
                it[descricao] = item.descricao
                it[ip] = item.ip
                it[temperatura] = item.temperatura
                it[ledPorMetro] = item.ledPorMetro
                it[modelo] = item.modelo
                it[amper] = item.amper
                it[watts] = item.watts
                it[blindada] = item.blindada
                it[gtin] = item.gtin
                it[tipoLed] = item.tipoLed
                it[fluxoLuminoso] = item.fluxoLuminoso
                it[volt] = item.volt
            }
        }
    }
}