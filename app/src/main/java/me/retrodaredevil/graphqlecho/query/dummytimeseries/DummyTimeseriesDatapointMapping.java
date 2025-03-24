package me.retrodaredevil.graphqlecho.query.dummytimeseries;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

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
}
