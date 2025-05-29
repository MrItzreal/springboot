package com.codewithizzy.springboot;

public record StudentResponseDto(
    String firstName,
    String lastName,
    String email) {
}