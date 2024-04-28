pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenCentral()
        maven { url =uri("https://maven.google.com") }
        gradlePluginPortal()
        jcenter()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenCentral()
        maven { url =uri("https://maven.google.com") }
        gradlePluginPortal()
        jcenter()
    }
}

rootProject.name = "MusicApp"
include(":app")
