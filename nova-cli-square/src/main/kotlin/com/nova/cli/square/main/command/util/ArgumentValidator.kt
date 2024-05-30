package com.nova.cli.square.main.command.util

import com.nova.cli.square.main.command.Command


object ArgumentValidator {
    fun expectsNArgs(args: List<String>, n: Int) = if (args.size != n) {
        Command.Result(false, "Invalid number of arguments. Expected $n Recevied ${args.size}")
    } else {
        Command.Result(true)
    }

    fun expectsAtLeastNArgs(args: List<String>, n: Int) = if (args.size < n) {
        Command.Result(false, "Invalid number of arguments")
    } else {
        Command.Result(true)
    }

    fun expectsAtMostNArgs(args: List<String>, n: Int) = if (args.size > n) {
        Command.Result(false, "Invalid number of arguments")
    } else {
        Command.Result(true)
    }

    fun expectsBetweenNandMArgs(args: List<String>, n: Int, m: Int) = if (args.size < n || args.size > m) {
        Command.Result(false, "Invalid number of arguments")
    } else {
        Command.Result(true)
    }
}