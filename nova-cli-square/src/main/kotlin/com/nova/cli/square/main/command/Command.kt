package com.nova.cli.square.main.command

interface Command {
    val name: String

    val description: String

    fun validate(args: List<String>): Result

    fun execute(args: List<String>, context: CommandExecutionContext): Result

    data class Result(val success: Boolean, val text: String? = null)
}