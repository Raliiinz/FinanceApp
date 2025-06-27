// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.detekt) apply true
}

private val versionMajor = 1
private val versionMinor = 0

val versionName by extra(initialValue = "$versionMajor.$versionMinor")
val versionCode by extra(initialValue = versionMajor * 1000 + versionMinor * 10)

// Конфигурация Detekt для всех подпроектов
allprojects {
    // Применяем плагин только к подпроектам (исключая корневой)
    if (this != rootProject) {
        apply(plugin = "io.gitlab.arturbosch.detekt")

        // Используем явное обращение к версии, так как libs недоступен здесь
        val detektVersion = "1.23.8" // Замените на вашу версию из libs.versions.toml

        configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
            toolVersion = detektVersion
            config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
            buildUponDefaultConfig = true
            autoCorrect = true
        }

        dependencies {
            detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
        }
    }
}
