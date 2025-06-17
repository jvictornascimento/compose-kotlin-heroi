package dao

import data.Empresas
import model.Empresa
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object EmpresaDao {
    fun inserir(empresa: Empresa) {
        transaction {
            Empresas.insert {
                it[nome] = empresa.nome
            }
        }
    }
    fun listarTodos(): List<Empresa> = transaction {
        Empresas.selectAll().map {
            Empresa(
                id = it[Empresas.id],
                nome = it[Empresas.nome]
            )
        }
    }
    fun atualizar(empresa: Empresa) {
        transaction {
            Empresas.update({ Empresas.id eq empresa.id }) {
                it[nome] = empresa.nome
            }
        }
    }
}