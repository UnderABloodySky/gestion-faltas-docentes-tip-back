plugins {
	java
	id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.3"
	jacoco
	id("org.sonarqube") version "4.3.0.3225"
	kotlin("jvm") version "1.9.0"
}

jacoco {
	toolVersion = "0.8.9"
//	reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

sonar {
	properties {
		property("sonar.projectKey", "UnderABloodySky_gestion-faltas-docentes-tip-back")
		property("sonar.organization", "underabloodysky")
		property("sonar.host.url", "https://sonarcloud.io")
	}
}

group = "ar.edu.unq.tpi"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.jacocoTestReport {
	reports {
		xml.required.set(true)
		csv.required.set(false)
		html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
	}
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
	dependsOn(tasks.test) // tests are required to run before generating the report
}

