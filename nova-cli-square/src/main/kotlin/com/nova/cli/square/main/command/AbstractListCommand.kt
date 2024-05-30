package com.nova.cli.square.main.command

import com.jakewharton.fliptables.FlipTableConverters

abstract class AbstractListCommand : Command {
    abstract val header: Array<String>

    abstract fun getRows(args: List<String>, context: CommandExecutionContext): Array<Array<String>>

    override fun execute(args: List<String>, context: CommandExecutionContext): Command.Result {
        val table = FlipTableConverters.fromObjects(header, getRows(args, context))
        return Command.Result(true, table)
    }
}