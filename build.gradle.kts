import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "1.9.23" // Or the latest stable Kotlin version
    id("org.jetbrains.compose") version "1.6.10" // Or the latest stable Compose Multiplatform version
}

group = "com.example.graphviewer" // Replace with your group
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") // For Compose development versions if needed
    google()
}

dependencies {
    // Compose Multiplatform for Desktop
    implementation(compose.desktop.currentOs) // This includes foundation, runtime, ui, material etc. for the current OS
    // If you want to be more specific or only include certain parts:
    // implementation(compose.foundation)
    // implementation(compose.runtime)
    // implementation(compose.material) // Material Design components (good to have)
    // implementation(compose.ui)       // Core UI components
    // implementation(compose.materialIconsExtended) // Optional: for more icons

    // Kotlin standard library
    implementation(kotlin("stdlib"))
}

compose.desktop {
    application {
        mainClass = "MainKt" // Assuming your main function is in Main.kt

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "GraphViewerApp" // Replace with your desired app name
            packageVersion = "1.0.0"
        }
    }
}
