package com.codewithizzy.springboot.student;

public record StudentDto(
    String firstName,
    String lastName,
    String email,
    Integer schoolId) {
}
