import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val koin_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val ktor_version = "3.1.2"

plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.1.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"

    id("application")
    id("com.google.protobuf") version "0.9.4"
}

group = "cz.cvut.fit.ejk.client"
version = "0.0.1"

repositories {
    mavenCentral()
}
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.3"
    }
    plugins {
        maybeCreate("grpc").apply {
            artifact = "io.grpc:protoc-gen-grpc-java:1.59.0"
        }
        maybeCreate("grpckt").apply {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.0:jdk8@jar"
        }
    }
}

dependencies {
    implementation(project(":shared"))

    // --- gRPC ---
    implementation("io.grpc:grpc-netty-shaded:1.59.0")
    implementation("com.google.protobuf:protobuf-java:3.25.0")
    implementation("io.grpc:grpc-kotlin-stub:1.4.0")

    // --- Ktor HTTP Client ---
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    // --- Kotlinx Serialization (JSON) ---
    implementation("io.ktor:ktor-serialization-kotlinx-json:${ktor_version}")

    // --- Clikt (CLI parser) ---
    implementation("com.github.ajalt.clikt:clikt:3.5.2")
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
    archiveBaseName.set("client")
    archiveClassifier.set("all")
    manifest {
        attributes(
            "Main-Class" to "cz.cvut.fit.ejk.client.MainKt"
        )
    }
}

application {
    mainClass.set("cz.cvut.fit.ejk.client.MainKt")
}