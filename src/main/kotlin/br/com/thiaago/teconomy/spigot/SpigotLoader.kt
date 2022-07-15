package br.com.thiaago.teconomy.spigot

import br.com.thiaago.teconomy.data.controller.AccountController
import br.com.thiaago.teconomy.spigot.commands.BalanceCommand
import br.com.thiaago.teconomy.spigot.commands.EarnCommand
import br.com.thiaago.teconomy.spigot.commands.PayCommand
import br.com.thiaago.teconomy.spigot.commands.SetBalCommand
import br.com.thiaago.teconomy.spigot.listeners.PlayerListeners
import me.saiintbrisson.bukkit.command.BukkitFrame
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class SpigotLoader {

    companion object {
        fun load(accountsController: AccountController, plugin: JavaPlugin) {
            val frame = BukkitFrame(plugin)
            frame.registerCommands(
                BalanceCommand(accountsController),
                EarnCommand(accountsController),
                PayCommand(accountsController),
                SetBalCommand(accountsController)
            )
            Bukkit.getPluginManager().registerEvents(PlayerListeners(accountsController), plugin)
        }
    }

}