import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies

class AOCConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("testImplementation", "org.jetbrains.kotlin:kotlin-test-junit5")
                add("testRuntimeOnly", "org.junit.platform:junit-platform-launcher")
                add("testImplementation", "org.junit.jupiter:junit-jupiter-engine:5.10.3")
            }
            tasks.getByName("test") {
                (this as Test).useJUnitPlatform()
            }
        }
    }
}