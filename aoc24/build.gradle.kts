plugins {
    application
    kotlin("jvm") version "2.0.21"
}

group = "com.github.matto1matteo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

task("taskJar", Jar::class) {
    group = "build"
    description = "Create a fat jar"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "com.github.matto1matteo.MainKt"
    }

    val dependencies = configurations.runtimeClasspath.get().map(::zipTree)
    from(dependencies)
    with(tasks.jar.get())
}

application {
    mainClass = "com.github.matto1matteo.MainKt"
}

tasks.test {
    useJUnitPlatform()
}