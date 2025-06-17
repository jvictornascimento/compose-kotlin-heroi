package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dao.ItemDao

@Composable
fun TelaListagemItens() {
    val itens = remember { ItemDao.listarTodos() }

    Column(Modifier.padding(16.dp)) {
        Text("Lista de Itens", style = MaterialTheme.typography.h5)
        itens.forEach { item ->
            Text("${item.id} - ${item.descricao} - Código: ${item.codigo}")
        }
    }
}