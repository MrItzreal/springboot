package com.codewithizzy.springboot.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

// Test Isolation w/ Mockito
class StudentServiceTest {
  // Service you want to test:
  @InjectMocks
  private StudentService studentService;

  // Declare dependencies:
  @Mock
  private StudentRepository repository;
  @Mock
  private StudentMapper studentMapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void should_successfully_save_a_student() {
    // Given
    StudentDto dto = new StudentDto(
        "Alucard",
        "Tepes",
        "Vamp@mail.com",
        1);

    Student student = new Student(
        "Alucard",
        "Tepes",
        "Vamp@mail.com",
        20);

    Student savedStudent = new Student(
        "Alucard",
        "Tepes",
        "Vamp@mail.com",
        20);
    savedStudent.setId(1);

    // Mock the calls
    Mockito.when(studentMapper.toStudent(dto))
        .thenReturn(student);
    Mockito.when(repository.save(student))
        .thenReturn(savedStudent);
    Mockito.when(studentMapper.toStudentResponseDto(savedStudent))
        .thenReturn(new StudentResponseDto(
            "Alucard",
            "Tepes",
            "Vamp@mail.com"));

    // When
    StudentResponseDto responseDto = studentService.saveStudent(dto);

    // Then
    assertEquals(dto.firstName(), responseDto.firstName());
    assertEquals(dto.lastName(), responseDto.lastName());
    assertEquals(dto.email(), responseDto.email());

    // Verify these methods are called once
    Mockito.verify(studentMapper, Mockito.times(1))
        .toStudent(dto);
    Mockito.verify(repository, Mockito.times(1))
        .save(student);
    Mockito.verify(studentMapper, Mockito.times(1))
        .toStudentResponseDto(savedStudent);
  }

  @Test
  public void should_return_all_students() {
    // Given
    List<Student> students = new ArrayList<>();
    students.add(new Student(
        "Alucard",
        "Tepes",
        "Vamp@mail.com",
        20));

    // Mock the calls
    Mockito.when(repository.findAll())
        .thenReturn(students);

    Mockito.when(studentMapper.toStudentResponseDto(any(Student.class)))
        .thenReturn(new StudentResponseDto(
            "Alucard",
            "Tepes",
            "Vamp@mail.com"));

    // When
    List<StudentResponseDto> responseDtos = studentService.findAllStudent();

    // Then
    assertEquals(students.size(), responseDtos.size());

    // Verify
    Mockito.verify(repository, Mockito.times(1)).findAll();
  }

  @Test
  public void should_return_student_by_id() {
    // Given
    Integer studentId = 1;
    Student student = new Student(
        "Alucard",
        "Tepes",
        "Vamp@mail.com",
        20);

    // Mock the calls
    Mockito.when(repository.findById(studentId))
        .thenReturn(Optional.of(student));

    Mockito.when(studentMapper.toStudentResponseDto(any(Student.class)))
        .thenReturn(new StudentResponseDto(
            "Alucard",
            "Tepes",
            "Vamp@mail.com"));

    // When
    StudentResponseDto dto = studentService.findStudentById(studentId);

    // Then
    assertEquals(dto.firstName(), student.getFirstName());
    assertEquals(dto.lastName(), student.getLastName());
    assertEquals(dto.email(), student.getEmail());

    // Verify
    Mockito.verify(repository, times(1))
        .findById(studentId);
  }

  @Test
  public void should_find_student_by_name() {
    // Given
    String studentName = "Alucard";

    List<Student> students = new ArrayList<>();
    students.add(new Student(
        "Alucard",
        "Tepes",
        "Vamp@mail.com",
        20));

    // Mock the calls
    Mockito.when(repository.findAllByFirstNameContaining(studentName))
        .thenReturn(students);

    Mockito.when(studentMapper.toStudentResponseDto(any(Student.class)))
        .thenReturn(new StudentResponseDto(
            "Alucard",
            "Tepes",
            "Vamp@mail.com"));

    // When
    var responseDto = studentService.findStudentsByName(studentName);

    // Then
    assertEquals(students.size(), responseDto.size());

    // Verify
    Mockito.verify(repository, times(1))
        .findAllByFirstNameContaining(studentName);
  }

  @Test
  public void should_delete_student() {
    // Given
    Integer studentId = 1;

    // When
    studentService.delete(studentId);

    // Then
    // Verify that the deleteById method on the repository was called exactly once
    // with the correct studentId
    Mockito.verify(repository, times(1))
        .deleteById(studentId);
  }
}