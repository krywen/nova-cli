package com.nova.cli.square.client

enum class Environment(val url: String) {
    LOCAL("http://localhost:8080/nova/v2"),
    DEV("http://localhost:8080/nova/v2"),
    STAGING("http://localhost:8080/nova/v2"),
    PROD("http://localhost:8080/nova/v2"),
}