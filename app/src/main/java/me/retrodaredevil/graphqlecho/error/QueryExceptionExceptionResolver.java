package me.retrodaredevil.graphqlecho.error;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import me.retrodaredevil.graphqlecho.query.exception.QueryException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

@Component
public class QueryExceptionExceptionResolver extends DataFetcherExceptionResolverAdapter {
	@Override
	protected @Nullable GraphQLError resolveToSingleError(@NotNull Throwable ex, @NotNull DataFetchingEnvironment env) {
		if (ex instanceof QueryException queryException) {
			return GraphQLError.newError()
					.message(queryException.getMessage())
					.errorType(queryException.getErrorClassification())
					.path(env.getExecutionStepInfo().getPath())
					.location(env.getField().getSourceLocation())
					.build();
		}
		return null;
	}
}
