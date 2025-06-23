package ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MenuLateral(selectedTela: String, onTelaSelecionada: (String) -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .width(200.dp)
            .fillMaxHeight()
            .background(Color(0xFFEFEFEF))
            .padding(8.dp)
            .verticalScroll(scrollState)
    ) {
        Text("Menu", style = MaterialTheme.typography.h6, modifier = Modifier.padding(bottom = 16.dp))

        MenuCategoria("Itens") {
            MenuItem("Novo", selectedTela == "cadastroItem") { onTelaSelecionada("cadastroItem") }
            MenuItem("Ver Todos", selectedTela == "listagemItem") { onTelaSelecionada("listagemItem") }
        }

        MenuCategoria("Lote") {
            MenuItem("Novo", selectedTela == "cadastroPedido") { onTelaSelecionada("cadastroPedido") }
            MenuItem("Ver Todos", selectedTela == "listagemPedidos") { onTelaSelecionada("listagemPedidos") }
            MenuItem("Impressão", selectedTela == "impressaoLote") { onTelaSelecionada("impressaoLote") }
        }
        MenuCategoria("Configuraçãoes") {
            MenuCategoria("Empresa"){
                MenuItem("Nova", selectedTela == "cadastroEmpresa") { onTelaSelecionada("cadastroEmpresa") }
                MenuItem("Ver Todas", selectedTela == "listagemEmpresa") { onTelaSelecionada("listagemEmpresa") }
            }
            MenuItem("Configurações", selectedTela == "") { onTelaSelecionada("") }
        }
    }
}

@Composable
fun MenuCategoria(titulo: String, content: @Composable ColumnScope.() -> Unit) {
    var expandido by remember { mutableStateOf(true) }

    Column {
        Text(
            text = if (expandido) "▼ $titulo" else "► $titulo",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandido = !expandido }
                .padding(vertical = 8.dp, horizontal = 8.dp)
        )
        if (expandido) {
            Column(modifier = Modifier.padding(start = 16.dp)) {
                content()
            }
        }
    }
}

@Composable
fun MenuItem(titulo: String, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        text = titulo,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(if (isSelected) MaterialTheme.colors.primary else Color.Transparent)
            .padding(vertical = 6.dp, horizontal = 8.dp),
        color = if (isSelected) Color.White else Color.Black
    )
}