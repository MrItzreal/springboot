package com.codewithizzy.springboot.student;

import jakarta.validation.constraints.NotEmpty;

public record StudentDto(
        @NotEmpty(message = "First Name should not be empty") String firstName,
        @NotEmpty(message = "Last Name should not be empty") String lastName,
        String email,
        Integer schoolId) {
}