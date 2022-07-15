package br.com.thiaago.teconomy.spigot.commands

import br.com.thiaago.teconomy.data.controller.AccountController
import me.saiintbrisson.minecraft.command.annotation.Command
import me.saiintbrisson.minecraft.command.command.Context
import org.bukkit.entity.Player

class PayCommand(private val accountController: AccountController) {

    @Command(
        name = "pay", description = "Command to pay player", usage = "pay (player) (amount)", permission = "pay.use"
    )
    fun handleCommand(context: Context<Player>, target: Player, args: Array<String>) {
        val accountTarget = accountController.getAccount(target)
        if (accountTarget == null) {
            context.sendMessage("§cPlayer not found!")
            return
        }
        val account = accountController.getAccount(context.sender) ?: return
        try {
            val money = args[0].toLong()
            accountController.addBalance(accountTarget, money)
            accountController.removeBalance(account, money)
            context.sendMessage("§aYou paid $money to player ${target.name}")
            target.sendMessage("§aYou received $money from player ${context.sender.name}")
        } catch (ex: NumberFormatException) {
            context.sendMessage("§cPlease use a real number!")
        }
    }

}