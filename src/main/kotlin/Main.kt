import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import config.conectarBanco
import ui.app

fun main() = application {
    conectarBanco()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Heroi | Controle de Lote",
        state = WindowState(placement = WindowPlacement.Maximized)
    ) {
        app()
    }
}
