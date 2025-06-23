package ui


import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Empresa
import model.Item
import model.Pedido
import ui.components.MenuLateral
import ui.empresa.TelaCadastroEmpresa
import ui.empresa.TelaEditarEmpresa
import ui.empresa.TelaListagemEmpresas
import ui.item.TelaCadastroItem
import ui.item.TelaEditarItem
import ui.item.TelaListagemItens
import ui.pedido.TelaCadastroPedido
import ui.pedido.TelaImpressaoEtiquetaLote
import ui.pedido.TelaListagemPedidos

@Composable
fun app() {
    var telaAtual by remember { mutableStateOf("home") }

    MaterialTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            MenuLateral(selectedTela = telaAtual, onTelaSelecionada = { telaAtual = it })
            var itemSelecionado by remember { mutableStateOf<Item?>(null) }
            var empresaSelecionado by remember { mutableStateOf<Empresa?>(null) }
            var pedidoSelecionado by remember { mutableStateOf<Pedido?>(null) }

            Divider(modifier = Modifier
                .fillMaxHeight()
                .width(1.dp))

            Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(16.dp)) {
                when (telaAtual) {
                    "home" -> TelaBoasVindas {telaAtual = it}
                    "cadastroItem" -> TelaCadastroItem(
                        onSalvar = { telaAtual = "listagemItem" },
                        onCancelar = { telaAtual = "home" }
                    )
                    "listagemItem" -> TelaListagemItens(
                        onEditar = { item ->
                            itemSelecionado = item
                            telaAtual = "editarItem"
                        }
                    )
                    "editarItem" -> itemSelecionado?.let { item ->
                        TelaEditarItem(
                            itemOriginal = item,
                            onSalvar = { telaAtual = "listagemItem" },
                            onCancelar = { telaAtual = "home" }
                        )
                    }

                    "cadastroEmpresa" -> TelaCadastroEmpresa(
                        onSalvar = { telaAtual = "listagemEmpresa" },
                        onCancelar = { telaAtual = "home" }
                    )
                    "listagemEmpresa" -> TelaListagemEmpresas(
                        onEditar = { empresa ->
                            empresaSelecionado = empresa
                            telaAtual = "editarEmpresa"
                        }
                    )
                    "editarEmpresa" -> empresaSelecionado?.let { empresa ->
                        TelaEditarEmpresa(
                            empresaOriginal = empresa,
                            onSalvar = { telaAtual = "listagemEmpresa" },
                            onCancelar = { telaAtual = "home" }
                        )
                    }

                    "cadastroPedido" -> TelaCadastroPedido (
                        onSalvar = { telaAtual = "listagemPedidos" },
                        onCancelar = { telaAtual = "home" }
                    )
                    "listagemPedidos" -> TelaListagemPedidos(
                        onEditar = { pedido ->
                            pedidoSelecionado = pedido
                            telaAtual = "listagemPedidos"
                        }
                    )


                    "cadastroLote" -> {/* TelaCadastroLote() - futura */}
                    "listagemLote" -> {/* TelaListagemLote() - futura */}
                    "impressaoLote" ->  TelaImpressaoEtiquetaLote ()
                    else -> Text("Tela não encontrada!")
                }
            }
        }
    }
}