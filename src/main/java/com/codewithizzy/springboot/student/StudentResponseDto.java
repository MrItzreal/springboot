package com.codewithizzy.springboot.student;

public record StudentResponseDto(
    String firstName,
    String lastName,
    String email) {
}