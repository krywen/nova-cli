package com.nova.cli.square.main.command.help.input

import com.nova.cli.square.client.AbstractClient
import com.nova.cli.square.main.command.Command
import org.beryx.textio.TextIO

/**
 * Executes a command received from the console.
 */
class ConsoleInput(
    client: AbstractClient,
    textIo: TextIO,
    commands: Map<String, Command>
) : Input(
    client,
    textIo,
    commands
) {
    override fun start() {
        while (true) {
            val input = textIO.newStringInputReader().read("> ")
            execute(input)
        }
    }
}