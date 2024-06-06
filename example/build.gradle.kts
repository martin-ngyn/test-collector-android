import com.buildkite.test.collector.android.tracer.environment.TestEnvironmentValue.BUILDKITE_ANALYTICS_DEBUG_ENABLED
import com.buildkite.test.collector.android.tracer.environment.TestEnvironmentValue.BUILDKITE_ANALYTICS_TOKEN

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.buildkite.test.collector.android.example"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Specifies `InstrumentedTestCollector` as the instrumented test listener for collecting test analytics.
        testInstrumentationRunnerArguments["listener"] =
            "com.buildkite.test.collector.android.InstrumentedTestCollector"

        // Passes environment variables as instrumentation arguments
        testInstrumentationRunnerArguments[BUILDKITE_ANALYTICS_TOKEN] =
            getEnv(BUILDKITE_ANALYTICS_TOKEN)
        testInstrumentationRunnerArguments[BUILDKITE_ANALYTICS_DEBUG_ENABLED] =
            getEnv(BUILDKITE_ANALYTICS_DEBUG_ENABLED)

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
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
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.buildkite.test.collector.android.example"
}

dependencies {
    androidTestImplementation(projects.collector.instrumentedTestCollector)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeKtx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)

    testImplementation(libs.testing.junit)

    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.compose.ui.test)
}

fun getEnv(key: String): String = System.getenv(key) ?: ""
