# KtAlex

KtAlex is an open-source Kotlin library for [OpenAlex](https://openalex.org/), a fully open catalog of the global research system. KtAlex uses the OpenAlex API to let users discover scholarly papers, authors, institutions, publishers, funders and the relations among all these entities.

With KtAlex you can:

- _search_ for papers, authors, sources, institutions, publishers, funders and concepts,
- _filter_ and _sort_ them by different criteria
- _discover_ the relations between authors and their published papers, publishers and their sources (e.g. journals, repositories), sources and their papers, and so on...
- get _statistics_ about the entities in the OpenAlex catalog. E.g. papers published by authors / sources / publishers by year.
- _integrate_ the OpenAlex API to your Kotlin/Java/JVM application.

## How to get started?

1. Add the following dependency to your `build.gradle(.kts)` file:

```groovy
implementation("org.bensoft:ktalex:1.0.0")
```

2. Dependening on your target language, follow one of the examples below:

<details>
<summary>Kotlin</summary>

1. Use one of the entity clients to get started. E.g. find an author and list the papers they co-authored:

```kotlin
AuthorClient().use { client ->
    client.getByOrcid("0000-0002-9805-1580").resolveWorks()?.forEach { response ->
        response.results?.forEach { work -> println(work.title) }
    }
}
```

Expected output:

```
MoDeS3: Model-Based Demonstrator for Smart and Safe Cyber-Physical Systems
Towards the next generation of reactive model transformations on low-code platforms
On Open Source Tools for Behavioral Modeling and Analysis with fUML and Alf.
Model checking as a service
Towards Continuous Consistency Checking of DevOps Artefacts
Towards Scalable Validation of Low-Code System Models: Mapping EVL to VIATRA Patterns
Pragmatic verification and validation of industrial executable SysML models
```

2. Find works that are cited by more than 10,000 times and are free to read:

```kotlin
WorkClient().use { client ->
    client.getEntities(QueryBuilder().gt("citedByCount", 10000).eq("isOa", true)).forEach { response ->
        println("Total number of works: ${response.meta?.count}")
        println("Works per page: ${response.meta?.perPage}")
        response.results?.forEach { works ->
            // TODO go through the works on this page...
        }
    }
}
```

Expected output (repeated 19 times):

```
Total number of works: 465
Works per page: 25
```
</details>

<details>
<summary>Java</summary>

1. Use one of the entity clients to get started. E.g. find an author and list the papers they co-authored:

```java
try (AuthorClient client = new AuthorClient()) {
    client.getByOrcid("0000-0002-9805-1580", null).resolveWorks().forEach(response ->
            response.getResults().forEach(work -> System.out.println(work.getTitle())));
}
```

Expected output:

```
MoDeS3: Model-Based Demonstrator for Smart and Safe Cyber-Physical Systems
Towards the next generation of reactive model transformations on low-code platforms
On Open Source Tools for Behavioral Modeling and Analysis with fUML and Alf.
Model checking as a service
Towards Continuous Consistency Checking of DevOps Artefacts
Towards Scalable Validation of Low-Code System Models: Mapping EVL to VIATRA Patterns
Pragmatic verification and validation of industrial executable SysML models
```

2. Find works that are cited by more than 10,000 times and are free to read:

```java
try (WorkClient client = new WorkClient()) {
    QueryBuilder queryBuilder = new QueryBuilder().gt("citedByCount", 10000).eq("isOa", true);
    client.getEntities(queryBuilder).forEach(response -> {
        System.out.println("Total number of works: " + response.getMeta().getCount());
        System.out.println("Works per page: " + response.getMeta().getPerPage());
        response.getResults().forEach(work -> {
            // TODO go through the works on this page...
        });
    });
}
```

Expected output (repeated 19 times):

```
Total number of works: 465
Works per page: 25
```

</details>

3. KtAlex uses [ch.qos.logback](https://logback.qos.ch/) to control logging. Add the following `logback.xml` configuration file to `src/main/resources` to enable logging:

```xml
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} MDC=%X{user} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="debug">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
```

4. The source code is published together with the compiled JAR. In  the sources JAR you will find the documentation of the entity client API, the QueryBuilder and some other classes. If you use IntelliJ, then simply click on the `Download Sources` button when opening one of the class files. Alternatively, you can download the `org.bensoft:ktalex-sources` JAR from one of the public repositories and add it to the classpath.

## Where to read more?

You can read more about the OpenAlex API, and the entities (work, author, source, institution, concept, publisher and funder) in the [OpenAlex documentation](https://docs.openalex.org/). The documentation also contains examples on how to use the API. All examples should work in KtAlex as well.

## Where to ask?

- Do you have a question in general or specifically? Then let's start a [discussion](https://github.com/benedekh/ktalex/discussions/).
- Do you have a question about a bug or a weird behavior? Then let's open an [issue](https://github.com/benedekh/ktalex/issues).

## How to contribute?

Check out the [contribution guide](CONTRIBUTING.md) to get started.