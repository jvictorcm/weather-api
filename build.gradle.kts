extra["springCloudVersion"] = "2021.0.3"
plugins {
    java
    id("org.springframework.boot") version "2.7.8"
    id("io.spring.dependency-management") version "1.1.0"
    jacoco
    application
}

group = "br.com.victor"
version = "0.0.1-SNAPSHOT"
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    //Spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    //db
    implementation("org.liquibase:liquibase-core:4.11.0")
    implementation("org.postgresql:postgresql:42.5.1")
    //docs
    implementation("org.springdoc:springdoc-openapi-ui:1.6.14")
    //logging
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("ch.qos.logback:logback-core:1.2.11")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    //test
    testCompileOnly("junit:junit:4.13")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.rest-assured:spring-mock-mvc:5.1.1")
    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")
    testImplementation("org.testcontainers:postgresql:1.17.6")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
    finalizedBy("jacocoTestReport")
    doLast {
        println("View code coverage at:")
        println("file://$buildDir/reports/jacoco/test/html/index.html")
    }
}
tasks.jacocoTestReport {
    dependsOn(tasks.test)
    classDirectories.setFrom(
            files(classDirectories.files.map {
                fileTree(it) {
                    exclude("*/config/*", "*/model/*", "/exceptions/*")
                }
            })
    )
}

jacoco {
    toolVersion = "0.8.8"
}

sourceSets.create("integrationTest") {
    java.srcDir("src/integrationTest/java")
    resources.srcDir("src/integrationTest/resources")

    compileClasspath += sourceSets.main.get().output + sourceSets.test.get().output
    runtimeClasspath += sourceSets.main.get().output + sourceSets.test.get().output
}

configurations["integrationTestCompile"].extendsFrom(configurations.testImplementation.get())
configurations["integrationTestRuntime"].extendsFrom(configurations.implementation.get())
configurations["integrationTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

tasks.create<Test>("integrationTest") {
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath + sourceSets["integrationTest"].compileClasspath
}

tasks["test"].finalizedBy("integrationTest")