plugins {
    kotlin("jvm").version("1.8.20")
}

val pluginGroup: String by project
val pluginAuthor: String by project
val pluginVersion: String by project
val serverVersion: String by project

group = pluginGroup
version = pluginVersion

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.destroystokyo.com/repository/maven-public/")
    }
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:$serverVersion")
}

tasks {
    processResources {
        val properties = mapOf("version" to pluginVersion, "author" to pluginAuthor)
        inputs.properties(properties)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml"){
            expand(properties)
        }
    }
    jar {
        from(configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}
