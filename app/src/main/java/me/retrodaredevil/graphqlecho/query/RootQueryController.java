package me.retrodaredevil.graphqlecho.query;

import graphql.schema.DataFetchingEnvironment;
import me.retrodaredevil.graphqlecho.GraphQLRequestHeaderInterceptor;
import me.retrodaredevil.graphqlecho.query.dummytimeseries.DummyTimeseriesDatapoint;
import me.retrodaredevil.graphqlecho.query.dummytimeseries.DummyTimeseriesNodeData;
import me.retrodaredevil.graphqlecho.query.dummytimeseries.DummyTimeseriesProcessorTemperature;
import me.retrodaredevil.graphqlecho.query.dummytimeseries.DummyTimeseriesResponse;
import me.retrodaredevil.graphqlecho.query.echohttpheaders.HeaderResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The controller containing the query mappings for the root query
 *
 * @see <a href="https://docs.spring.io/spring-graphql/reference/controllers.html">docs.spring.io/spring-graphql/reference/controllers.html</a>
 */
@Controller
public class RootQueryController {
	private static final Duration DEFAULT_DUMMY_TIMESERIES_GENERATION_PERIOD = Duration.ofSeconds(30);
	private static final double MINIMUM_TEMPERATURE = 10.0;
	private static final double MAXIMUM_TEMPERATURE = 90.0;
	private static final double TEMPERATURE_WIGGLE = 2.0;

	@QueryMapping
	public @NotNull HeaderResponse echoHttpHeaders(DataFetchingEnvironment env) {
		HttpHeaders headers = env.getGraphQlContext().get(GraphQLRequestHeaderInterceptor.CONTEXT_KEY_HTTP_HEADERS);
		return new HeaderResponse(headers);
	}

	@QueryMapping
	public @NotNull DummyTimeseriesResponse generateDummyTimeseries(
			@Argument long from, @Argument long to,
			@Argument("nodeCount") @Nullable Integer nodeCountArgument,
			@Argument("datapointCount") @Nullable Integer datapointCountArgument,
			@Argument("processorCount") @Nullable Integer processorCountArgument
	) {
		Instant fromInstant = Instant.ofEpochMilli(from);
		Instant toInstant = Instant.ofEpochMilli(to);
		if (toInstant.isBefore(fromInstant)) {
			throw new IllegalArgumentException("to must not be before from!");
		}
		Duration length = Duration.between(fromInstant, toInstant);
		final int nodeCount;
		final int processorCount;

		if (nodeCountArgument == null) {
			nodeCount = 2;
		} else if (nodeCountArgument < 0) {
			throw new IllegalArgumentException("nodeCount cannot be negative!");
		} else {
			nodeCount = nodeCountArgument;
		}
		if (datapointCountArgument != null && datapointCountArgument < 0) {
			throw new IllegalArgumentException("datapointCount cannot be negative!");
		}
		if (processorCountArgument == null) {
			processorCount = 2;
		} else if (processorCountArgument <= 0) {
			throw new IllegalArgumentException("processorCount must be positive!");
		} else {
			processorCount = processorCountArgument;
		}

		List<@NotNull DummyTimeseriesNodeData> nodeData = IntStream.range(0, nodeCount)
				.mapToObj(nodeIndex -> {
					final int datapointCount;
					final Duration offsetFromStart;
					final Duration durationBetweenDatapoints;

					if (datapointCountArgument == null) {
						long result = length.dividedBy(DEFAULT_DUMMY_TIMESERIES_GENERATION_PERIOD);
						if (result == 0) {
							datapointCount = 1;
							durationBetweenDatapoints = Duration.ZERO;
							offsetFromStart = Duration.ofMillis((long) (length.toMillis() * Math.random()));
						} else if (result > Integer.MAX_VALUE) {
							throw new IllegalArgumentException("Please manually specify datapointCount when the range between to and from is this large");
						} else {
							datapointCount = (int) result;
							durationBetweenDatapoints = DEFAULT_DUMMY_TIMESERIES_GENERATION_PERIOD;
							Duration wiggleRoom = length.minus(durationBetweenDatapoints.multipliedBy(datapointCount));
							offsetFromStart = Duration.ofMillis((long) (wiggleRoom.toMillis() * Math.random()));
						}
					} else {
						datapointCount = datapointCountArgument;
						offsetFromStart = Duration.ZERO;
						durationBetweenDatapoints = length.dividedBy(datapointCount);
					}
					List<DummyTimeseriesDatapoint> datapoints = IntStream.range(0, datapointCount)
							.mapToObj(datapointIndex -> {
								Instant timestamp = fromInstant
										.plus(offsetFromStart)
										.plus(durationBetweenDatapoints.multipliedBy(datapointIndex));
								double baseTemperature = MINIMUM_TEMPERATURE + (MAXIMUM_TEMPERATURE - TEMPERATURE_WIGGLE - MINIMUM_TEMPERATURE) * Math.random();
								List<DummyTimeseriesProcessorTemperature> processorTemperatures = IntStream.range(0, processorCount)
										.mapToObj(processorIndex -> {
											double temperature = baseTemperature + TEMPERATURE_WIGGLE * Math.random();
											return new DummyTimeseriesProcessorTemperature(processorIndex, temperature);
										})
										.toList();
								return new DummyTimeseriesDatapoint(
										timestamp,
										processorTemperatures
								);
							})
							.toList();

					return new DummyTimeseriesNodeData(
							"node " + (nodeIndex + 1),
							datapoints
					);
				})
				.toList();

		return new DummyTimeseriesResponse(
				"Central Server 1",
				nodeData
		);
//				List.of(
//						new DummyTimeseriesNodeData(
//								"node 1",
//								List.of(
//										new DummyTimeseriesDatapoint(
//												1234,
//												List.of(
//														new DummyTimeseriesProcessorTemperature(0, 31.0),
//														new DummyTimeseriesProcessorTemperature(1, 32.2)
//												)
//										)
//								)
//						),
//						new DummyTimeseriesNodeData(
//								"node 2",
//								List.of(
//										new DummyTimeseriesDatapoint(
//												1234,
//												List.of(
//														new DummyTimeseriesProcessorTemperature(0, 36.0),
//														new DummyTimeseriesProcessorTemperature(1, 35.2)
//												)
//										)
//								)
//						)
//				)
//		);
	}
}

