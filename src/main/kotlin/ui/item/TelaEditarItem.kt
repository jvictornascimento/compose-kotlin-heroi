package ui.item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Item
import dao.ItemDao

@Composable
fun TelaEditarItem(
    itemOriginal: Item,
    onSalvar: () -> Unit,
    onCancelar: () -> Unit
) {
    var codigo by remember { mutableStateOf(itemOriginal.codigo.toString()) }
    var descricao by remember { mutableStateOf(itemOriginal.descricao ?: "") }
    var ip by remember { mutableStateOf(itemOriginal.ip?.toString() ?: "") }
    var temperatura by remember { mutableStateOf(itemOriginal.temperatura ?: "") }
    var ledPorMetro by remember { mutableStateOf(itemOriginal.ledPorMetro?.toString() ?: "") }
    var modelo by remember { mutableStateOf(itemOriginal.modelo ?: "") }
    var amper by remember { mutableStateOf(itemOriginal.amper?.toString() ?: "") }
    var watts by remember { mutableStateOf(itemOriginal.watts?.toString() ?: "") }
    var blindada by remember { mutableStateOf(itemOriginal.blindada ?: false) }
    var gtin by remember { mutableStateOf(itemOriginal.gtin?.toString() ?: "") }
    var tipoLed by remember { mutableStateOf(itemOriginal.tipoLed ?: "") }
    var fluxoLuminoso by remember { mutableStateOf(itemOriginal.fluxoLuminoso ?: "") }
    var volt by remember { mutableStateOf(itemOriginal.volt?.toString() ?: "") }

    Column(modifier = Modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())
    ) {
        Text("Editar Item", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)) {

            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it },
                label = { Text("Código") },
                modifier = Modifier
                    .width(300.dp)
                    .height(80.dp)
                    .padding(8.dp)
            )
            Checkbox(
                checked = blindada,
                onCheckedChange = { blindada = it });
            Text("Blindada")
        }


        // Campos String com espaço maior
        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp) // <-- campo maior
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)) {
            OutlinedTextField(
                value = temperatura,
                onValueChange = { temperatura = it },
                label = { Text("Temperatura") },
                modifier = Modifier
                    .width(300.dp)
                    .height(80.dp)
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = modelo,
                onValueChange = { modelo = it },
                label = { Text("Modelo") },
                modifier = Modifier
                    .width(200.dp)
                    .height(80.dp)
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = fluxoLuminoso,
                onValueChange = { fluxoLuminoso = it },
                label = { Text("Fluxo Luminoso") },
                modifier = Modifier
                    .width(200.dp)
                    .height(80.dp)
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = ip,
                onValueChange = { ip = it },
                label = { Text("IP") },
                modifier = Modifier
                    .width(200.dp)
                    .height(80.dp)
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = ledPorMetro,
                onValueChange = { ledPorMetro = it },
                label = { Text("LEDs por Metro") },
                modifier = Modifier
                    .width(200.dp)
                    .height(80.dp)
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = tipoLed,
                onValueChange = { tipoLed = it },
                label = { Text("Tipo LED") },
                modifier = Modifier
                    .width(200.dp)
                    .height(80.dp)
                    .padding(8.dp)
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)) {

            OutlinedTextField(
                value = amper,
                onValueChange = { amper = it },
                label = { Text("Amper") },
                modifier = Modifier
                    .width(200.dp)
                    .height(80.dp)
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = watts,
                onValueChange = { watts = it },
                label = { Text("Watts") },
                modifier = Modifier
                    .width(200.dp)
                    .height(80.dp)
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = volt,
                onValueChange = { volt = it },
                label = { Text("Volt") },
                modifier = Modifier
                    .width(200.dp)
                    .height(80.dp)
                    .padding(8.dp)
            )


            OutlinedTextField(
                value = gtin,
                onValueChange = { gtin = it },
                label = { Text("GTIN") },
                modifier = Modifier
                    .width(200.dp)
                    .height(80.dp)
                    .padding(8.dp)
            )
        }

        // Botões
        Row(modifier = Modifier.padding(top = 16.dp)) {
            Button(onClick = {
                val novoCodigo = codigo.toIntOrNull()
                if (novoCodigo == null) {
                    println("Erro: Código inválido! Precisa ser número inteiro.")
                    return@Button
                }
                val itemAtualizado = Item(
                    id = itemOriginal.id,
                    codigo = codigo.toIntOrNull() ?: 0,
                    descricao = descricao,
                    ip = ip.toIntOrNull(),
                    temperatura = temperatura,
                    ledPorMetro = ledPorMetro.toIntOrNull(),
                    modelo = modelo,
                    amper = amper.toDoubleOrNull(),
                    watts = watts.toIntOrNull(),
                    blindada = blindada,
                    gtin = gtin.toLongOrNull(),
                    tipoLed = tipoLed,
                    fluxoLuminoso = fluxoLuminoso,
                    volt = volt.toIntOrNull()
                )
                ItemDao.atualizar(itemAtualizado)
                onSalvar()
            }) {
                Text("Salvar Alterações")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                onCancelar()
            }) {
                Text("Cancelar")
            }
        }
    }
}
