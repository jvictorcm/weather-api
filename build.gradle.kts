plugins {
    java
    id("org.springframework.boot") version "2.7.8"
    id("io.spring.dependency-management") version "1.1.0"
    jacoco
}

group = "br.com.victor"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

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

    //test
    testCompileOnly("junit:junit:4.13")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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