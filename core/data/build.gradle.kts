plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.ksp)
    alias(libs.plugins.secrets)
}

android {
    namespace = "com.example.financeapp.data"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        defaultConfig {
            buildConfigField("String", "FINANCE_API_URL", "\"https://shmr-finance.ru/api/v1/\"")
        }

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
            vectorDrawables.useSupportLibrary = true
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
    implementation(project(path = ":core:network"))
    implementation(project(path = ":core:base"))

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    // Network
    implementation(libs.bundles.network.deps)

    //Room
    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.ksp)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}