plugins {
    id("java-library")
    id("maven-publish")
}

group = "com.deichor"
version = "0.1.2-SNAPSHOT"

publishing {
    java {
        withSourcesJar()
        withJavadocJar()
    }

    repositories {
        mavenLocal()

        maven(
            name = "repooyunzor",
            url = "https://repo.oyunzor.com",
            username = EnvLoader.getRequired("OZ_REPOSILITE_USER"),
            password = EnvLoader.getRequired("OZ_REPOSILITE_TOKEN"),
            snapshots = true,
            beta = true,
        )
    }
}

fun RepositoryHandler.maven(
    name: String,
    url: String,
    username: String,
    password: String,
    snapshots: Boolean = true,
    beta: Boolean = false
) {
    val isSnapshot = version.toString().endsWith("-SNAPSHOT")

    if (isSnapshot && !snapshots) {
        return
    }

    val isBeta = version.toString().contains("-BETA")

    if (isBeta && !beta) {
        return
    }

    this.maven {
        this.name =
            if (isSnapshot) "${name}Snapshots"
            else "${name}Releases"

        this.url =
            if (isSnapshot) uri("$url/snapshots")
            else uri("$url/releases")

        this.credentials {
            this.username = EnvLoader.getRequired("OZ_REPOSILITE_USER")
            this.password = EnvLoader.getRequired("OZ_REPOSILITE_TOKEN")
        }
    }
}

interface QuestPublicationExtension {
    var artifactId: String
}

val extension = extensions.create<QuestPublicationExtension>("questapiPublications")

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                artifactId = extension.artifactId
                from(project.components["java"])
            }
        }
    }
}