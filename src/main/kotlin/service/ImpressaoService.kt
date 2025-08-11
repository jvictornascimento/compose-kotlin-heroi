package service

import dao.EmpresaDao
import model.Item
import model.Pedido
import net.sf.jasperreports.engine.JREmptyDataSource
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.export.JRPrintServiceExporter
import net.sf.jasperreports.export.SimpleExporterInput
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration
import net.sf.jasperreports.view.JasperViewer
import java.io.File
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class ImpressaoService {
    fun gerarPreviewEtiquetaMes() {
        try {
            val hoje = LocalDate.now()
            val parametros = mutableMapOf<String, Any?>(
                "ano" to hoje.year.toString(),
                "mes" to hoje.month.getDisplayName(TextStyle.FULL, Locale("pt", "BR")).uppercase()
            )

            val arquivo = File("src/relatorios/mes/etiqueta_4_colunas.jrxml")
            val imagemPath = File("src/relatorios/img/logo.png").absolutePath
            parametros["imagem"] = imagemPath

            val report = JasperCompileManager.compileReport(arquivo.absolutePath)
            val jasperPrint = JasperFillManager.fillReport(report, parametros, JREmptyDataSource())

            // Criar arquivo temporário PDF
            val tempFile = File.createTempFile("etiqueta_mes_", ".pdf")
            tempFile.deleteOnExit() // garante exclusão no final da JVM

            // Exportar para PDF
            net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfFile(jasperPrint, tempFile.absolutePath)

            // Abrir no Acrobat Reader (ou outro programa padrão de PDF)
            val desktop = java.awt.Desktop.getDesktop()
            desktop.open(tempFile)

            // Thread para aguardar e apagar o PDF
            Thread {
                val logFile = File("LOG_IMPRESSAO.txt") // raiz do programa
                val logMensagem = "[${java.time.LocalDateTime.now()}] Impressão de etiqueta do mês gerada: ${tempFile.name}\n"

                try {
                    // Cria ou acrescenta no arquivo
                    logFile.appendText(logMensagem, Charsets.UTF_8)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                Thread.sleep(15_000) // espera 15 segundos
                if (tempFile.exists()) {
                    tempFile.delete()
                    println("Arquivo temporário apagado.")
                }
            }.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
    fun gerarPreviewEtiqueta(item: Item, lote: String, modelo: File, pedido: Pedido) {
        try {
            val parametros = mutableMapOf<String, Any?>(
                "descricao" to item.descricao,
                "codigoRusso" to item.codigoRusso,
                "codigoMali" to item.codigoMali,
                "temperatura" to (item.temperatura ?: ""),
                "ip" to (item.ip ?: 0),
                "ledPorMetro" to (item.ledPorMetro ?: 0),
                "Modelo" to (item.modelo ?: ""),
                "ampere" to (item.amper ?: 0),
                "watts" to (item.watts ?: 0),
                "gtin" to (item.gtin ?: 0L),
                "tipoLed" to (item.tipoLed ?: ""),
                "fluxoLuminoso" to (item.fluxoLuminoso ?: ""),
                "volts" to (item.volt ?: 0),
                "lote" to (lote.toIntOrNull() ?: 0)
            )
            if ("direto_na_rede" in modelo.nameWithoutExtension) {
                val imagemPath = File("src/relatorios/img/PlacaA5.png").absolutePath
                parametros["imagem"] = imagemPath
            }
            if ("100x40" in modelo.nameWithoutExtension ||
                "3_colunas" in modelo.nameWithoutExtension
            ) {
                val imagemPath = File("src/relatorios/img/logo.png").absolutePath
                parametros["imagem"] = imagemPath
            }
            if ("100x150" in modelo.nameWithoutExtension) {
                val imagemPath = File("src/relatorios/img/logoBranco.png").absolutePath
                parametros["imagem"] = imagemPath
            }
            if ("identificação" in modelo.nameWithoutExtension) {
                val empresa = EmpresaDao.buscarPorCodifo(pedido.empresaId)?.nome ?: ""
                parametros["empresa"] = empresa
            }


            val report = JasperCompileManager.compileReport(modelo.absolutePath)
            val jasperPrint = JasperFillManager.fillReport(report, parametros, JREmptyDataSource())
            JasperViewer.viewReport(jasperPrint, false)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }