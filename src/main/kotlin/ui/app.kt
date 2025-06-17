package ui


import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Item

@Composable
fun app() {
    var telaAtual by remember { mutableStateOf("home") }

    MaterialTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            MenuLateral(selectedTela = telaAtual, onTelaSelecionada = { telaAtual = it })
            var itemSelecionado by remember { mutableStateOf<Item?>(null) }

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
                            telaAtual = "editar"
                        }
                    )

                    "editar" -> itemSelecionado?.let { item ->
                        TelaEditarItem(
                            itemOriginal = item,
                            onSalvar = { telaAtual = "listagemItem" },
                            onCancelar = { telaAtual = "home" }
                        )
                    }
                    "cadastroLote" -> {/* TelaCadastroLote() - futura */}
                    "listagemLote" -> {/* TelaListagemLote() - futura */}
                    "impressaoLote" -> {/* TelaImpressaoLote() - futura */}
                    else -> Text("Tela não encontrada!")
                }
            }
        }
    }
}