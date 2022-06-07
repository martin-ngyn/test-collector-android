pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}
include(":example-project")
include(":test-collector")
include(":test-collector:instrumented-test-collector-library")
include(":test-collector:unit-test-collector-plugin")
include(":test-collector:test-data-uploader")
