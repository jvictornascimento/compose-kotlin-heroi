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
import java.io.File

@Composable
fun TelaImpressaoEtiquetaLote() {
    var codigoPedido by remember { mutableStateOf("") }
    var itensDoPedido by remember { mutableStateOf(listOf<Item>()) }
    var itemSelecionado by remember { mutableStateOf<Item?>(null) }
    var erro by remember { mutableStateOf<String?>(null) }

    val focusManager = LocalFocusManager.current

    // Carrega os modelos disponíveis da pasta
    val modelosEtiquetas = remember {
        File("src/relatorios")
            .listFiles { file -> file.extension == "jrxml" }
            ?.map { file ->
                file.nameWithoutExtension.replace("_", " ").replaceFirstChar { it.uppercase() } to file
            } ?: emptyList()
    }

    var modeloSelecionado by remember { mutableStateOf<Pair<String, File>?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Impressão Etiqueta de Lote", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

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
            // Dropdown de item
            var itemDropdownExpanded by remember { mutableStateOf(false) }

            Box {
                TextButton(onClick = { itemDropdownExpanded = true }) {
                    Text(itemSelecionado?.descricao ?: "Selecionar item")
                }
                DropdownMenu(expanded = itemDropdownExpanded, onDismissRequest = { itemDropdownExpanded = false }) {
                    itensDoPedido.forEach { item ->
                        DropdownMenuItem(onClick = {
                            itemSelecionado = item
                            itemDropdownExpanded = false
                        }) {
                            Text("${item.codigoRusso} | ${item.codigoMali} - ${item.descricao}")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown de modelo de etiqueta
            var modeloDropdownExpanded by remember { mutableStateOf(false) }

            Box {
                TextButton(onClick = { modeloDropdownExpanded = true }) {
                    Text(modeloSelecionado?.first ?: "Selecionar modelo de etiqueta")
                }
                DropdownMenu(expanded = modeloDropdownExpanded, onDismissRequest = { modeloDropdownExpanded = false }) {
                    modelosEtiquetas.forEach { modelo ->
                        DropdownMenuItem(onClick = {
                            modeloSelecionado = modelo
                            modeloDropdownExpanded = false
                        }) {
                            Text(modelo.first)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (itemSelecionado != null && modeloSelecionado != null) {
                    gerarPreviewEtiqueta(itemSelecionado!!, codigoPedido, modeloSelecionado!!.second)
                } else {
                    erro = "Selecione um item e um modelo de etiqueta"
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

fun gerarPreviewEtiqueta(item: Item, lote: String, modelo: File) {
    try {
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
        if ("direto_na_rede" in modelo.nameWithoutExtension) {
            val imagemPath = File("src/relatorios/img/PlacaA5.png").absolutePath
            parametros["imagem"] = imagemPath
        }


        val report = JasperCompileManager.compileReport(modelo.absolutePath)
        val jasperPrint = JasperFillManager.fillReport(report, parametros, JREmptyDataSource())
        JasperViewer.viewReport(jasperPrint, false)

    } catch (e: Exception) {
        e.printStackTrace()
    }
}
