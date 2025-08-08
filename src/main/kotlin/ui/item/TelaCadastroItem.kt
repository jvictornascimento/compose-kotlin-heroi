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
fun TelaCadastroItem(onSalvar: () -> Unit, onCancelar: () -> Unit) {
    var codigoRusso by remember { mutableStateOf("") }
    var codigoMali by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var ip by remember { mutableStateOf("") }
    var temperatura by remember { mutableStateOf("") }
    var ledPorMetro by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var amper by remember { mutableStateOf("") }
    var watts by remember { mutableStateOf("") }
    var blindada by remember { mutableStateOf(false) }
    var gtin by remember { mutableStateOf("") }
    var tipoLed by remember { mutableStateOf("") }
    var fluxoLuminoso by remember { mutableStateOf("") }
    var volt by remember { mutableStateOf("") }


    Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Cadastro de Item", style = MaterialTheme.typography.h5)

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)) {

            OutlinedTextField(
                value = codigoRusso,
                onValueChange = { codigoRusso = it },
                label = { Text("Código Russo") },
                modifier = Modifier
                    .width(300.dp)
                    .height(80.dp)
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = codigoMali,
                onValueChange = { codigoMali= it },
                label = { Text("Código Mali") },
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

        Row(modifier = Modifier.padding(top = 16.dp)) {
            Button(onClick = {
                val item = Item(
                    codigoRusso = codigoRusso.toIntOrNull() ?: 0,
                    codigoMali = codigoMali.toIntOrNull() ?: 0,
                    descricao = descricao,
                    ip = ip.toIntOrNull(),
                    temperatura = temperatura,
                    ledPorMetro = ledPorMetro.toIntOrNull(),
                    modelo = modelo,
                    amper = amper.toDoubleOrNull(),
                    watts = watts.toDoubleOrNull(),
                    blindada = blindada,
                    gtin = gtin.toLongOrNull(),
                    tipoLed = tipoLed,
                    fluxoLuminoso = fluxoLuminoso,
                    volt = volt.toIntOrNull()
                )
                ItemDao.inserir(item)
                onSalvar()
            }) {
                Text("Salvar")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                // Limpa tudo e volta pra home
                codigoRusso = ""
                codigoMali = ""
                descricao = ""
                ip = ""
                temperatura = ""
                ledPorMetro = ""
                modelo = ""
                amper = ""
                watts = ""
                blindada = false
                gtin = ""
                tipoLed = ""
                fluxoLuminoso = ""
                volt = ""
                onCancelar()
            }) {
                Text("Cancelar")
            }
        }
    }
}
