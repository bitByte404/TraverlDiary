    pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven (url ="https://www.jitpack.io" )
        maven (url ="https://jitpack.io" )
    }
}

    buildscript {
        repositories {
            maven (url ="https://www.jitpack.io" )
        }
    }
rootProject.name = "TravelDiary"
include(":app")
 