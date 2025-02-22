plugins {
    id("questapi-java21")
    id("questapi-publications")
}

questapiPublications {
    artifactId = "questapi-api"
}
dependencies {
    api(project(":core"))
}
