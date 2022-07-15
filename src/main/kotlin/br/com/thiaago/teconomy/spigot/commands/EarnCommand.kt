package br.com.thiaago.teconomy.spigot.commands

import br.com.thiaago.teconomy.data.controller.AccountController
import me.saiintbrisson.minecraft.command.annotation.Command
import me.saiintbrisson.minecraft.command.command.Context
import org.bukkit.entity.Player
import java.util.*

class EarnCommand(private val accountController: AccountController) {

    @Command(
        name = "earn", description = "Command to get money randomly", usage = "earn", permission = "earn.use"
    )
    fun handleCommand(context: Context<Player>) {
        val account = accountController.getAccount(context.sender) ?: return
        val random = Random()
        val moneySorted = random.nextLong(1, 5)
        accountController.addBalance(account, moneySorted)
        if (moneySorted == 5L) context.sendMessage("§aCongratulations you won the top prize!")
        context.sendMessage("§aYou won $moneySorted coins")
    }

}