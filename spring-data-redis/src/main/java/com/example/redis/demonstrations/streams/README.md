# Streams Demonstration

This package demonstrates Redis Streams, a powerful and persistent messaging system.

The `StreamsDemonstration` service shows how to:
- Produce a message to a Redis Stream.
- A separate stream consumer (StreamConsumerService) will read and acknowledge this message from a consumer group.

# Output:

---- Demonstrating Streams ----

Sending a message to the stream...

Sent message: {action=login, user=Alice}

Message sent.

Received user action message: {action="login", user="Alice"} with ID: 1757296466183-0

Acknowledged stream message: 1757296466183-0