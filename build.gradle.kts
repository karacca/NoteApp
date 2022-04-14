@file:Suppress("SpellCheckingInspection")

import org.jlleitschuh.gradle.ktlint.KtlintExtension

buildscript {
    extra.apply {
        set("composeVersion", "1.1.1")
        set("daggerVersion", "2.41")
    }

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("com.android.tools.build:gradle:7.1.2")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.2.1")
        val daggerVersion = rootProject.extra.get("daggerVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$daggerVersion")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    configure<KtlintExtension> {
        version.set("0.44.0")
        disabledRules.add("no-wildcard-imports")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
