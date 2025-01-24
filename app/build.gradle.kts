plugins {
	// java-common-conventions applies java plugin, configs repositories, defines Java version and sets up JUnit
	id("buildlogic.java-common-conventions")

	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.netflix.dgs.codegen") version "7.0.3"
}

group = "me.retrodaredevil"
version = "0.0.1-SNAPSHOT"

extra["netflixDgsVersion"] = "10.0.1"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:${property("netflixDgsVersion")}")
	}
}

tasks.generateJava {
	schemaPaths.add("${projectDir}/src/main/resources/graphql-client")
	packageName = "me.retrodaredevil.graphqlecho.codegen"
	generateClient = true
}
