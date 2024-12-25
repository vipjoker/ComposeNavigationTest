plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.navigationtest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.navigationtest"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.compose)

    val composeBom = platform("androidx.compose:compose-bom:2024.12.01")

    implementation(composeBom)
    implementation(libs.androidx.material3)
    // Android Studio Preview support

    implementation(libs.androidx.foundation)
    // or only import the main APIs for the underlying toolkit systems,
    // such as input and measurement/layout
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)


    // Optional - Integration with activities
    implementation(libs.androidx.activity.compose)
    // Optional - Integration with ViewModels
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}