plugins {
    kotlin("jvm") version "2.2.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.haifengl:smile-core:3.0.2")
    implementation("com.github.haifengl:smile-io:3.0.2")
    implementation("com.github.haifengl:smile-kotlin:3.0.2")
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}