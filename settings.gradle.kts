pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Innodesk"
include(":app")
include(":core:designsystem")
include(":core:common")
include(":core:network")
include(":core:sharedpref")
include(":core:model")
include(":core:datastore")
include(":core:datastore-proto")
include(":core:database")
include(":core:data")

include(":feature:project-management")
