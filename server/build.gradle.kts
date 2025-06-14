val koin_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version = "0.50.1"
val ktor_version = "3.1.2"

plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.1.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"

    id("application")
    id("com.google.protobuf") version "0.9.4"
}

group = "cz.cvut.fit.ejk.server"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

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
    implementation("io.ktor:ktor-server-resources:${ktor_version}")
    implementation("io.grpc:grpc-kotlin-stub:1.4.0")
    implementation("io.grpc:grpc-protobuf:1.59.0")
    implementation("io.grpc:grpc-netty-shaded:1.59.0")
    implementation("com.google.protobuf:protobuf-java:3.25.0")
    implementation("io.minio:minio:8.5.7")

    implementation("org.jetbrains.exposed:exposed-core:${exposed_version}")
    implementation("org.jetbrains.exposed:exposed-dao:${exposed_version}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${exposed_version}")
    implementation("org.postgresql:postgresql:42.7.1")

    implementation("io.ktor:ktor-server-call-logging:${ktor_version}")
    implementation("io.ktor:ktor-server-core:${ktor_version}")
    implementation("io.ktor:ktor-server-content-negotiation:${ktor_version}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${ktor_version}")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
    implementation("io.ktor:ktor-server-netty:${ktor_version}")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml:${ktor_version}")
    implementation("io.ktor:ktor-server-openapi:3.1.2")
    testImplementation("io.ktor:ktor-server-test-host:${ktor_version}")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

sourceSets {
    main {
        java {
            srcDirs("build/generated/source/proto/main/java",
                "build/generated/source/proto/main/grpc",
                "build/generated/source/proto/main/grpckt")
        }
    }
    test {
        java.srcDirs("src/test/kotlin/cz/cvut/fit/ejk/server")
    }
    }
