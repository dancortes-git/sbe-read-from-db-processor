# Spring Batch Examples - Read from DB Processor

Project with examples using Spring Batch. In this project there are configurations using JdbcPagingItemReader and ItemWriter simple implementation to write a log.

## Requirements
You must set a database used for tests in our example we are using Postgresql.
Below you can find a command line with docker.
```
docker run -d --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -v [local-directory-here]:/var/lib/postgresql/data postgres:10.10
```
* Java 11
* Maven 3.5.* or higher
* Make sure your workspace is using encode UTF-8
* The Lombok library is being used as a resource to help the creation of classes, check if your IDE has support/plugin to use Lombok. More information at https://projectlombok.org.

### Initialize the Spring Batch database model
At `application.properties` file in the ` src / main / resources` folder there is the parameter below that is commented by default, in the first time you run a spring batch with a empty database you need to create the data model of Spring Batch repository.
```
spring.batch.initialize-schema=always
```

## Getting Started

### Reference documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/maven-plugin/)
* [Spring Batch](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/htmlsingle/#howto-batch-applications)

### Guides
The guides below illustrate how to correctly use some spring batch features:

* [Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)

### Environment variables
The `application.properties` file in the` src / main / resources` folder contains the environment variables that will be used by the application.
If no environment variable is informed, the deafult values ​​will be used.
Description of the variables:
* `REPOSITORY_DRIVER`: (spring.datasource.driverClassName) Driver used to connect to the Spring Batch repository database. The default value is: `org.h2.Driver`, referring to database h2 in memory.
* `REPOSITORY_URL`: (spring.datasource.url) Connection sring to the Spring Batch repository database. The default value is: jdbc:h2:mem:springbatch.
* `REPOSITORY_USER`: (spring.datasource.username) User connecting to the Spring Batch repository database. The default value is: sa.
* `REPOSITORY_PASS`: (spring.datasource.password) Password for connecting to the Spring Batch repository database. The default value is: <empty>.


### Running the project with docker
The maven Jib plugin builds a Docker image. To create an image, execute the command below replacing the variable {imageName} with the name of the image to be generated. Jib will use the local Docker daemon:

```
mvn clean package jib:dockerBuild -Dimage={imageName}
```

To run the job using docker, run the command below:

```
docker run -it -e {environment variable}={environment variable value} {imageName} {job parameter}={job parameter value}
```
