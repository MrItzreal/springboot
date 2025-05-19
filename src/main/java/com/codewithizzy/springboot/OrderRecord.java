package com.codewithizzy.springboot;

// Records are "final" by default
public record OrderRecord(
    String customerName,
    String productName,
    int quantity) {
}
