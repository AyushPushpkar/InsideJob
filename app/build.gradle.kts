plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.insidejob"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.insidejob"
        minSdk = 25
        targetSdk = 34
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
    buildFeatures{
        viewBinding = true
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.28")
    implementation("com.airbnb.android:lottie:6.4.0")
    implementation("io.github.shashank02051997:FancyToast:2.0.2")
    implementation ("com.github.emreesen27:Android-Custom-Toast-Message:1.0.5")
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.animation.graphics.android)
    //firebase
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //sweetalert
   implementation("com.github.taimoorsultani:android-sweetalert2:2.0.2")
    implementation("com.google.android.material:material:1.9.0")
    //motion toast
    implementation ("com.github.Spikeysanju:MotionToast:1.4")
    //andoid anim
    implementation ("com.github.gayanvoice:android-animations-kotlin:1.0.1")
    //loading
    implementation ("com.github.ybq:Android-SpinKit:1.4.0")
    //time range
    implementation ("nl.joery.timerangepicker:timerangepicker:1.0.0")
    //coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-firestore:25.0.0")
    implementation ("com.google.firebase:firebase-auth:23.0.0")
    implementation ("com.google.firebase:firebase-database:21.0.0")
    implementation ("com.google.firebase:firebase-storage:21.0.0")

    //picasso
    implementation ("com.squareup.picasso:picasso:2.8")

    //efficient image loading, caching, image transformations, and support for animated GIFs and WebP images
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    implementation ("com.google.android.gms:play-services-auth:20.2.0")  // Ensure you have the latest version

    implementation ("androidx.appcompat:appcompat:1.4.1")
    implementation ("androidx.core:core-ktx:1.7.0")
}