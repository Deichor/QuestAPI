plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    val testImplementation by configurations
    testImplementation(TestLibs.JUPITER_API)
    testImplementation(TestLibs.JUPITER_ENGINE)
    testImplementation(TestLibs.MOCKITO_CORE)
    testImplementation(TestLibs.MOCKITO_JUPITER)
    testImplementation(TestLibs.ASSERTJ_CORE)
    
    // Add test database dependencies
    testImplementation(TestLibs.H2_DATABASE) // H2 for testing MySQL mode
    testImplementation(TestLibs.SQLITE_JDBC) // SQLite for testing SQLite mode
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}