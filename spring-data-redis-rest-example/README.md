# Spring Data Redis + Spring Data REST Example

This project demonstrates how to use Spring Data Redis in combination with Spring Data REST to quickly expose a Redis-backed repository as a RESTful API.

## How to Run the Project

1.  **Navigate to the project directory** in your terminal:
    ```bash
    cd spring-data-redis-rest-example
    ```

2.  **Run the application** using Maven:
    ```bash
    mvn spring-boot:run
    ```
    The application will start and connect to your local Redis instance.

## Interacting with the REST API

Once the application is running, Spring Data REST automatically exposes REST endpoints for the `ProductRepository`. You can use a tool like `curl` or Postman to interact with the API.

The base URL for the product resources is `http://localhost:8080/products`.

*   **Create a Product (POST /products)**
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"id": "A123", "name": "Gaming Mouse", "price": 59.99}' http://localhost:8080/products
    ```

*   **Retrieve a Product (GET /products/{id})**
    ```bash
    curl http://localhost:8080/products/A123
    ```

*   **Retrieve all Products (GET /products)**
    ```bash
    curl http://localhost:8080/products
    ```

*   **Update a Product (PUT /products/{id})**
    ```bash
    curl -X PUT -H "Content-Type: application/json" -d '{"id": "A123", "name": "Ergonomic Mouse", "price": 65.50}' http://localhost:8080/products/A123
    ```

*   **Delete a Product (DELETE /products/{id})**
    ```bash
    curl -X DELETE http://localhost:8080/products/A123
    ```
