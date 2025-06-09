package com.codewithizzy.springboot.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentMapperTest {

  private StudentMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new StudentMapper();
  }

  @Test
  public void shouldMapStudentDtoToStudent() {
    // Given
    StudentDto dto = new StudentDto(
        "Izzy",
        "DevG",
        "fake@email.com",
        1);

    // When
    Student student = mapper.toStudent(dto);

    // Then
    assertEquals(dto.firstName(), student.getFirstName());
    assertEquals(dto.lastName(), student.getLastName());
    assertEquals(dto.email(), student.getEmail());
    assertNotNull(student.getSchool());
    assertEquals(dto.schoolId(), student.getSchool().getId());
  }

  @Test
  public void should_throw_null_pointer_exception_when_studentDto_is_null() {
    var exp = assertThrows(NullPointerException.class, () -> mapper.toStudent(null));
    assertEquals("The student Dto should not be null", exp.getMessage());
  }

  @Test
  public void shouldMapStudentToStudentResponseDto() {
    // Given
    Student student = new Student(
        "Pokemon",
        "Yeung",
        "syp@hk.com",
        48);

    // When
    StudentResponseDto response = mapper.toStudentResponseDto(student);

    // Then
    assertEquals(response.firstName(), student.getFirstName());
    assertEquals(response.lastName(), student.getLastName());
    assertEquals(response.email(), student.getEmail());
  }
}