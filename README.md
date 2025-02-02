# graphql-echo
A simple GraphQL server with queries that allow the easy testing of your GraphQL clients.

Motivation: https://github.com/wildmountainfarms/wild-graphql-datasource/issues/10

## Running yourself

* Run `GraphQLEchoApplication` in IntelliJ, or run the `:app:bootRun` gradle task in your terminal
* Navigate to http://localhost:8080/graphiql?path=/graphql to test the endpoint

## How this project was made

* https://docs.spring.io/spring-boot/reference/web/spring-graphql.html
  * You will use this: https://start.spring.io/

## Design Decisions

* Language: I knew I wanted to build this with Java or Kotlin, as they are the languages I most enjoy working with. I chose Java because I wanted to experiment with all the feature so of modern Java versions.
* Framework: Spring is the obvious choice, but what to put on top of it?
  * [DGS (by Netflix)](https://netflix.github.io/dgs/) - the first working version of GraphQL Echo uses this. I chose it because it had [codegen support](https://netflix.github.io/dgs/generating-code-from-schema/)
    * I chose not to use this because its codegen support was not as good as I had hoped, and DGS feels kind of deprecated in favor of Spring for GraphQL.
    * If I needed a client library, I probably would have chosen to use this and its codegen support
  * [Spring for GraphQL](https://spring.io/projects/spring-graphql) - great integration with Spring and well supported

## IDE Setup

### IntelliJ IDEA

Install plugins:
* https://plugins.jetbrains.com/plugin/17852-dgs
* https://plugins.jetbrains.com/plugin/22807-spring-graphql
