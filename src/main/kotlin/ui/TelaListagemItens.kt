package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dao.ItemDao
import model.Item

@Composable
fun TelaListagemItens(onEditar: (Item) -> Unit) {
    val itens = ItemDao.listarTodos()

    Column {
        Text("Lista de Itens", style = MaterialTheme.typography.h5)

        itens.forEach { item ->
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Text("Código: ${item.codigo} - ${item.descricao}", modifier = Modifier.weight(1f))
                Button(onClick = { onEditar(item) }) {
                    Text("Editar")
                }
            }
        }
    }
}