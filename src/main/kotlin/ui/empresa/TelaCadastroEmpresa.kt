package ui.empresa

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dao.EmpresaDao
import kotlinx.coroutines.launch
import model.Empresa

@Composable
fun TelaCadastroEmpresa(
    onSalvar: () -> Unit,
    onCancelar: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Cadastrar Empresa", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )
        erro?.let {
            Text(text = it, color = MaterialTheme.colors.error)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Row(modifier = Modifier.padding(top = 16.dp)) {
            Button(onClick = {
                if (nome.isBlank()) {
                    erro = "O nome não pode ser vazio"
                    return@Button
                }
                erro = null
                coroutineScope.launch {
                    EmpresaDao.inserir(Empresa(nome = nome))
                    onSalvar()
                }
            }) {
                Text("Salvar")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    }
}
