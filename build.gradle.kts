plugins {
	java
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	val containerVersion = "2.1.4"
	val mapstructVersion = "1.6.3"
	val awsSdkVersion = "2.33.2"

	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.amazonaws.serverless:aws-serverless-java-container-springboot3:$containerVersion")
	implementation("software.amazon.awssdk:dynamodb:$awsSdkVersion")
    implementation("software.amazon.awssdk:auth:$awsSdkVersion")
    implementation("software.amazon.awssdk:regions:$awsSdkVersion")
	implementation("software.amazon.awssdk:dynamodb-enhanced:$awsSdkVersion")
	implementation("software.amazon.awssdk:apache-client:$awsSdkVersion")
	implementation("org.mapstruct:mapstruct:$mapstructVersion")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
}

tasks.register<Zip>("buildZip") {
    from(tasks.compileJava)
    from(tasks.processResources)

    into("lib") {
        from(configurations.compileClasspath.get()) {
            exclude("tomcat-embed-*")
        }
    }
}

tasks.named("build") {
    dependsOn("buildZip")
}
