# Spring Data Redis Unified Example

This project demonstrates various features of Spring Data Redis in a unified application.

## Prerequisites

- Java 11
- Maven
- Docker (for running Redis)

## Running the Application

1.  Start a Redis instance using Docker:
    ```sh
    docker run -d --name redis -p 6379:6379 redis
    ```

2.  Run the Spring Boot application:
    ```sh
    mvn spring-boot:run
    ```

## Demonstrations

You can trigger the demonstrations by sending POST requests to the following endpoints:

-   **Caching:** `http://localhost:8080/demonstrate/caching`
-   **Repository:** `http://localhost:8080/demonstrate/repository`
-   **@Query:** `http://localhost:8080/demonstrate/query`
-   **Pub/Sub:** `http://localhost:8080/demonstrate/pubsub`
-   **Streams:** `http://localhost:8080/demonstrate/streams`

## REST API

The application also exposes a REST API for managing products:

-   **GET /products**: Get all products.
-   **POST /products**: Create a new product.
-   **GET /products/search/findByName?name={name}**: Find products by name.
-   **GET /products/search/findByPriceGreaterThan?price={price}**: Find products by price greater than the given value.

## Streams

The application also demonstrates Redis Streams.

-   **POST /send**: Send a message to the stream. The body of the request should be a JSON object, for example: `{"key": "value"}`.



