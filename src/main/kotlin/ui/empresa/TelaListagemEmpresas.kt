package ui.empresa

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dao.EmpresaDao
import kotlinx.coroutines.launch
import model.Empresa
import ui.item.MostrarDetalhesItem

@Composable
fun TelaListagemEmpresas(onEditar: (Empresa)-> Unit) {
    var empresas by remember { mutableStateOf<List<Empresa>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            empresas = EmpresaDao.listarTodos()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Empresas", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(8.dp))

        empresas.forEach { empresa ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ) {
                Text("Código: ${empresa.id} - ${empresa.nome}", modifier = Modifier.weight(1f))
                Button(onClick = { onEditar(empresa) }) {
                    Text("Editar")
                }
            }
        }
    }
}