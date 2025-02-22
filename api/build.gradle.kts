plugins {
    id("questapi-java21")
    id("questapi-publications")
    id("questapi-test")
}

questapiPublications {
    artifactId = "questapi-api"
}

dependencies {
    api(project(":core"))
}
