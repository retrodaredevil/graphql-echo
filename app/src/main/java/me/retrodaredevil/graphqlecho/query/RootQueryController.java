package me.retrodaredevil.graphqlecho.query;

import graphql.schema.DataFetchingEnvironment;
import jakarta.servlet.http.HttpServletRequest;
import me.retrodaredevil.graphqlecho.query.echohttpheaders.HeaderResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * The controller containing the query mappings for the root query
 *
 * @see <a href="https://docs.spring.io/spring-graphql/reference/controllers.html">docs.spring.io/spring-graphql/reference/controllers.html</a>
 */
@Controller
public class RootQueryController {

//	public @NotNull HeaderResponse echoHttpHeaders(@RequestHeader MultiValueMap<String, String> headers) {
	@QueryMapping
	public @NotNull HeaderResponse echoHttpHeaders(DataFetchingEnvironment env) {
		HttpServletRequest request = env.getGraphQlContext().get("httpRequest");
		var thing = env.getGraphQlContext();


		// ChatGPT o3-mini came up with most of this.
		//   To get into stream land, it wanted to use Collections.list(), but that's not the most efficient way to do this.
		//   The cool thing is that ChatGPT o3-mini actually had an explanation that you should use Spliterators.spliteratorUnknownSize()
		//   https://stackoverflow.com/a/33243700/5434860 got me all the way there. Thanks!
		var headers = StreamSupport.stream(Spliterators.spliteratorUnknownSize(request.getHeaderNames().asIterator(), Spliterator.ORDERED), false)
				.collect(Collectors.toMap(
						headerName -> headerName,
						headerName -> Collections.list(request.getHeaders(headerName))
				));

		// Note that the fact that we do any sort of the conversion means we might be doing extra computation when we don't need to.
		//   Maybe we decide to change that in the future (and maybe decide whether to keep HeaderResponse as a record).
		//   In this case I don't think we need to do any premature optimization.
		//   But just know, that we could :)

		// Now you can access headers as usual
		return new HeaderResponse(headers);
	}
}

