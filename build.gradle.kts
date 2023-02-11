plugins {
	java
	id("org.springframework.boot") version "3.0.1"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "com.vanart"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
	maven {
		url = uri("https://repo.spring.io/snapshot")
	}
	maven {
		url = uri("https://repo.spring.io/milestone")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.statemachine:spring-statemachine-starter:3.2.0")
	implementation("org.postgresql:postgresql:42.5.1")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")


	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("com.github.pengrad:java-telegram-bot-api:6.3.0")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.14.1")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
