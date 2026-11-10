plugins {
    id("java-library")
    id("xyz.jpenilla.run-paper") version "3.1.0"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.23"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    repositories {
        maven {
            name = "lunarclient"
            url = uri("https://repo.lunarclient.dev")
        }
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven {
            name = "EngineHub"
            url = uri("https://maven.enginehub.org/repo/")
        }
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.3.build.+")

    compileOnly("com.lunarclient:apollo-api:1.3.0")

    // For Adventure support add the following dependency.
    compileOnly("com.lunarclient:apollo-extra-adventure4:1.3.0")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.4.6")
    paperweight.paperDevBundle("26.3.build.+")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("26.3")
        jvmArgs("-Xms2G", "-Xmx2G")
    }

    processResources {
        val props = mapOf("version" to version)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}
