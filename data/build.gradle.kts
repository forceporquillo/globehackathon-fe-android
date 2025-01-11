plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.forcecodes.auth.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(project(":domain"))

    testImplementation(libs.junit)

    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)

    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.paging3.runtime)
    implementation(libs.room.runtime)

    implementation(libs.retrofit.runtime)
    implementation(libs.retrofit.moshi)
    implementation(libs.moshi)

    ksp(libs.moshi.kotlin.codegen)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.room.ktx)

    implementation(libs.okhttp)
//    implementation(libs.logging.interceptor)
}
