package br.com.thiaago.teconomy.data.save.controller

import br.com.thiaago.teconomy.data.model.Account
import org.bukkit.OfflinePlayer

class SaveAccountsController(val accounts: MutableMap<OfflinePlayer, Account>) {

    fun add(player: OfflinePlayer, account: Account) {
        accounts.remove(player)
        accounts[player] = account
    }

}