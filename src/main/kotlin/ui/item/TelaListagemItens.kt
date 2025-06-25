package ui.item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dao.ItemDao
import model.Item

@Composable
fun TelaListagemItens(onEditar: (Item) -> Unit) {
    val todosItens = ItemDao.listarTodos()
    var itemSelecionado by remember { mutableStateOf<Item?>(null) }
    var mostrarDetalhes by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var textoFiltro by remember { mutableStateOf("") }

    val itensFiltrados = todosItens.filter { item ->
        val codigoRussoStr = item.codigoRusso?.toString()?.lowercase() ?: ""
        val codigoMaliStr = item.codigoMali?.toString()?.lowercase() ?: ""
        val filtro = textoFiltro.lowercase()

        filtro.isBlank() ||
                codigoRussoStr.contains(filtro) ||
                codigoMaliStr.contains(filtro)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            "Lista de Itens",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        androidx.compose.material.OutlinedTextField(
            value = textoFiltro,
            onValueChange = { textoFiltro = it },
            label = { Text("Filtrar por código russo ou mali") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        itensFiltrados.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    "Códigos: ${item.codigoRusso ?: ""} | ${item.codigoMali ?: ""} - ${item.descricao}",
                    modifier = Modifier.weight(1f)
                )

                Button(onClick = {
                    itemSelecionado = item
                    mostrarDetalhes = true
                }) {
                    Text("Detalhar")
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(onClick = { onEditar(item) }) {
                    Text("Editar")
                }
            }

            if (mostrarDetalhes && itemSelecionado == item) {
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
        }

        if (itensFiltrados.isEmpty()) {
            Text(
                "Nenhum item encontrado.",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}
