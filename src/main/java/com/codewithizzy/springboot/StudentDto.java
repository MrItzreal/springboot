package com.codewithizzy.springboot;

public record StudentDto(
    String firstName,
    String lastName,
    String email,
    Integer schoolId) {
}
