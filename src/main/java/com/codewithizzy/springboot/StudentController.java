package com.codewithizzy.springboot;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/* - It's good practice to always specify the response status.
 * - RestController used at the class level to indicate an annotated class is a controller.
*/

@RestController
public class StudentController {

  private final StudentRepository repository;

  // Constructor
  public StudentController(StudentRepository repository) {
    this.repository = repository;
  }

  @PostMapping("/students")
  public StudentResponseDto post(
      @RequestBody StudentDto dto) {
    var student = toStudent(dto);
    var savedStudent = repository.save(student);
    return toStudentResponseDto(savedStudent);
  }

  private Student toStudent(StudentDto dto) {
    var student = new Student();
    student.setFirstName(dto.firstName());
    student.setLastName(dto.lastName());
    student.setEmail(dto.email());

    var school = new School();
    school.setId(dto.schoolId());

    student.setSchool(school);
    return student;
  }

  private StudentResponseDto toStudentResponseDto(Student student) {
    return new StudentResponseDto(
        student.getFirstName(),
        student.getLastName(),
        student.getEmail());
  }

  @GetMapping("/students")
  public List<Student> findAllStudent() {
    return repository.findAll();
  }

  @GetMapping("/students/{student-id}")
  public Student findStudentById(@PathVariable("student-id") Integer id) {
    return repository.findById(id)
        .orElse(new Student());
  }

  @GetMapping("/students/search/{student-name}")
  public List<Student> findStudentsByName(@PathVariable("student-name") String name) {
    return repository.findAllByFirstNameContaining(name);
  }

  @DeleteMapping("students/{student-id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable("student-id") Integer id) {
    repository.deleteById(id);
  }
}
