package com.nova.cli.square.main.command

import com.nova.cli.square.main.command.help.Exit
import com.nova.cli.square.main.command.help.Help
import com.nova.cli.square.main.command.tenants.ListTenants
import com.nova.cli.square.main.command.trades.ListTradesAll
import com.nova.cli.square.main.command.trades.ListTradesOf
import com.nova.cli.square.main.command.trades.ListTradesSince

object Commands {
    val commands = listOf(
        ListTradesOf(),
        ListTradesSince(),
        ListTradesAll(),
        ListTenants(),
        Help(),
        Exit(),
    )
}