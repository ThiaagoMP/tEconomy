package br.com.thiaago.teconomy.spigot.commands

import br.com.thiaago.teconomy.data.controller.AccountController
import me.saiintbrisson.minecraft.command.annotation.Command
import me.saiintbrisson.minecraft.command.annotation.Optional
import me.saiintbrisson.minecraft.command.command.Context
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BalanceCommand(private val accountController: AccountController) {

    @Command(
        name = "bal",
        aliases = ["balance", "money", "coins"],
        description = "Command to see player balance",
        usage = "bal (player)",
        permission = "balance.use"
    )
    fun handleCommand(context: Context<CommandSender>, @Optional target: OfflinePlayer?) {
        if (target != null) {
            val account = accountController.getAccount(target)
            if (account == null) {
                context.sendMessage("§cPlayer not found!")
            } else {
                context.sendMessage("§a${target.name} money: ${account.balance}")
            }
        } else {
            if (context.sender is Player) {
                val account = accountController.getAccount(context.sender as Player)
                context.sendMessage("§aMoney: ${account?.balance}")
            } else {
                context.sendMessage("§cUse /bal (player)")
                return
            }
        }
    }

}