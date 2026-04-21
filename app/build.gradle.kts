plugins {
    id("com.android.application")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.app"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        // Enable minification and code shrinking
        buildTypes {
            release {
                isMinifyEnabled = true
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                // Add R8 in release mode
                useProguard = false 
            }
            debug {
                isMinifyEnabled = false
            }
        }
    }

    // Optimize performance
    dexOptions {
        javaMaxHeapSize = "4g"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.10.0")
}
