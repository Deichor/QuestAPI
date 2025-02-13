plugins {
    id("java")
    id("io.freefair.lombok") version "8.4"
    id("test-configuration")
}

group = "com.deichor.questapi.core"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:${Versions.lombok}")
    annotationProcessor("org.projectlombok:lombok:${Versions.lombok}")
}
