package ui.item

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dao.ItemDao
import model.Item

@Composable
fun TelaListagemItens(onEditar: (Item) -> Unit) {
    val itens = ItemDao.listarTodos()
    var itemSelecionado by remember { mutableStateOf<Item?>(null) }
    var mostrarDetalhes by remember { mutableStateOf(false) }

    Column {
        Text("Lista de Itens", style = MaterialTheme.typography.h5)

        itens.forEach { item ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ) {
                Text("Código: ${item.codigo} - ${item.descricao}", modifier = Modifier.weight(1f))

                Button(onClick = {
                    itemSelecionado = item
                    mostrarDetalhes = true
                }) {
                    Text("Detalhar")
                }
                if (mostrarDetalhes && itemSelecionado != null) {
                    MostrarDetalhesItem(
                        item = itemSelecionado!!,
                        onEditar = {
                            mostrarDetalhes = false
                            onEditar(item)
                        },
                        onFechar = {
                            mostrarDetalhes = false
                        }
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(onClick = { onEditar(item) }) {
                    Text("Editar")
                }


            }
        }
    }
}