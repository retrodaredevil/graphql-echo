package me.retrodaredevil.graphqlecho.query.echohttpheaders;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record HeaderEntry(
		@NotNull String name,
		@NotNull List<@NotNull String> values
) {
}
