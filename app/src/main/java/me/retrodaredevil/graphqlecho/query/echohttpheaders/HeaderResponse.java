package me.retrodaredevil.graphqlecho.query.echohttpheaders;

import java.util.List;
import java.util.Map;

/**
 * Represents data necessary to respond to an echoHttpHeaders query.
 * Logic for how that data is returned is present in {@link HeaderResponseMapping}
 *
 * @param entryMap a map representing the headers. This implementation of map has <strong>case-insensitive</strong> keys. Please keep that in mind when using it!
 */
public record HeaderResponse(
		Map<String, ? extends List<String>> entryMap
) {
	/*
	My (Lavender's) random thoughts on records in Java on some random Saturday at 8PM:

	HeaderResponse was going to be a record with a single List<HeaderEntry> entries field in it.
	I quickly changed it to a class after I realized that I shouldn't prioritize my public fields as "the way the data should be represented".
	The fact that if I want to change the underlying implementation I have to use a method not prefixed with it, it stands out.
	I could easily make a record behave like the class I want it to be, but that feels wrong.
	It feels that records are for DATA. Raw data.
	Now, that's good sometimes, but the main problem I see with that is that I really like the idea
	of encapsulation such that a class's internal representation of the data is not public and can be altered with ease.

	I believe that when you start thinking about encapsulating the internal way of representing the data, you probably shouldn't be using a record.

	The cool thing is that when you need to stop using a record and change to a class, you're forced to update the names of the "getter" methods
	by prefixing them all with "get~". Well, you should at least feel forced to.
	The reason is that when someone is using a record somewhere in their code, they actually will likely make assumptions about
	the internal representation of the data. So code that needs updating is actually code that needs reviewing too.
	You might now understand how to update the code using these fields so that they use a more efficient getter
	for the specific task they are trying to accomplish.

	If you agree with the above statements and think about all that, you get a few pros and cons out of them.
	Pros:
	* You have a chance to review code that may need to be updated.

	Cons:
	* When making a change that should feel as simple as changing a record to a class, you may have to spend the time to update many places in the code.
	  * This itself may discourage people from changing a record to a class when it would be beneficial to. If that
	    ends up being the case, then introducing records into your code at all could be bad if it reduces overall quality
	    by having records being used when classes should be used.

	Thanks for reading to my thoughts on records in Java.
	I haven't been using them long (it's 2025.02.01 as I write this).

	*40 seconds later*

	Ok wait, so I know this is still a record, but I think that's OK!
	Now I have most of the logic inside of HeaderResponseMapping.
	It gets to deal with the internal representation of this, and that's OK!

	Ok, wait again.
	So I totally could change it to a class and have a getter for how it need to be for the GraphQL mapped data.
	It just didn't seem like there was a way to use schema mappings directly inside this class, which is why a bunch of the logic is in HeaderResponseMapping.
	I mean, the idea is that the data lives in this class...

	Alright, I don't know.
	I'm so indecisive on the record thing.
	This is a record.
	Maybe that will change in the future.
	I love modern Java versions.
	I spend so much time deciding which new features to use and not use.
	It's my favorite! /s

	Ok, but I do like records.
	But maybe I overthought this one a bit.

	Oh, and one thing we have to make sure is that we don't call any public fields in this class
	the same thing as a field in schema.graphqls.
	So that's another downside of this being a record.
	I feel like we're exposing some of what I think is internal data representation to Spring and its GraphQL stuff.

	Ok! That's it that's all my thoughts on records for tonight!
	 */
}
