package ui.pedido

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dao.EmpresaDao
import dao.ItemDao
import dao.PedidoDao
import model.Item
import model.Pedido
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun TelaCadastroPedido(
    onSalvar: () -> Unit,
    onCancelar: () -> Unit
) {
    var codigo by remember { mutableStateOf("") }
    var dataCompra by remember { mutableStateOf("") }
    val empresas = EmpresaDao.listarTodos()
    val itensDisponiveis = ItemDao.listarTodos()

    var empresaSelecionada by remember { mutableStateOf<Int?>(null) }
    val itensSelecionados = remember { mutableStateListOf<Item>() }

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Cadastro de Pedido", style = MaterialTheme.typography.h5)

        Text("Empresa:")
        empresas.forEach { empresa ->
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                RadioButton(
                    selected = empresaSelecionada == empresa.id,
                    onClick = { empresaSelecionada = empresa.id }
                )
                Text(empresa.nome)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)) {

            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it },
                label = { Text("Número do Pedido") },
                modifier = Modifier
                    .width(300.dp)
                    .height(80.dp)
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = dataCompra,
                onValueChange = { dataCompra = it },
                label = { Text("Data da Compra (DD/MM/AAAA)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .width(300.dp)
                    .height(80.dp)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Itens:")
        SeletorDeItens(
            itensDisponiveis = itensDisponiveis,
            itensSelecionados = itensSelecionados
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)) {
            Button(onClick = {
                try {
                    if (empresaSelecionada != null) {
                        val dataFormatada = LocalDate.parse(dataCompra, formatter)

                        PedidoDao.inserir(
                            Pedido(
                                codigo = codigo,
                                dataCompra = dataFormatada,
                                empresaId = empresaSelecionada!!,
                                itens = itensSelecionados.map { it.id }
                            )
                        )
                        onSalvar()
                    }
                } catch (e: DateTimeParseException) {
                }
            }) {
                Text("Salvar Pedido")
            }

            Spacer(modifier = Modifier.width(20.dp))

            Button(onClick = {
                onCancelar()
            }) {
                Text("Cancelar")
            }
        }
    }
}
