import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
//     https://mvnrepository.com/artifact/io.arrow-kt/arrow-core
    implementation("io.arrow-kt:arrow-core:1.1.2")
//    implementation("io.arrow-kt:arrow-core-data:0.12.1")
//    implementation("io.arrow-kt:arrow-fx:0.12.1")
//    implementation("io.arrow-kt:arrow-mtl:0.11.0")
//    implementation("io.arrow-kt:arrow-mtl-data:0.11.0")
//    implementation("io.arrow-kt:arrow-syntax:0.12.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.3.2") // https://mvnrepository.com/artifact/io.kotest/kotest-assertions-core-jvm
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
