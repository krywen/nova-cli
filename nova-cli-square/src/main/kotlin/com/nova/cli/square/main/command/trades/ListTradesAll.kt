package com.nova.cli.square.main.command.trades

import com.nova.cli.square.main.command.AbstractListCommand
import com.nova.cli.square.main.command.CommandExecutionContext
import com.nova.cli.square.main.command.util.ArgumentValidator
import com.nova.model.trades.Trade
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ListTradesAll : AbstractListCommand() {

    override val name = "" +
            "trades.list"

    override val description = "displays all trades"

    override fun validate(args: List<String>) = ArgumentValidator.expectsNArgs(args, 0)

    override val header = arrayOf("TIMESTAMP", "TICKER", "SELLER", "BUYER")

    override fun getRows(args: List<String>, context: CommandExecutionContext): Array<Array<String>> {
        val historicalTrades: List<Trade> = context.client.listTrades()

        return historicalTrades.map { trade ->
            arrayOf(
                trade.executedAt.toString(),
                trade.ticker.toString(),
                trade.seller.companyName,
                trade.buyer.companyName,
            )
        }.toTypedArray()
    }
}