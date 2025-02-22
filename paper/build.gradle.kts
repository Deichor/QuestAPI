plugins {
    id("java")
    id("questapi-java21")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.14"
    id("questapi-general")
    id("questapi-publications")
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

dependencies {
    paperweight.foliaDevBundle("1.21.4-R0.1-SNAPSHOT")
    api(project(":localization"))
    api(project(":api"))
}

questapiPublications {
    artifactId = "questapi-paper"
}