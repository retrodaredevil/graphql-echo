package me.retrodaredevil.graphqlecho.query.dummytimeseries;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record DummyTimeseriesResponse(
		@NotNull String serverName,
		@NotNull List<@NotNull DummyTimeseriesNodeData> nodeData
) {
}
