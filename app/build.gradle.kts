plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    //id("kotlin-kapt")

    id("androidx.navigation.safeargs.kotlin") // Plugin para SafeArgs en Navigation Component
}

android {
    namespace = "com.cesar.navigationcomponentexample"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.cesar.navigationcomponentexample"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true // Habilita ViewBinding
    }
}

dependencies {

    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.material3.android)
    implementation(libs.identity.credential)
    implementation(libs.filament.android)
    val navVersion = "2.8.4"

    // CameraX Core
    //implementation(libs.camera-core)
    //implementation(libs.camera-camera2)
   //implementation(libs.camera-lifecycle)
    //implementation(libs.camera-view)

    // CameraX Video
    //implementation(libs.androidx-camera-video) // Asegúrate de tener la última versión

    // Dependencias de Navigation Component
   // implementation(libs.navigation.fragment.ktx)
   // implementation(libs.androidx.navigation.ui.ktx)

    // Navegación
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.3")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.3")

    // Dependencias principales de Android
   // implementation("androidx.room:room-ktx:2.6.1")
   // kapt("androidx.room:room-ktx:2.6.1")
    //kapt("androidx.room:room-compiler:2.6.1")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // CameraX Core

    implementation ("androidx.camera:camera-camera2:1.4.0")
    implementation ("androidx.camera:camera-core:1.4.0")
    implementation ("androidx.camera:camera-lifecycle:1.4.0")
    implementation ("androidx.camera:camera-view:1.4.0")
    implementation ("androidx.camera:camera-video:1.4.0")
    implementation ("androidx.core:core-ktx:1.4.0")






}







