package br.com.thiaago.teconomy.dao.factory

import org.bukkit.Bukkit
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object ConnectionFactory {

    private const val type = "jdbc:mysql://"
    private const val driverName = "com.mysql.jdbc.Driver"
    private var connection: Connection? = null

    fun getConnection(pass: String?, user: String?, host: String, port: String, database: String): Connection? {
        val url = "$type$host:$port/$database?autoReconnect=true"
        try {
            Class.forName(driverName)
            try {
                connection = DriverManager.getConnection(url, user, pass)
            } catch (ex: SQLException) {
                Bukkit.getConsoleSender().sendMessage("§2[tEconomy] §cERROR CONNECTING TO DATABASE!")
            }
        } catch (ex: ClassNotFoundException) {
            Bukkit.getConsoleSender().sendMessage("§2[tEconomy] §cERROR CONNECTING TO DATABASE (DRIVER NOT FOUND)!")
        }
        return connection
    }
}