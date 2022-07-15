package br.com.thiaago.teconomy.spigot.commands

import br.com.thiaago.teconomy.data.controller.AccountController
import me.saiintbrisson.minecraft.command.annotation.Command
import me.saiintbrisson.minecraft.command.command.Context
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

class SetBalCommand(private val accountController: AccountController) {

    @Command(
        name = "setbal",
        aliases = ["setbalance", "setmoney", "setcoins"],
        description = "Command to set player balance",
        usage = "setbal (player) (amount)",
        permission = "setbal.use"
    )
    fun handleCommand(context: Context<Player>, target: OfflinePlayer, args: Array<String>) {
        val accountTarget = accountController.getAccount(target)
        if (accountTarget == null) {
            context.sendMessage("§cPlayer not found!")
            return
        }
        try {
            val money = args[0].toLong()
            accountController.setBalance(accountTarget, money)
            context.sendMessage("§aMoney set!")
        } catch (ex: NumberFormatException) {
            context.sendMessage("§cPlease use a real number!")
        }
    }

}