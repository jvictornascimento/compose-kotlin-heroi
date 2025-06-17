package config

import org.jetbrains.exposed.sql.Database

fun conectarBanco() {
    Database.connect(
        url = "jdbc:mysql://localhost:3306/heroi",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = ""
    )
}