# graphql-echo
A simple GraphQL server with queries that allow the easy testing of your GraphQL clients.

Motivation: https://github.com/wildmountainfarms/wild-graphql-datasource/issues/10


## How this project was made

* https://docs.spring.io/spring-boot/reference/web/spring-graphql.html
  * You will use this: https://start.spring.io/
* Building container images: https://docs.spring.io/spring-boot/reference/packaging/container-images/index.html

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

## Building locally

Building the project is as simple as understanding what Gradle tasks you need to call.
Typically, you might want to test building the docker image.
You can build the docker image like so:

```shell
./gradlew bootBuildImage -Pplatform=linux/amd64
```

Note that you may need to run these commands for multiplatform building:
```shell
docker run --rm --privileged multiarch/qemu-user-static --reset -p yes
docker buildx create --name multiarch-builder --use --bootstrap --driver docker-container

# Confirm available platforms with:
docker buildx inspect --bootstrap
```

## Running Locally

* Run `GraphQLEchoApplication` in IntelliJ, or run the `:app:bootRun` gradle task in your terminal
* Navigate to http://localhost:8080/graphiql?path=/graphql to test the endpoint
