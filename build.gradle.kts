import net.kyori.indra.IndraPlugin

plugins {
    java
    id("net.kyori.indra") version "3.1.3"
    id("de.eldoria.plugin-yml.bukkit") version "0.7.1"
}

group = "me.glaremasters"
version = "1.0-SNAPSHOT"

apply {
    plugin<IndraPlugin>()
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.glaremasters.me/repository/public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("me.glaremasters:guilds:3.5.7.0")
}

bukkit {
    name = "GuildsUpkeep"
    main = "me.glaremasters.guildsupkeep.GuildsUpkeep"
    version = "1.0-SNAPSHOT"
    apiVersion = "1.21"
    authors = listOf("Glare")
    depend = listOf("Guilds")
}

tasks {
    indra {
        mitLicense()

        javaVersions {
            target(8)
        }

        github("guilds-plugin", "guilds-upkeep") {
            publishing(true)
        }

        publishAllTo("guilds", "https://repo.glaremasters.me/repository/guilds/")
    }

    compileJava {
        options.encoding = "UTF-8"
    }

    processResources {
        expand("version" to rootProject.version)
    }
}

