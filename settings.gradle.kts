dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "aoc2024"
include(":day1")
include(":day2")
include(":utils")
