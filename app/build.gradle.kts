plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.app.cosmeticapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.app.cosmeticapp"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.google.firebase:firebase-auth:22.3.1")
    implementation ("com.google.firebase:firebase-database:20.3.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")
    implementation ("com.google.code.gson:gson:2.10.1")

    // Firestore
    implementation ("com.google.firebase:firebase-firestore:24.11.1")

// Firebase Storage
    implementation ("com.google.firebase:firebase-storage:20.3.0")

// Firebase Auth (already likely added, but include if missing)
    implementation ("com.google.firebase:firebase-auth:22.3.0")

// Glide (for image loading)
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")



}
apply(plugin = "com.google.gms.google-services")