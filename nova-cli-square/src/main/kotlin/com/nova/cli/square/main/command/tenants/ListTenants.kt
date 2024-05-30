package com.nova.cli.square.main.command.tenants

import com.nova.cli.square.main.command.AbstractListCommand
import com.nova.cli.square.main.command.CommandExecutionContext
import com.nova.cli.square.main.command.util.ArgumentValidator
import com.nova.model.tenant.Tenant
import com.nova.model.trades.Trade
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ListTenants : AbstractListCommand() {

    override val name = "tenants.list"

    override val description = "displays the trades of this OTC desk"

    override fun validate(args: List<String>) = ArgumentValidator.expectsNArgs(args, 0)

    override val header = arrayOf("COMPANY", "SHORT")

    override fun getRows(args: List<String>, context: CommandExecutionContext): Array<Array<String>> {
        val tenants: List<Tenant> = context.client.listTenants()

        return tenants.map { trade ->
            arrayOf(
                trade.companyName,
                trade.shortName,
            )
        }.toTypedArray()
    }
}