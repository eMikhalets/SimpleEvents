plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.emikhalets.simpleevents"
    compileSdk = rootProject.extra["compileSdk"] as Int
    defaultConfig {
        applicationId = "com.emikhalets.simpleevents"
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int
        versionCode = rootProject.extra["versionCode"] as Int
        versionName = rootProject.extra["versionName"] as String
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
        kapt { arguments { arg("room.schemaLocation", "$projectDir/schemas") } }
    }
    buildTypes {
        debug {
            versionNameSuffix = ".debug"
            applicationIdSuffix = ".debug"
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = rootProject.extra["java"] as JavaVersion
        targetCompatibility = rootProject.extra["java"] as JavaVersion
    }
    kotlinOptions {
        jvmTarget = rootProject.extra["java"].toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxCompose.get()
    }
    packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
}
dependencies {

    implementation(libs.androidx.core)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.androidx.coroutines)
    implementation(libs.bundles.androidx.room)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidx.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.hilt.navigation)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.work)
    implementation(libs.google.hilt.android)
    implementation(libs.jsoup)
    implementation(libs.kotlin.serialization.json)

    kapt(libs.google.hilt.compiler)
    kapt(libs.androidx.room.compiler)

//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
}
kapt {
    correctErrorTypes = true
}
