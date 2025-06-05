package com.codewithizzy.springboot.student;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/* - It's good practice to always specify the response status.
 * - RestController used at the class level to indicate an annotated class is a controller.
*/

@RestController
public class StudentController {

  private final StudentService studentService;

  // Constructor
  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @PostMapping("/students")
  public StudentResponseDto saveStudent(
      @Valid @RequestBody StudentDto dto) {
    return this.studentService.saveStudent(dto);
  }

  @GetMapping("/students")
  public List<StudentResponseDto> findAllStudent() {
    return studentService.findAllStudent();
  }

  @GetMapping("/students/{student-id}")
  public StudentResponseDto findStudentById(@PathVariable("student-id") Integer id) {
    return studentService.findStudentById(id);
  }

  @GetMapping("/students/search/{student-name}")
  public List<StudentResponseDto> findStudentsByName(@PathVariable("student-name") String name) {
    return studentService.findStudentsByName(name);
  }

  @DeleteMapping("students/{student-id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable("student-id") Integer id) {
    studentService.delete(id);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
    var errors = new HashMap<String, String>();
    exp.getBindingResult().getAllErrors().forEach(error -> {
      var fieldName = ((FieldError) error).getField();
      var errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}