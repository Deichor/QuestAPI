plugins {
    kotlin("jvm") version "2.0.21"
    `kotlin-dsl`
}

group = "com.deichor.questapi.buildSrc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}