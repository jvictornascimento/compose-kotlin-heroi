package ui.pedido

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import dao.ItemDao
import dao.PedidoDao
import model.Item
import net.sf.jasperreports.engine.JREmptyDataSource
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.view.JasperViewer

@Composable
fun TelaImpressaoEtiquetaLote() {
    var codigoPedido by remember { mutableStateOf("") }
    var itensDoPedido by remember { mutableStateOf(listOf<Item>()) }
    var itemSelecionado by remember { mutableStateOf<Item?>(null) }
    var erro by remember { mutableStateOf<String?>(null) }
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Impressão de Etiqueta", style = MaterialTheme.typography.h5)

        OutlinedTextField(
            value = codigoPedido,
            onValueChange = { codigoPedido = it },
            label = { Text("Código do Pedido") },
            modifier = Modifier
                .fillMaxWidth()
                .onPreviewKeyEvent { event ->
                    if (event.key == Key.Enter && event.type == KeyEventType.KeyUp) {
                        val pedido = PedidoDao.buscarPorCodigo(codigoPedido)
                        if (pedido != null) {
                            itensDoPedido = ItemDao.buscarPorIds(pedido.itens)
                            erro = null
                        } else {
                            erro = "Pedido não encontrado"
                            itensDoPedido = emptyList()
                            itemSelecionado = null
                        }
                        true
                    } else {
                        false
                    }
                }

        )

        Button(onClick = {
            val pedido = PedidoDao.buscarPorCodigo(codigoPedido)
            if (pedido != null) {
                itensDoPedido = ItemDao.buscarPorIds(pedido.itens)
                erro = null
            } else {
                erro = "Pedido não encontrado"
                itensDoPedido = emptyList()
                itemSelecionado = null
            }

        }) {
            Text("Buscar Itens do Pedido")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (itensDoPedido.isNotEmpty()) {
            var expanded by remember { mutableStateOf(false) }

            Box {
                TextButton(onClick = { expanded = true }) {
                    Text(itemSelecionado?.descricao ?: "Selecione um item")
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    itensDoPedido.forEach { item ->
                        DropdownMenuItem(onClick = {
                            itemSelecionado = item
                            expanded = false
                        }) {
                            Text("${item.codigoRusso} | ${item.codigoMali} - ${item.descricao}")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (itemSelecionado != null) {
                    gerarPreviewEtiqueta(itemSelecionado!!, codigoPedido)
                } else {
                    erro = "Selecione um item antes de imprimir"
                }
            }) {
                Text("Imprimir")
            }
        }

        erro?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colors.error)
        }
    }
}


fun gerarPreviewEtiqueta(item: Item, lote: String) {
    try {
        val caminhoJrxml = "src/relatorios/lote.jrxml"

        val parametros = mutableMapOf<String, Any?>(
            "descricao" to item.descricao,
            "codigoRusso" to item.codigoRusso,
            "codigoMali" to item.codigoMali,
            "temperatura" to (item.temperatura ?: ""),
            "ip" to (item.ip ?: 0),
            "ledPorMetro" to (item.ledPorMetro ?: 0),
            "Modelo" to (item.modelo ?: ""),
            "ampere" to (item.amper ?: 0),
            "watts" to (item.watts ?: 0),
            "gtin" to (item.gtin ?: 0L),
            "tipoLed" to (item.tipoLed ?: ""),
            "fluxoLuminoso" to (item.fluxoLuminoso ?: ""),
            "volts" to (item.volt ?: 0),
            "lote" to (lote.toIntOrNull() ?: 0)
        )

        val report = JasperCompileManager.compileReport(caminhoJrxml)
        val jasperPrint = JasperFillManager.fillReport(report, parametros, JREmptyDataSource())
        JasperViewer.viewReport(jasperPrint, false)

    } catch (e: Exception) {
        e.printStackTrace()
    }
}