package br.com.thiaago.teconomy.data.model

import org.bukkit.OfflinePlayer

data class Account(val player: OfflinePlayer, var balance: Long)
