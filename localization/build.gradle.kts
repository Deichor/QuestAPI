plugins {
    id("java")
    id("questapi-java21")
    id("questapi-general")
}
repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.akkinoc.util", "yaml-resource-bundle", Versions.YAML_RESOURCE_BUNDLE)
}
