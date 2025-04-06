plugins {
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	java
	application
}

group = "br.com.dio"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.liquibase:liquibase-core")
	implementation("org.postgresql:postgresql:42.7.3")

	implementation("org.projectlombok:lombok:1.18.34")
	annotationProcessor("org.projectlombok:lombok:1.18.34")

	implementation("org.liquibase:liquibase-core")
	implementation("org.projectlombok:lombok:1.18.34")
	implementation("org.postgresql:postgresql:42.7.3")
	annotationProcessor("org.projectlombok:lombok:1.18.34")
}

application {
	mainClass.set("br.com.dio.Main")
}

tasks.test {
	useJUnitPlatform()
}

tasks.jar {
	archiveBaseName.set("app")
	archiveVersion.set("")
	archiveClassifier.set("")
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE

	manifest {
		attributes["Main-Class"] = "br.com.dio.Main"
	}

	from({
		configurations.runtimeClasspath.get().filter { it.exists() }.map { if (it.isDirectory) it else zipTree(it) }
	})
}
