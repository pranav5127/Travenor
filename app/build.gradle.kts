import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.pranav.travenor"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.pranav.travenor"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "SUPABASE_URL",
            "\"${localProperties.getProperty("SUPABASE_URL") ?: ""}\""
        )
        buildConfigField(
            "String",
            "SUPABASE_ANON_KEY",
            "\"${localProperties.getProperty("SUPABASE_ANON_KEY") ?: ""}\""
        )
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
    kotlin {
        jvmToolchain(11)
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime.saveable)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // SplashScreen
    implementation(libs.androidx.core.splashscreen)

    // Extended Icons
    implementation(libs.androidx.compose.material.icons.extended.android)

    // On boarding
    implementation(libs.foundation.pager)

    // Navigation3 dependencies
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.android.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    // Serialization dependency
    implementation(libs.kotlinx.serialization.json)

    // Koin dependency
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.coroutines)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.annotation)
    ksp(libs.koin.ksp.compiler)

    testImplementation(libs.koin.test)
    testImplementation(libs.koin.android.test)
    testImplementation(libs.mockito)


    // supabase
    implementation(platform(libs.supabase.bom))
    implementation(libs.auth.kt)
    implementation(libs.ktor.client.okhttp)

}
