package ui.pedido

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dao.EmpresaDao
import dao.ItemDao
import dao.PedidoDao
import kotlinx.coroutines.launch
import model.Empresa
import model.Item
import model.Pedido
import ui.item.MostrarDetalhesItem

@Composable
fun TelaListagemPedidos(onEditar: (Pedido)-> Unit) {
    var pedidos by remember { mutableStateOf<List<Pedido>>(emptyList()) }
    var pedidosSelecionado by remember { mutableStateOf<Pedido?>(null) }
    var mostrarDetalhes by remember { mutableStateOf(false) }
    val itens = ItemDao.listarTodos()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            pedidos = PedidoDao.listarTodos()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Pedidos", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(8.dp))

        pedidos.forEach { pedidos ->
            Row(modifier = Modifier
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
                            onEditar(pedidos)
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
    }
}