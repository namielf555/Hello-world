plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "dev.lelonio.square"
  compileSdk = 37

  defaultConfig {
    applicationId = "dev.lelonio.square"
    minSdk = 26
    targetSdk = 36
    versionCode = 34
    versionName = "2.2.8"

    buildConfigField("boolean", "VERBOSE_LOG", "true")

    ndk {
      abiFilters += listOf("arm64-v8a")
    }
  }

  signingConfigs {
    create("debugConfig") {
      storeFile = file("${rootDir}/debug.keystore")
      storePassword = "android"
      keyAlias = "androiddebugkey"
      keyPassword = "android"
    }
  }

  buildTypes {
    release {
      isCrunchPngs = false
      isMinifyEnabled = false
      signingConfig = signingConfigs.getByName("debugConfig")
    }
    debug {
      signingConfig = signingConfigs.getByName("debugConfig")
    }
    create("dev") {
      initWith(getByName("debug"))
    }
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlin {
    compilerOptions {
      jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
  }

  buildFeatures {
    compose = true
    buildConfig = true
  }

  packaging {
    jniLibs.keepDebugSymbols += "**/libsquarecore.so"
  }

  sourceSets["main"].jniLibs.directories.add("src/main/jniLibs")
}

dependencies {
  implementation(libs.timber)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.profileinstaller)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.browser)
  implementation(libs.androidx.security.crypto)
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.material.icons.extended)
  implementation(libs.androidx.compose.ui.tooling.preview)
  debugImplementation(libs.androidx.compose.ui.tooling)
  implementation(libs.media3.common)
  implementation(libs.media3.session)
  implementation(libs.media3.exoplayer)
  implementation(libs.media3.exoplayer.dash)
  implementation(libs.media3.exoplayer.hls)
  implementation(libs.media3.ui)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.guava)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.okhttp)
  implementation(libs.logging.interceptor)
  implementation(libs.retrofit)
  implementation(libs.retrofit.serialization)
  implementation(libs.newpipe.extractor)
  implementation(project(":innertube"))
  implementation(libs.coil.compose)
  implementation(libs.androidx.palette)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.backdrop)
  implementation(libs.kyant.shapes)
  implementation(libs.phosphor)
  coreLibraryDesugaring(libs.desugaring)
}
