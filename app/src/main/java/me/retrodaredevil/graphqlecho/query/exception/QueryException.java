package me.retrodaredevil.graphqlecho.query.exception;

public class QueryException extends Exception {
	/*
	Another random thought, on a random Saturday at 8:30 PM:

	Traditionally, I (Lavender) have been a big fan of checked exceptions in Java.
	Take a look at SolarThing or couchdb-java.
	Checked exceptions everywhere!
	There have been a few times when I decide that I don't want to use checked exceptions.

	Now I'm faced with another question:
	Do I want my exceptions that will be thrown within mappings to be checked?
	If I make this checked I would have to put `throws QueryException` everywhere.
	And I'm not going to be the one calling the methods.
	All these methods that would throw this exception would have `@SchemaMapping` annotations on them.
	It feels a little pointless in this situation.

	I'm going to try this as a checked exception first, and maybe I'll change that later.
	 */

	public QueryException(String message) {
		super(message);
	}
}
