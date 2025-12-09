plugins {
    kotlin("android")
    alias(libs.plugins.android.library)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.yujin.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib"))

    // Core 모듈
    implementation(project(":core:common"))

    // Domain 모듈
    implementation(project(":domain"))

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio) // 기본 클라이언트 엔진
    implementation(libs.ktor.client.android) // Android 전용 엔진
    implementation(libs.ktor.client.logging) // 로깅
    implementation(libs.ktor.client.content.negotiation) // JSON 파싱
    implementation(libs.ktor.serialization.kotlinx.json) // kotlinx.serialization 사용

    //Hilt
    implementation(libs.hilt.core)
    ksp(libs.hilt.android.compiler)

    //paging
    implementation(libs.androidx.paging.runtime)

    // 테스트
    testImplementation(kotlin("test"))
}
