package com.nova.cli.square.main.command.help

import com.nova.cli.square.main.command.Command
import com.nova.cli.square.main.command.CommandExecutionContext

class Exit : Command {
    override val name = "exit"

    override val description = "exits from the CLI"

    override fun validate(args: List<String>): Command.Result {
        return Command.Result(true)
    }

    override fun execute(args: List<String>, context: CommandExecutionContext): Command.Result {
        System.exit(0)
        return Command.Result(true)
    }
}