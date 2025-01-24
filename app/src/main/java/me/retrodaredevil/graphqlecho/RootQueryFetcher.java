package me.retrodaredevil.graphqlecho;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import me.retrodaredevil.graphqlecho.codegen.types.HeaderEntry;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@DgsComponent
public class RootQueryFetcher {

	@DgsQuery
	public List<HeaderEntry> echoHttpHeaders(@RequestHeader MultiValueMap<String, String> headers) {
		return headers.entrySet().stream()
				.map(e -> new HeaderEntry(e.getKey(), e.getValue()))
				.toList();
	}
}

