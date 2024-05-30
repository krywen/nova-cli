package com.nova.cli.square.main

import com.nova.cli.square.client.Environment
import com.nova.cli.square.client.MockClient
import com.nova.cli.square.main.command.Commands
import com.nova.cli.square.main.command.help.input.ConsoleInput
import org.beryx.textio.TextIoFactory


fun main(args: Array<String>) {
    val textIO = TextIoFactory.getTextIO()

    val env = textIO.newEnumInputReader(Environment::class.java).read("Environment")

    val email = textIO.newStringInputReader().read("Email")

    val password = textIO.newStringInputReader()
        .withInputMasking(true)
        .read("Password")

    val otp = textIO.newStringInputReader().read("OTP")

    val terminal = textIO.textTerminal

    val client = MockClient(env, email, password, otp, terminal)

//    val token = client.connect()
//    if (token == null) {
//        System.err.println("Timeout while logging in")
//        return System.exit(-1)
//    }

    terminal.println("Connected")

    val commands = Commands.commands.associateBy { it.name }

    val input = ConsoleInput(client, textIO, commands)
    input.start()

    terminal.println("input finished. Cleaning up ...")
    client.disconnect()
}