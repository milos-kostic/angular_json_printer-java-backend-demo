package com.example.demo.models;

public record Product(
        Long id,
        String name,
        double price,
        String description,
        int stock) {
}