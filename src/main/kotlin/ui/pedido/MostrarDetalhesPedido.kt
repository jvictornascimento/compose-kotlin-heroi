package ui.pedido

import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import dao.EmpresaDao
import model.Item
import model.Pedido

@Composable
fun MostrarDetalhesPedido(
    pedido: Pedido,
    itens: List<Item>,
    onEditar: () -> Unit,
    onFechar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onFechar,
        title = { Text("Detalhes do Pedido") },
        text = {
            Column {
                Text("ID: ${pedido.id}")
                Text("Lote: ${pedido.codigo}")
                Text("Data da Compra: ${pedido.dataCompra}")
                Text("Empresa: " + (EmpresaDao.buscarPorCodifo(pedido.empresaId)?.nome ?: ""))

                Text("Itens do Pedido:")
                pedido.itens.forEach { itemId ->
                    val item = itens.find { it.id == itemId }
                    Text("- Códigos do Item: ${item?.codigoRusso ?: "Item não encontrado"} | ${item?.codigoMali ?: ""}")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onEditar) {
                Text("Editar")
            }
        },
        dismissButton = {
            TextButton(onClick = onFechar) {
                Text("Fechar")
            }
        }
    )
}
