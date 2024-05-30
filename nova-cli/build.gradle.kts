plugins {
    id("org.jetbrains.kotlin.jvm").version("1.8.0")
    application
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

dependencies {
    implementation("com.github.ajalt:clikt:2.8.0")
}
repositories {
    mavenCentral()
}

//mainClassName = "com.nova.cli.main.MainKt"
application {
    mainClassName = "com.nova.cli.main.MainKt"
    mainClass.set("com.nova.cli.main.MainKt")
}
tasks.jar {
    manifest {
        attributes(mapOf(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Main-Class" to application.mainClass,
            // link other jars
//            "Class-Path" to configurations
//                .runtimeClasspath
//                .get()
//                .joinToString(separator = " ") { file ->
//                    "libs/${file.name}"
//                }

        ))

        // make a fatJar
//        val dependencies = configurations
//            .runtimeClasspath
//            .get()
//            .map(::zipTree) // OR .map { zipTree(it) }
//        from(dependencies)
//        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
//    from(configurations.runtimeOnly.map({ if (it.isDirectory) it else zipTree(it) }))
//    configurations{
//        compile.collect { entry -> zipTree(entry) }
//    }
}
