ext {
    extra["compileSdk"] = 34
    extra["targetSdk"] = 34
    extra["minSdk"] = 26
    extra["versionCode"] = 1
    extra["versionName"] = "1.6.0"
    extra["java"] = JavaVersion.VERSION_17
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.kapt)
}
tasks.register("clean").configure {
    delete(rootProject.buildDir)
}
