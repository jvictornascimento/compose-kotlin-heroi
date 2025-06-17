package ui.pedido

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Item

@Composable
fun SeletorDeItens(itensDisponiveis: List<Item>, itensSelecionados: SnapshotStateList<Item>) {
    var filtro by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = filtro,
            onValueChange = { filtro = it },
            label = { Text("Buscar item...") },
            modifier = Modifier.fillMaxWidth()
        )

        val resultados = if (filtro.isBlank()) {
            emptyList()
        } else {
            itensDisponiveis.filter {
                it.codigo.toString().contains(filtro, ignoreCase = true) ||
                        it.descricao.contains(filtro, ignoreCase = true)
            }
        }
        LazyColumn {
            items(resultados) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${item.codigo} - ${item.descricao}")
                    Button(onClick = {
                        if (!itensSelecionados.contains(item)) {
                            itensSelecionados.add(item)
                        }
                    }) {
                        Text("Adicionar")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Itens adicionados ao pedido:")

        LazyColumn {
            items(itensSelecionados) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${item.codigo} - ${item.descricao}")
                    Button(onClick = { itensSelecionados.remove(item) }) {
                        Text("Remover")
                    }
                }
            }
        }
    }
}