plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    id("org.jetbrains.kotlinx.kover") version "0.8.3" apply false
}

subprojects {
    pluginManager.apply("org.jetbrains.kotlinx.kover")
}