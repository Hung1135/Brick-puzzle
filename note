plugins {
    id("java")
    id("war")
    id("maven-publish")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // Servlet API (provided)
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")

    // JSTL
    implementation("jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.1")
    implementation("org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1")

    // MySQL JDBC driver
    implementation("mysql:mysql-connector-java:8.0.33")

    // Logging
    implementation("org.slf4j:slf4j-simple:2.0.9")

    // BCrypt
    implementation("org.mindrot:jbcrypt:0.4")

    // JDBI
    implementation("org.jdbi:jdbi3-core:3.41.3")
    implementation("org.jdbi:jdbi3-sqlobject:3.43.0")

    // JSON
    implementation("org.json:json:20240303")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.cloudinary:cloudinary-http44:1.36.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")

    // Mail
    implementation("com.sun.mail:jakarta.mail:2.0.2")

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.0")
}

group = "vn.edu.hcmuaf.fit"
version = "1.0-SNAPSHOT"
description = "TTLTW_Nhom6"


java {
    sourceCompatibility = JavaVersion.VERSION_20
    targetCompatibility = JavaVersion.VERSION_20
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}
