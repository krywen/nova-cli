package com.nova.cli.square.main.command.help

import com.jakewharton.fliptables.FlipTableConverters
import com.nova.cli.square.main.command.Command
import com.nova.cli.square.main.command.CommandExecutionContext
import com.nova.cli.square.main.command.Commands

class Help : Command {
    override val name = "help"

    override val description = "Displays all available commands"

    override fun validate(args: List<String>) = Command.Result(true)

    override fun execute(args: List<String>, context: CommandExecutionContext): Command.Result {
        val header = arrayOf("COMMAND", "DESCRIPTION")

        val rows = mutableListOf<Array<String>>()

        Commands.commands.forEach {
            rows.add(arrayOf(it.name, it.description))
        }

        val table = FlipTableConverters.fromObjects(header, rows.toTypedArray())
        return Command.Result(true, table)
    }
}