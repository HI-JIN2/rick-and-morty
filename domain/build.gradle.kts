plugins {
    kotlin("jvm")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    // Hilt
    implementation(libs.hilt.core)
    implementation(libs.androidx.paging.common)
    
    testImplementation(kotlin("test"))
}