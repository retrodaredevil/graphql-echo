package me.retrodaredevil.graphqlecho.query;

import graphql.schema.DataFetchingEnvironment;
import me.retrodaredevil.graphqlecho.GraphQLRequestHeaderInterceptor;
import me.retrodaredevil.graphqlecho.query.echohttpheaders.HeaderResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;

/**
 * The controller containing the query mappings for the root query
 *
 * @see <a href="https://docs.spring.io/spring-graphql/reference/controllers.html">docs.spring.io/spring-graphql/reference/controllers.html</a>
 */
@Controller
public class RootQueryController {

	@QueryMapping
	public @NotNull HeaderResponse echoHttpHeaders(DataFetchingEnvironment env) {
		HttpHeaders headers = env.getGraphQlContext().get(GraphQLRequestHeaderInterceptor.CONTEXT_KEY_HTTP_HEADERS);
		return new HeaderResponse(headers);
	}
}

