package me.retrodaredevil.graphqlecho.query.dummytimeseries;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record DummyTimeseriesNodeData(
		@NotNull String nodeName,
		@NotNull List<@NotNull DummyTimeseriesDatapoint> datapoints
) {
}
