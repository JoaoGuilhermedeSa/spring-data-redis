# Spring Data Redis Streams Example

This project demonstrates how to use Redis Streams with Spring Data Redis to create a reliable messaging system with a producer and a consumer.

## How to Run the Project

1.  **Navigate to the project directory** in your terminal:
    ```bash
    cd spring-data-redis-streams-example
    ```

2.  **Run the application** using Maven:
    ```bash
    mvn spring-boot:run
    ```

3.  **Observe the Consumer**: When the application starts, you will see a message in the console indicating that the consumer group has been created (or that it already exists). The consumer is now listening for messages in the background.

## Interacting with the Stream

### 1. Send a Message (Producer)

Open a new terminal and use `curl` to send a JSON payload to the `/send` endpoint. This will add a new message to the Redis Stream.

```bash
curl -X POST -H "Content-Type: application/json" -d '{"user": "Alice", "action": "login"}' http://localhost:8080/send
```

### 2. Check the Application Console

Go back to the console where your Spring Boot application is running. You should see output similar to this, showing that the message was received and acknowledged by the `StreamConsumerService`:

```
Sent message: {user=Alice, action=login}
Received message: {user=Alice, action=login} with ID: 166213...
Acknowledged message: 166213...
```

### 3. Send Another Message

You can send as many messages as you like.

```bash
curl -X POST -H "Content-Type: application/json" -d '{"user": "Bob", "action": "post_comment"}' http://localhost:8080/send
```

Each time, you will see the corresponding "Received" and "Acknowledged" logs in the application console, demonstrating the complete message flow.
