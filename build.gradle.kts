buildscript {
    plugins {
        // ...

        // Add the dependency for the Google services Gradle plugin
        id("com.google.gms.google-services") version "4.4.2" apply false

    }

    dependencies {
        classpath(libs.google.services)
        classpath("com.google.gms:google-services:4.4.2")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
}