package br.com.thiaago.teconomy.data.save

import br.com.thiaago.teconomy.dao.AccountProvider
import br.com.thiaago.teconomy.data.save.controller.SaveAccountsController
import org.bukkit.OfflinePlayer
import org.bukkit.scheduler.BukkitRunnable

class SaveAccountsRunnable(
    private val saveAccountsController: SaveAccountsController, private val provider: AccountProvider
) : BukkitRunnable() {

    override fun run() {
        saveAccountsController.accounts.keys.forEach { player: OfflinePlayer ->
            val account = saveAccountsController.accounts[player] ?: return@forEach
            if (provider.hasAccount(account.player.name)) provider.updateAccount(account.player.name, account.balance)
            else provider.addAccount(account.player.name, account.balance)
        }
        saveAccountsController.accounts.clear()
    }
}