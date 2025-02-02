package me.retrodaredevil.graphqlecho.query.echohttpheaders;

import me.retrodaredevil.graphqlecho.query.exception.ExpectQueryException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@SchemaMapping(typeName = "HeaderResponse")
public class HeaderResponseMapping {

	@SchemaMapping
	@NotNull List<@NotNull HeaderEntry> entries(HeaderResponse element) {
		return element.entryMap().entrySet().stream().map(e -> new HeaderEntry(e.getKey(), e.getValue())).collect(Collectors.toList());
	}

	@SchemaMapping
	public @Nullable List<@NotNull String> getHeaderMulti(HeaderResponse element, @Argument @NotNull String name) {
		return element.entryMap().get(name);
	}

	@SchemaMapping
	public @Nullable String getHeader(HeaderResponse element, @Argument String name) throws ExpectQueryException {
		var headerMulti = getHeaderMulti(element, name);
		if (headerMulti == null) {
			return null;
		}
		if (headerMulti.size() > 1) {
			throw new ExpectQueryException("Multiple headers (\" + headerMulti.size() + \") found for " + name + ": " + headerMulti);
		}
		return headerMulti.getFirst();
	}

	@SchemaMapping
	public @NotNull List<@NotNull String> expectHeaderMulti(HeaderResponse element, @Argument @NotNull String name) throws ExpectQueryException {
		var headerMulti = getHeaderMulti(element, name);
		if (headerMulti == null) {
			throw new ExpectQueryException("Expected header '" + name + "' to be present.");
		}
		return headerMulti;
	}

	@SchemaMapping
	public @NotNull String expectHeader(HeaderResponse element, @Argument String name) throws ExpectQueryException {
		var header = getHeader(element, name);
		if (header == null) {
			throw new ExpectQueryException("Expected header '" + name + "' to be present.");
		}
		return header;
	}
}
