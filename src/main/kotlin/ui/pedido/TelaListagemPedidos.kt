package ui.pedido

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
import dao.PedidoDao
import kotlinx.coroutines.launch
import model.Pedido

@Composable
fun TelaListagemPedidos(onEditar: (Pedido) -> Unit) {
    var pedidosSelecionado by remember { mutableStateOf<Pedido?>(null) }
    var mostrarDetalhes by remember { mutableStateOf(false) }
    val itens = ItemDao.listarTodos()
    val todosPedidos = PedidoDao.listarTodos()
    val scrollState = rememberScrollState()
    var textoFiltro by remember { mutableStateOf("") }

    val pedidosFiltrados = todosPedidos.filter { pedido ->
        val codigoStr = pedido.codigo?.toString()?.lowercase() ?: ""
        val filtro = textoFiltro.lowercase()

        filtro.isBlank() ||
                codigoStr.contains(filtro)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(scrollState)
    ) {
        Text("Lista de Pedidos", style = MaterialTheme.typography.h4)

        androidx.compose.material.OutlinedTextField(
            value = textoFiltro,
            onValueChange = { textoFiltro = it },
            label = { Text("Filtrar pelo numero do pedido russo ou mali") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        pedidosFiltrados.forEach { pedidos ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Lote: ${pedidos.codigo} - ${pedidos.dataCompra}", modifier = Modifier.weight(1f))

                Button(onClick = {
                    pedidosSelecionado = pedidos
                    mostrarDetalhes = true
                }) {
                    Text("Detalhar")
                }
                if (mostrarDetalhes && pedidosSelecionado != null) {
                    MostrarDetalhesPedido(
                        pedido = pedidosSelecionado!!,
                        itens = itens,
                        onEditar = {
                            mostrarDetalhes = false
                            onEditar(pedidosSelecionado!!)
                        },
                        onFechar = {
                            mostrarDetalhes = false
                        }
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))
                Button(onClick = { onEditar(pedidos) }) {
                    Text("Editar")
                }
            }
        }
        if (pedidosFiltrados.isEmpty()) {
            Text(
                "Nenhum pedido encontrado.",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}