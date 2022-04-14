@file:Suppress("SpellCheckingInspection")

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.task.noteapp"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.task.noteapp.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra.get("composeVersion") as String
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
        }

        getByName("test") {
            java.srcDir("src/test/kotlin")
        }

        getByName("androidTest") {
            java.srcDir("src/androidTest/kotlin")
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("androidx.room:room-runtime:2.4.2")
    implementation("androidx.room:room-ktx:2.4.2")
    kapt("androidx.room:room-compiler:2.4.2")

    val composeVersion = rootProject.extra.get("composeVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

    val daggerVersion = rootProject.extra.get("daggerVersion")
    implementation("com.google.dagger:hilt-android:$daggerVersion")
    kapt("com.google.dagger:hilt-compiler:$daggerVersion")

    implementation("com.google.android.material:material:1.5.0")
    implementation("io.coil-kt:coil-compose:2.0.0-rc01")

    testImplementation("androidx.test:core:1.4.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
    testImplementation("com.google.truth:truth:1.1.3")

    androidTestImplementation("com.google.dagger:hilt-android-testing:$daggerVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$daggerVersion")
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test:core-ktx:1.4.0")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
}

kapt {
    correctErrorTypes = true
}
