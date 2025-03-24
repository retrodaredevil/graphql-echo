package me.retrodaredevil.graphqlecho;

import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLEchoConfig {

	@Bean
	public RuntimeWiringConfigurer runtimeWiringConfigurer() {
		// https://github.com/graphql-java/graphql-java-extended-scalars?tab=readme-ov-file#spring-for-graphql
		return wiringBuilder -> wiringBuilder
				.scalar(ExtendedScalars.GraphQLLong)
				;
	}
}
