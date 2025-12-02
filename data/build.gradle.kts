plugins {
    kotlin("jvm")
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib"))

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

    // 테스트
    testImplementation(kotlin("test"))
}
