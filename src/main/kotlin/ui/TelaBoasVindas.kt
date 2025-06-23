package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TelaBoasVindas(onNavegar: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bem-vindo ao Heroi", style = MaterialTheme.typography.h4)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { onNavegar("cadastroItem") }) {
            Text("Cadastrar um novo item")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onNavegar("cadastroPedido") }) {
            Text("Cadastrar um novo Lote")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onNavegar("impressaoLote") }) {
            Text("Impresssão de Lote")
        }
    }
}