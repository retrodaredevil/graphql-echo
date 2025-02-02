package me.retrodaredevil.graphqlecho;

import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Thanks <a href="https://medium.com/miragon/how-to-access-http-headers-in-spring-for-graphql-be166bd33312">How to access HTTP-headers in Spring-For-GraphQL</a>!
 * That article has some good discussion on why a class like this is even necessary.
 * The article also shows how you could use {@code @ContextValue} to access something applied to the GraphQL Context
 */
@Component
public class GraphQLRequestHeaderInterceptor implements WebGraphQlInterceptor {
	public static String CONTEXT_KEY_HEADER_MAP = GraphQLRequestHeaderInterceptor.class.getCanonicalName() + ".headerMap";

	@Override
	public @NotNull Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
		HttpHeaders headers = request.getHeaders();
		request.configureExecutionInput((executionInput, builder) -> builder.graphQLContext(Map.of(
				CONTEXT_KEY_HEADER_MAP, headers
		)).build());
        return chain.next(request);
	}
}
