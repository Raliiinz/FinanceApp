pluginManagement {
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

rootProject.name = "FinanceApp"
include(":app")
include(":core:base")
include(":core:domain")
include(":core:navigation")
include(":feature:category")
include(":feature:expenses")
include(":feature:income")
include(":feature:settings")
include(":feature:account")
include(":core:data")
include(":feature:history")
include(":core:util")
include(":core:network")
include(":feature:transaction")
include(":feature:analysis")
