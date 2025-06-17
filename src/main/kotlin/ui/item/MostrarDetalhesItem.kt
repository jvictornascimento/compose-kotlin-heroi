package ui.item

import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import model.Item

@Composable
fun MostrarDetalhesItem(
    item: Item,
    onEditar: () -> Unit,
    onFechar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onFechar,
        title = { Text("Detalhes do Item") },
        text = {
            Column {
                Text("Código: ${item.codigo}")
                Text("Descrição: ${item.descricao ?: ""}")
                Text("IP: ${item.ip ?: ""}")
                Text("Temperatura: ${item.temperatura ?: ""}")
                Text("LED por Metro: ${item.ledPorMetro ?: ""}")
                Text("Modelo: ${item.modelo ?: ""}")
                Text("Amper: ${item.amper ?: ""}")
                Text("Watts: ${item.watts ?: ""}")
                Text("Blindada: ${if(item.blindada == true) "Sim" else "Não"}")
                Text("GTIN: ${item.gtin ?: ""}")
                Text("Tipo LED: ${item.tipoLed ?: ""}")
                Text("Fluxo Luminoso: ${item.fluxoLuminoso ?: ""}")
                Text("Volt: ${item.volt ?: ""}")
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
