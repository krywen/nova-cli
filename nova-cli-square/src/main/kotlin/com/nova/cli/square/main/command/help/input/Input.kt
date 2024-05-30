package com.nova.cli.square.main.command.help.input

import com.nova.cli.square.client.AbstractClient
import com.nova.cli.square.main.command.Command
import com.nova.cli.square.main.command.CommandExecutionContext
import org.beryx.textio.TextIO
import java.util.ArrayList
import java.util.regex.Pattern

abstract class Input(
    protected val client: AbstractClient,
    protected val textIO: TextIO,
    protected val commands: Map<String, Command>
) {

    protected fun execute(input: String): Boolean {
        val commandText = input.split(" ")[0]
        val terminal = textIO.textTerminal
        terminal.print("\n")
        try {
            val command = commands[commandText]
            if (command != null) {
                val commandArgs = parseArguments(input.removePrefix(commandText).trim())
                val validate = command.validate(commandArgs)
                if (!validate.success) {
                    terminal.println(validate.text ?: "Unknown validation error")
                    terminal.println(command.description)
                } else {
                    val context = CommandExecutionContext(client, terminal)
                    val result = command.execute(commandArgs, context)
                    if (result.success) {
                        terminal.println(result.text ?: "")
                        return true // command was successfully executed.
                    } else {
                        terminal.println("Error executing command: ${result.text}")
                    }
                }
            }
        } catch (ex: Exception) {
            System.err.println("Error running command")
            ex.printStackTrace()
        }
        return false
    }

    private fun parseArguments(commandArgs: String): List<String> {
        val matchList = ArrayList<String>()
        val regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'")
        val regexMatcher = regex.matcher(commandArgs)
        while (regexMatcher.find()) {
            when {
                regexMatcher.group(1) != null -> // Add double-quoted string without the quotes
                    matchList.add(regexMatcher.group(1))
                regexMatcher.group(2) != null -> // Add single-quoted string without the quotes
                    matchList.add(regexMatcher.group(2))
                else -> // Add unquoted word
                    matchList.add(regexMatcher.group())
            }
        }
        return matchList
    }

    abstract fun start()
}