package me.retrodaredevil.graphqlecho.query.exception;

import me.retrodaredevil.graphqlecho.error.GraphQLEchoErrorType;

public class ExpectQueryException extends QueryException {
	public ExpectQueryException(String message) {
		super(message, GraphQLEchoErrorType.EXPECTATION_NOT_MET);
	}
}
