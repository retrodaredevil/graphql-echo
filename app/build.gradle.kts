plugins {
	// java-common-conventions applies java plugin, configs repositories, defines Java version and sets up JUnit
	id("buildlogic.java-common-conventions")

	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	// https://github.com/Netflix/dgs-codegen/releases/tag/v7.0.3
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
    // More info here: https://netflix.github.io/dgs/generating-code-from-schema/
    // The main downside of DGS is that it always generates POJOs with setters (I don't like mutable objects)

	schemaPaths.add("${projectDir}/src/main/resources/schema")
	packageName = "me.retrodaredevil.graphqlecho.codegen"
	generateClient = false // we just need the data types generated
}
