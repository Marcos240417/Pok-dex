plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.newpokedex"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.newpokedex"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    dependencies {
        // Core Android e Lifecycle
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.navigation.compose)
        // Ícones básicos
        implementation(libs.androidx.compose.material.icons.core)
        // Biblioteca completa (opcional, contém milhares de ícones)
        implementation(libs.androidx.compose.material.icons.extended)

        // Jetpack Compose (ÚNICA DECLARAÇÃO DO BOM)
        implementation(platform(libs.androidx.compose.bom.v20260800))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)

        // Room Database + Room Paging
        implementation(libs.androidx.room.runtime.v284)
        implementation(libs.androidx.room.ktx.v284)
        implementation(libs.androidx.room.paging)
        ksp(libs.androidx.room.compiler.v284)

        // Paging 3
        implementation(libs.androidx.paging.runtime.ktx)
        implementation(libs.androidx.paging.compose)

        // Retrofit + Serialization + Gson
        implementation(libs.retrofit)
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.converter.kotlinx.serialization)
        implementation(libs.converter.gson)

        // Coroutines
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.coroutines.android)

        // Koin
        implementation(libs.koin.android)
        implementation(libs.koin.androidx.compose)

        // Coil
        implementation(libs.coil.compose)

        // Testes Unitários
        testImplementation(libs.junit)

        // Testes de Instrumentação (SEM plataforma duplicada)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)

        // Debug (SEM plataforma duplicada)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
    }
}