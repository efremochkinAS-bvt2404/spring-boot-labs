import java.time.LocalDateTime
import java.util.Properties
import java.io.File

plugins {
    id("java")
    application
    id("com.gradleup.shadow") version "9.3.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("org.example.Main")
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.apache.commons:commons-lang3:3.20.0")

    implementation("org.slf4j:slf4j-api:2.0.17")

    implementation("ch.qos.logback:logback-classic:1.5.32")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(project(":string-utils"))
}


tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaExec> {
    jvmArgs = listOf("-Dfile.encoding=UTF-8")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = "org.example.Main"
    }
}

abstract class PrintInfoTask : DefaultTask() {

    @TaskAction
    fun print() {
        println("===================================")
        println("Это моя первая пользовательская задача!")
        println("Проект: ${project.name}")
        println("Версия Gradle: ${project.gradle.gradleVersion}")
        println("===================================")
    }
}

tasks.register<PrintInfoTask>("printInfo") {
    group = "Custom"
    description = "Выводит информацию о проекте"
}

fun getGitHash(): String {
    return try {
        val process = ProcessBuilder("git", "rev-parse", "HEAD")
            .redirectErrorStream(true)
            .start()

        process.inputStream.bufferedReader().readText().trim()
    } catch (e: Exception) {
        "unknown"
    }
}

fun getNextBuildNumber(file: File): Int {
    if (!file.exists()) return 1

    val props = Properties()
    file.inputStream().use { props.load(it) }

    val old = props.getProperty("buildNumber")?.toIntOrNull() ?: 0
    return old + 1
}

tasks.register("generateBuildPassport") {
    doLast {

        val file = file("src/main/resources/build-passport.properties")
        file.parentFile.mkdirs()

        val rawUsername = System.getenv("USERNAME") ?: System.getenv("USER") ?: "unknown"
        val username = String(rawUsername.toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8)

        val os = System.getProperty("os.name")
        val javaVersion = System.getProperty("java.version")
        val date = LocalDateTime.now()

        val gitHash = getGitHash()
        val buildNumber = getNextBuildNumber(file)

        val props = Properties()

        props["user"] = username
        props["os"] = os
        props["java"] = javaVersion
        props["date"] = date.toString()
        props["message"] = "Hello from Gradle!"
        props["gitCommitHash"] = gitHash
        props["buildNumber"] = buildNumber.toString()

        file.outputStream().use {
            props.store(it, "Build Passport")
        }

        println("Файл build-passport.properties обновлён")
    }
}

tasks.named("processResources") {
    dependsOn("generateBuildPassport")
}


