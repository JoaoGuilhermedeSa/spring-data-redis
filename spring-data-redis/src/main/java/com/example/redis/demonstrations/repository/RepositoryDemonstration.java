package com.example.redis.demonstrations.repository;

import com.example.redis.model.Product;
import com.example.redis.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryDemonstration {

    @Autowired
    private ProductService productService;

    public void run() {
        System.out.println("\n---- Demonstrating Repository Operations ----");

        System.out.println("Creating and saving two products...");
        Product product1 = new Product("P123", "Gaming Keyboard", 99.99);
        Product product2 = new Product("P456", "Gaming Mouse", 49.99);
        productService.save(product1);
        productService.save(product2);
        System.out.println("Saved products: " + product1 + ", " + product2);

        System.out.println("\nFinding all products...");
        productService.findAll().forEach(p -> System.out.println("Found product: " + p));

        System.out.println("\nDeleting products...");
        productService.deleteById("P123");
        productService.deleteById("P456");
        System.out.println("Deleted products.");
    }
}
