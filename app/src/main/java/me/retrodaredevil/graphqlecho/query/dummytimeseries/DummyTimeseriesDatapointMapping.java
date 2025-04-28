package me.retrodaredevil.graphqlecho.query.dummytimeseries;

import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@SchemaMapping(typeName = "DummyTimeseriesDatapoint")
public class DummyTimeseriesDatapointMapping {
	@SchemaMapping
	public long dateMillis(DummyTimeseriesDatapoint datapoint) {
		return datapoint.timestamp().toEpochMilli();
	}
	@SchemaMapping
	public long dateEpochSecond(DummyTimeseriesDatapoint datapoint) {
		return datapoint.timestamp().getEpochSecond();
	}
	@SchemaMapping
	public @NotNull List<@NotNull Double> processorTemperaturesCelsius(DummyTimeseriesDatapoint datapoint) {
		return datapoint.processorTemperatures().stream()
				.mapToDouble(DummyTimeseriesProcessorTemperature::temperatureCelsius)
				.boxed()
				.toList();
	}
}
