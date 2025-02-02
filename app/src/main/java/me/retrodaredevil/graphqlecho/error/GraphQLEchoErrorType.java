package me.retrodaredevil.graphqlecho.error;

import graphql.ErrorClassification;
import org.springframework.graphql.execution.ErrorType;

/**
 * Contains custom error types used by graphql-echo and may contain getters for
 * other commonly used error types that are not necessarily a {@link GraphQLEchoErrorType}.
 *
 * @see <a href="https://graphql.org/learn/response/#errors">https://graphql.org/learn/response/#errors</a>
 * @see <a href="https://spec.graphql.org/draft/#sec-Errors">https://spec.graphql.org/draft/#sec-Errors</a>
 */
public enum GraphQLEchoErrorType implements ErrorClassification {
	EXPECTATION_NOT_MET,
	;

	public static ErrorClassification getInternalError() {
		return ErrorType.INTERNAL_ERROR;
	}
}
