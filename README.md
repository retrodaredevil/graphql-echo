# graphql-echo
A simple GraphQL server with queries that allow the easy testing of your GraphQL clients.


## Running yourself

* Run `GraphQLEchoApplication` in IntelliJ, or run the `:app:bootRun` gradle task in your terminal
* Navigate to http://localhost:8080/graphiql?path=/graphql to test the endpoint

## How this project was made

* https://netflix.github.io/dgs/
    * You will use this: https://start.spring.io/
* https://netflix.github.io/dgs/generating-code-from-schema/
  * You will reference this guide for generating code and understanding how it works.
  * Keep in mind that this guide is designed for client libraries, but we are making a server. Not everything here applies to it.

## IDE Setup

### IntelliJ IDEA

Install plugins:
* https://plugins.jetbrains.com/plugin/17852-dgs
* https://plugins.jetbrains.com/plugin/22807-spring-graphql
