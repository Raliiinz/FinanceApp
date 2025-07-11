plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.secrets)
}

android {
    namespace = "com.example.financeapp.network"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        defaultConfig {
            buildConfigField("String", "FINANCE_API_URL", "\"https://shmr-finance.ru/api/v1/\"")
        }

        buildFeatures{
            buildConfig = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(project(path = ":core:domain"))
    implementation(project(path = ":core:util"))
    implementation(project(path = ":core:base"))

    implementation("androidx.annotation:annotation:1.7.1")
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Network
    implementation(libs.bundles.network.deps)
}