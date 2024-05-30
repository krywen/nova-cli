package com.nova.cli.square.main.command.trades

import com.nova.cli.square.main.command.AbstractListCommand
import com.nova.cli.square.main.command.CommandExecutionContext
import com.nova.cli.square.main.command.util.ArgumentValidator
import com.nova.model.trades.Trade
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ListTradesSince : AbstractListCommand() {

    override val name = "trades.list.since"

    override val description = "displays the historical trades since <YYYYMMDD>"

    override fun validate(args: List<String>) = ArgumentValidator.expectsNArgs(args, 1)

    override val header = arrayOf("TIMESTAMP", "TICKER", "SELLER", "BUYER")

    override fun getRows(args: List<String>, context: CommandExecutionContext): Array<Array<String>> {
        val instant = LocalDate.parse(args[0], DateTimeFormatter.BASIC_ISO_DATE).atStartOfDay().toInstant(ZoneOffset.UTC)
        val historicalTrades: List<Trade> = context.client.listTrades().filter { it.executedAt > instant }

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