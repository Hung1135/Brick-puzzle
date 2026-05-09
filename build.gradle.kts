plugins {
    id("java")
    id("war")
    id("maven-publish")
    id("org.hidetake.ssh") version "2.11.2"
}

group = "vn.edu.hcmuaf.fit"
version = "1.0-SNAPSHOT"
description = "TTLTW_Nhom6"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {

    // Servlet API
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")

    // JSTL
    implementation("jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.1")
    implementation("org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1")

    // MySQL
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

/* =========================
   SSH CONFIG
   ========================= */

configure<org.hidetake.gradle.ssh.plugin.SshPluginExtension> {

    knownHosts = allowAnyHosts

    remotes {
        create("host") {
            host = "192.168.59.138"
            user = "hung"

            // password server ubuntu
            password = "123"
        }
    }
}

/* =========================
   DOCKER START TOMCAT
   ========================= */

tasks.register("docker_app_start") {

    doLast {

        println("begin docker_app_start")

        ssh.run {

            session(remotes["host"]) {

                execute(
                    "docker stop tomcat9",
                    mapOf("ignoreError" to true)
                )

                execute(
                    "docker rm tomcat9",
                    mapOf("ignoreError" to true)
                )

                execute(
                    """
                    docker run -it --rm -d \
                    --name tomcat9 \
                    -v /opt/deploy:/usr/local/tomcat/webapps \
                    -p 80:8080 \
                    tomcat:9.0
                    """.trimIndent()
                )
            }
        }
    }
}

/* =========================
   UPLOAD WAR
   ========================= */

tasks.register("docker_upload_file_to_server") {

    dependsOn("build")

    doLast {

        println("begin docker_upload_file_to_server")

        ssh.run {

            session(remotes["host"]) {

                execute("rm -rf /opt/deploy/ROOT")
                execute("rm -f /opt/deploy/lab.war")

                put(
                    hashMapOf(
                        "from" to file("${project.projectDir}/build/libs/TTLTW_Nhom6-1.0-SNAPSHOT.war"),
                        "into" to "/opt/deploy/lab.war"
                    )
                )
            }
        }
    }
}

/* =========================
   DEPLOY TASK
   ========================= */

tasks.register("docker_deploy") {

    dependsOn("build")
    dependsOn("docker_app_start")
    dependsOn("docker_upload_file_to_server")

    tasks.getByName("docker_app_start")
        .mustRunAfter("docker_upload_file_to_server")
}