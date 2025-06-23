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
fun TelaEditarPedido(
    pedidoOriginal: Pedido,
    onSalvar: () -> Unit,
    onCancelar: () -> Unit
) {
    var codigo by remember { mutableStateOf(pedidoOriginal.codigo) }
    var dataCompra by remember { mutableStateOf(pedidoOriginal.dataCompra.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) }

    val empresas = EmpresaDao.listarTodos()
    var empresaSelecionada by remember { mutableStateOf(pedidoOriginal.empresaId) }

    val itensDisponiveis = ItemDao.listarTodos()
    val itensSelecionados = remember { mutableStateListOf<Item>() }

    // preencher itensSelecionados a partir do pedidoOriginal.itens (lista de IDs)
    LaunchedEffect(pedidoOriginal) {
        itensSelecionados.clear()
        val selecionados = pedidoOriginal.itens.mapNotNull { id -> itensDisponiveis.find { it.id == id } }
        itensSelecionados.addAll(selecionados)
    }

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text("Editar Pedido", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = codigo,
            onValueChange = { codigo = it },
            label = { Text("Número do Pedido") },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = dataCompra,
            onValueChange = { dataCompra = it },
            label = { Text("Data da Compra (DD/MM/AAAA)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(bottom = 8.dp)
        )

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

        Text("Itens:")
        SeletorDeItens(itensDisponiveis = itensDisponiveis, itensSelecionados = itensSelecionados)

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = {
                try {
                    val dataFormatada = LocalDate.parse(dataCompra, formatter)
                    val pedidoAtualizado = Pedido(
                        id = pedidoOriginal.id,
                        codigo = codigo,
                        dataCompra = dataFormatada,
                        empresaId = empresaSelecionada,
                        itens = itensSelecionados.map { it.id }
                    )
                    PedidoDao.atualizar(pedidoAtualizado)
                    onSalvar()
                } catch (e: DateTimeParseException) {
                    // você pode exibir um erro mais amigável, aqui só printa
                    println("Data inválida: ${e.message}")
                }
            }) {
                Text("Salvar Alterações")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = { onCancelar() }) {
                Text("Cancelar")
            }
        }
    }
}
