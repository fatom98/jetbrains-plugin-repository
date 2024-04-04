plugins {
    `maven-publish`
    kotlin("jvm")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api(libs.com.fasterxml.jackson.module.jackson.module.kotlin)
    api(libs.com.fasterxml.jackson.dataformat.jackson.dataformat.yaml)

    implementation("org.apache.logging.log4j:log4j-core:2.22.1")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.4.0")
}

group = "tr.com"
version = "1.0"
description = "Jetbrains Plugin Repository"