package com.example.demo.services;

import com.example.demo.models.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    public ProductService() {
        products.add(new Product(idCounter.incrementAndGet(), "Laptop DELL", 55999.99, "32GB RAM", 10));
        products.add(new Product(idCounter.incrementAndGet(), "DESK PC HP", 48999.99, "64GB RAM", 5));
        products.add(new Product(idCounter.incrementAndGet(), "Tablet Samsung", 39999.99, "Black", 5));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Optional<Product> getProductById(Long id) {
        return products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst();
    }

    public Product addProduct(Product product) {
        Product newProduct = new Product(
                idCounter.incrementAndGet(),
                product.name(),
                product.price(),
                product.description(),
                product.stock());
        products.add(newProduct);
        return newProduct;
    }

    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        int index = IntStream.range(0, products.size())
                .filter(i -> products.get(i).id().equals(id))
                .findFirst()
                .orElse(-1);

        if (index != -1) {
            Product productToUpdate = new Product(
                    id,
                    updatedProduct.name(),
                    updatedProduct.price(),
                    updatedProduct.description(),
                    updatedProduct.stock());
            products.set(index, productToUpdate);
            return Optional.of(productToUpdate);
        }
        return Optional.empty();
    }

    public boolean deleteProduct(Long id) {
        return products.removeIf(p -> p.id().equals(id));
    }
}