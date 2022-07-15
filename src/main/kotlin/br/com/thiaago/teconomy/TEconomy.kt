package br.com.thiaago.teconomy

import br.com.thiaago.teconomy.dao.AccountProvider
import br.com.thiaago.teconomy.dao.factory.ConnectionFactory
import br.com.thiaago.teconomy.data.controller.AccountController
import br.com.thiaago.teconomy.data.save.SaveAccountsRunnable
import br.com.thiaago.teconomy.data.save.controller.SaveAccountsController
import br.com.thiaago.teconomy.spigot.SpigotLoader
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection

class TEconomy : JavaPlugin() {

    private var runnable: SaveAccountsRunnable? = null

    companion object {
        var instance: TEconomy? = null
            private set;
    }

    private var saveAccountsController: SaveAccountsController? = null
    private var connection: Connection? = null
    private var accountProvider: AccountProvider? = null

    override fun onEnable() {
        instance = this
        saveDefaultConfig()
        connection = createConnection()
        saveAccountsController = SaveAccountsController(HashMap())
        if (connection == null) {
            Bukkit.getConsoleSender().sendMessage("§atEconomy - §cError connecting to database! Plugin shutting down.")
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }
        accountProvider = AccountProvider(connection!!)

        accountProvider!!.createTable()
        val accountsController = AccountController(HashMap(), accountProvider!!, saveAccountsController!!)

        SpigotLoader.load(accountsController, this)

        Bukkit.getOnlinePlayers().forEach {
            accountsController.loadPlayer(it)
        }

        initRunnable(saveAccountsController!!, accountProvider!!)
    }

    override fun onDisable() {
        savePlayers()
    }

    private fun createConnection(): Connection? {
        val section = config.getConfigurationSection("DB")
        return ConnectionFactory.getConnection(
            section.getString("pass"), section.getString("user"),
            section.getString("host"), section.getString("port"), section.getString("database")
        )
    }

    private fun initRunnable(
        saveAccountsController: SaveAccountsController,
        accountProvider: AccountProvider
    ) {
        runnable = SaveAccountsRunnable(saveAccountsController, accountProvider)

        runnable!!.runTaskTimer(
            this,
            20 * 60 * 5,
            20 * 60 * 5
        )
    }

    private fun savePlayers() {
        runnable?.run()
    }
}
