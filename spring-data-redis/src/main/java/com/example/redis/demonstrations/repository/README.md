# Repository Operations Demonstration

This package demonstrates basic CRUD (Create, Read, Update, Delete) operations using Spring Data Redis Repositories.

The `RepositoryDemonstration` service shows how to:
- Save new `Product` entities to Redis.
- Retrieve all `Product` entities from Redis.
- Delete `Product` entities from Redis.

# Output:

---- Demonstrating Repository Operations ----

Creating and saving two products...

Saved products: Product{id='P123', name='Gaming Keyboard', price=99.99}, Product{id='P456', name='Gaming Mouse', price=49.99}


Finding all products...

Found product: Product{id='998', name='Webcam', price=199.99}

Found product: Product{id='999', name='Webcam', price=99.99}

Found product: Product{id='P123', name='Gaming Keyboard', price=99.99}

Found product: Product{id='P456', name='Gaming Mouse', price=49.99}

Deleting products...

Deleted products.