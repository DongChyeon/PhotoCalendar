import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.mavenPublish)
}

kotlin {
    androidLibrary {
        namespace = "com.dongchyeon.calendar"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }

        androidResources.enable = true
    }

    jvm("desktop") {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    val xcfName = "calendarKit"

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcfName
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlin.stdlib)

            // Compose core (multiplatform)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // KotlinX
            implementation(libs.kotlinx.datetime)
            implementation(libs.coil.compose)
            implementation(libs.coil.compose.core)
            implementation(libs.coil.network.ktor3)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.testExt.junit)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }

        val desktopMain by getting
        desktopMain.dependencies {
            implementation(libs.ktor.client.java)
        }
    }
}

compose {
    // optional, enables multiplatform resource access and hot reload
    resources {
        publicResClass = true
    }
}

mavenPublishing {
    coordinates("io.github.dongchyeon", "photo-calendar", "1.1.1")

    pom {
        name.set("PhotoCalendar")
        description.set("Multiplatform customizable Compose calendar component")
        url.set("https://github.com/DongChyeon/PhotoCalendar")

        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("DongChyeon")
                name.set("DongChyeon")
                url.set("https://github.com/DongChyeon")
            }
        }
        scm {
            url.set("https://github.com/DongChyeon/PhotoCalendar.git")
            connection.set("scm:git:git://github.com/DongChyeon/PhotoCalendar.git")
            developerConnection.set("scm:git:ssh://git@github.com:DongChyeon/PhotoCalendar.git")
        }
    }
}