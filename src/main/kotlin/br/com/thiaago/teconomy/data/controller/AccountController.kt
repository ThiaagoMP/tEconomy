package br.com.thiaago.teconomy.data.controller

import br.com.thiaago.teconomy.dao.AccountProvider
import br.com.thiaago.teconomy.data.model.Account
import br.com.thiaago.teconomy.data.save.controller.SaveAccountsController
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

class AccountController(
    private val accounts: MutableMap<Player, Account>,
    private val accountProvider: AccountProvider,
    private val saveAccountsController: SaveAccountsController
) {

    fun getAccount(player: OfflinePlayer): Account? {
        return accounts[player]
            ?: if (accountProvider.hasAccount(player.name)) accountProvider.getAccount(player.name) else null
    }

    fun loadPlayer(player: Player) {
        if (accountProvider.hasAccount(player.name)) accounts[player] =
            accountProvider.getAccount(player.name) ?: return
        else {
            val account = Account(player, 0)
            accounts[player] = account
            saveAccountsController.add(player, account)
        }
    }

    fun setBalance(account: Account, balance: Long) {
        val player = account.player
        if (accounts.containsKey(player)) {
            account.balance = balance
            saveAccountsController.add(player, account)
        } else saveAccountsController.add(player, Account(player, balance))
    }

    fun addBalance(account: Account, balance: Long) {
        setBalance(account, account.balance + balance)
    }

    fun removeBalance(account: Account, balance: Long) {
        setBalance(account, account.balance - balance)
    }

}
