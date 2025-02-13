import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class TestConfigurationPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            dependencies {
                "testImplementation"(TestLibs.jupiterApi)
                "testRuntimeOnly"(TestLibs.jupiterEngine)
                "testImplementation"(TestLibs.mockitoCore)
                "testImplementation"(TestLibs.mockitoJupiter)
                "testImplementation"(TestLibs.assertjCore)
            }

            tasks.withType<Test> {
                useJUnitPlatform()
            }
        }
    }
}