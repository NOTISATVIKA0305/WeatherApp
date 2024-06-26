plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.weatherapp"
    compileSdk = 34
    buildFeatures{
        viewBinding=true;

    }

    defaultConfig {
        applicationId = "com.example.weatherapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.car.ui.lib)
    implementation ("com.airbnb.android:lottie:6.4.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("javax.annotation:javax.annotation-api:1.3.2")
    implementation ("com.squareup.retrofit2:retrofit: 2.11.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}