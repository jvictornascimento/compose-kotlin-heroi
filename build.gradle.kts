import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "com.jvictornascimento"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.exposed:exposed-core:0.45.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.45.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.45.0")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("org.jetbrains.compose.desktop:desktop:1.5.10")
    implementation("org.jetbrains.exposed:exposed-java-time:0.50.1")
    implementation("net.sf.jasperreports:jasperreports:6.21.4")
    implementation("org.apache.commons:commons-collections4:4.4") // Necessário para Jasper
    implementation("net.sf.barcode4j:barcode4j:2.1")
    implementation("org.apache.xmlgraphics:batik-all:1.19")
    implementation("org.apache.xmlgraphics:xmlgraphics-commons:2.11")

}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Heroi"
            packageVersion = "1.0.0"
        }
    }
}
