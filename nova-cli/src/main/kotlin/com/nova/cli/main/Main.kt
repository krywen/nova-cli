package com.nova.cli.main

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int


class ActionCommand : CliktCommand(
    name = "action",
    help = "what do you do?",
    printHelpOnEmptyArgs = true,
) {
//    val action by option(help = "what do you do?").choice("Fight", "Run", ignoreCase = true)
    val action by argument(help = "Input file").choice("Fight", "Run")

//    val compress by option(help = "Enable compression").flag()
//    val level by option(help = "Compression level").int().default(6)

    override fun run() {
        echo("action: $action")
        when(action) {
            "Fight" -> echo("... you fight and win")
            "Run" -> echo("... you Run and lose")
            else -> echo("uh?")
        }
    }
}

class DropCommand : CliktCommand(name = "drop", help = "what do you drop?") {
    //    val action by option(help = "what do you do?").choice("Fight", "Run", ignoreCase = true)
    val action by argument(help = "Input file").choice("sword", "axe")

//    val compress by option(help = "Enable compression").flag()
//    val level by option(help = "Compression level").int().default(6)

    override fun run() {
        echo("action: $action")
        when(action) {
            "sword" -> echo("you drop the sword")
            "axe" -> echo("... you frop the axe")
            else -> echo("uh?")
        }
    }
}

class Echo : CliktCommand() {
    val name by option(help = "name to echo")
    override fun run() {
        while (true) {

            val userInput = readLine()!!
            if (userInput == "exit") {
                return
            }
            println("You entered: $userInput")
        }
    }
}

class MainChoice : CliktCommand(name = "main", help = "choose your adventure", printHelpOnEmptyArgs = true) {
//    val name by option(help = "name to echo")

    override fun run() {
        println("type something..")
        while (true) {
            val userInput = readLine()!!
            if (userInput == "exit") {
                return
            }
            val commandStr = userInput.split(" ").firstOrNull() ?: continue
            println("You entered: $userInput")
            when (commandStr) {
                ActionCommand().commandName -> ActionCommand().main(userInput.split(" "))
                DropCommand().commandName -> DropCommand().main(userInput.split(" "))
                else -> println("not recognised, try again")
            }
        }
    }
}

fun main(args: Array<String>) = MainChoice().main(args)
