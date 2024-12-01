pluginManagement {
    includeBuild("build-logic")
    repositories {
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "aoc2024"

include(":day1")
include(":day2")
include(":utils")
