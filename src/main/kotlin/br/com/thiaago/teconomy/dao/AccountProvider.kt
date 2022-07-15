package br.com.thiaago.teconomy.dao

import br.com.thiaago.teconomy.data.model.Account
import org.bukkit.Bukkit
import java.sql.Connection

class AccountProvider(private val connection: Connection) {

    private val tableName = "teconomy_accounts"
    private val playerNameField = "playerName"
    private val balanceField = "balance"

    fun createTable() {
        val sql = "CREATE TABLE IF NOT EXISTS $tableName($playerNameField varchar(20), $balanceField BIGINT);"
        val statement = connection.prepareStatement(sql)
        statement.execute()
        statement.close()
    }

    fun addAccount(playerName: String, balance: Long) {
        val sql = "INSERT INTO $tableName($playerNameField, $balanceField) VALUES (?,?);"
        val statement = connection.prepareStatement(sql)
        statement.setString(1, playerName)
        statement.setLong(2, balance)
        statement.execute()
        statement.close()
    }

    fun remove(playerName: String) {
        val sql = "DELETE FROM $tableName WHERE $playerNameField = ?;"
        val statement = connection.prepareStatement(sql)
        statement.setString(1, playerName)
        statement.execute()
        statement.close()
    }

    fun updateAccount(playerName: String, balance: Long) {
        val sql = "UPDATE $tableName SET $balanceField = ? WHERE $playerNameField = ?;"
        val statement = connection.prepareStatement(sql)
        statement.setLong(1, balance)
        statement.setString(2, playerName)
        statement.execute()
        statement.close()
    }

    fun hasAccount(playerName: String): Boolean {
        val sql = "SELECT * FROM $tableName WHERE $playerNameField = ?;"
        val statement = connection.prepareStatement(sql)
        statement.setString(1, playerName)
        val query = statement.executeQuery()
        val has = query.next()
        statement.close()
        query.close()
        return has
    }

    fun getAccount(playerName: String): Account? {
        val sql = "SELECT * FROM $tableName WHERE $playerNameField = ?;"
        val statement = connection.prepareStatement(sql)
        statement.setString(1, playerName)
        val query = statement.executeQuery()
        var account: Account? = null
        if (query.next()) {
            account = Account(
                Bukkit.getOfflinePlayer(query.getString(playerNameField)), query.getLong(balanceField)
            )
        }
        statement.close()
        query.close()
        return account
    }

}