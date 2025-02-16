plugins {
    id("questapi-java21")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.14"
}

group = "com.deichor.questapi.paper"
version = "1.0-SNAPSHOT"

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

dependencies {
    paperweight.foliaDevBundle("1.21.4-R0.1-SNAPSHOT")
}