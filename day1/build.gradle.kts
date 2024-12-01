plugins {
    alias(libs.plugins.kotlin.jvm)
    id("lennartegb.aoc.convention")
}

dependencies {
    implementation(project(":utils"))
}
