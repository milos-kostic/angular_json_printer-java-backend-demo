package com.example.demo.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    public ProductController() {
        products.add(new Product(idCounter.incrementAndGet(), "Laptop DELL", 55999.99, "32GB RAM", 10));
        products.add(new Product(idCounter.incrementAndGet(), "DESK PC HP", 48999.99, "64GB RAM", 5));
        products.add(new Product(idCounter.incrementAndGet(), "Tablet Samsung", 39999.99, "Black", 5));
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return products;
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable Long id) {
        return products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Proizvod nije pronadjen"));
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        Product newProduct = new Product(
                idCounter.incrementAndGet(),
                product.name(),
                product.price(),
                product.description(),
                product.stock());
        products.add(newProduct);
        return newProduct;
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product existing = getProduct(id);
        products.remove(existing);
        Product updated = new Product(
                id,
                product.name(),
                product.price(),
                product.description(),
                product.stock());
        products.add(updated);
        return updated;
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        Product product = getProduct(id);
        products.remove(product);
    }

    public record Product(
            Long id,
            String name,
            double price,
            String description,
            int stock) {
    }
}