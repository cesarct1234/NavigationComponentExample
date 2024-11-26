// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false  // Define el plugin para una aplicación Android
    alias(libs.plugins.kotlin.android) apply false  // Define el plugin para Kotlin en Android
   // alias(libs.plugins.kotlinPlugin)
    //alias(libs.plugins.kotlin.kapt)

    id("androidx.navigation.safeargs.kotlin") version "2.8.4" apply false  // Plugin de Safe Args para Navigation Component

}
