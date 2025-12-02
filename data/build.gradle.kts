plugins {
    kotlin("jvm")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib"))

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)     // JVM 기반 HTTP 엔진
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization.kotlinx.json)

    // 테스트
    testImplementation(kotlin("test"))
}