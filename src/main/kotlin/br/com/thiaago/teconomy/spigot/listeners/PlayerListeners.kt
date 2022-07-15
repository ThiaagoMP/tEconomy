package br.com.thiaago.teconomy.spigot.listeners

import br.com.thiaago.teconomy.data.controller.AccountController
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerListeners(private val accountController: AccountController) : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        accountController.loadPlayer(event.player)
    }

}