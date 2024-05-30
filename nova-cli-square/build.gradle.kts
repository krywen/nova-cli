plugins {
    id("org.jetbrains.kotlin.jvm").version("1.8.0")
    application
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

dependencies {
    implementation("com.github.ajalt:clikt:2.8.0")
    implementation("org.beryx:text-io:3.4.1")
    implementation("com.jakewharton.fliptables:fliptables:1.1.0")
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation(project(":model-common"))
}
repositories {
    mavenCentral()
}

application {
    mainClassName = "com.nova.cli.square.main.MainKt"
    mainClass.set("com.nova.cli.square.main.MainKt")
}
tasks.jar {
    manifest {
        attributes(mapOf(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Main-Class" to application.mainClass,
        ))
    }
}
