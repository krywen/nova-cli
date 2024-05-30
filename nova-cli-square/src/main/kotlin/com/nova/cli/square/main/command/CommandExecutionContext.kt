package com.nova.cli.square.main.command

import com.nova.cli.square.client.AbstractClient
import org.beryx.textio.TextTerminal

data class CommandExecutionContext(
    val client: AbstractClient,
//    val environment: Environment,
    val terminal: TextTerminal<*>
)