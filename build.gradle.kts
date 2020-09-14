import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
}


repositories {
    mavenCentral()
    jcenter()

}

java.sourceCompatibility = JavaVersion.VERSION_13

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.arrow-kt:arrow-core:0.10.5")

    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.hibernate:hibernate-validator:6.1.5.Final")

    implementation("io.github.microutils:kotlin-logging:1.8.3")
    implementation("org.slf4j:slf4j-api:1.6.6")
    implementation("ch.qos.logback:logback-core:1.0.9")
    implementation("ch.qos.logback:logback-classic:1.0.9")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "13"
    }
}
