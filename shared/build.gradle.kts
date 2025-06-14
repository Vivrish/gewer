val koin_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version = "0.50.1"
val ktor_version = "3.1.2"

plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.1.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
    id("com.google.protobuf") version "0.9.4"
}

repositories {
    mavenCentral()
}

group = "cz.cvut.fit.ejk.shared"
version = "0.0.1"



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
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("grpc")
                create("grpckt")
            }
        }
    }
}

dependencies {
    implementation("io.grpc:grpc-kotlin-stub:1.4.0")
    implementation("io.grpc:grpc-protobuf:1.59.0")
    implementation("io.grpc:grpc-netty-shaded:1.59.0")
    implementation("com.google.protobuf:protobuf-java:3.25.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${ktor_version}")
}
