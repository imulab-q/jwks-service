plugins {
    java
    id("io.spring.dependency-management").version("1.0.7.RELEASE")
    id("org.springframework.boot").version("2.2.0.M2")
    id("jacoco")
}

group = "io.imulab.q"
version = "0.0.1"

repositories {
    jcenter()
    mavenLocal()
    mavenCentral()
    maven("https://repo.spring.io/milestone")
    maven("https://repo.spring.io/snapshot")
}

jacoco {
    toolVersion = "0.8.2"
}

springBoot {
    buildInfo {
        properties {
            artifact = "jwks-service"
            group = "io.imulab.q"
            name = "jwks-service"
            version = project.version.toString()
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.bitbucket.b_c:jose4j:0.6.5")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    test {
        useJUnit()
        testLogging {
            events("PASSED", "FAILED", "SKIPPED")
        }
    }
    jacocoTestReport {
        reports {
            html.apply {
                isEnabled = true
            }
            xml.apply {
                isEnabled = true
            }
        }
    }
    bootRun {
        if (project.hasProperty("args")) {
            args(project.findProperty("args")!!.toString().split(","))
        }
    }
}

configurations.all {
    exclude(module = "jakarta.validation-api")
    exclude(module = "hibernate-validator")
}