package me.retrodaredevil.graphqlecho.query.dummytimeseries;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.List;

public record DummyTimeseriesDatapoint(
		Instant timestamp,
		@NotNull List<@NotNull DummyTimeseriesProcessorTemperature> processorTemperatures
) {
}
