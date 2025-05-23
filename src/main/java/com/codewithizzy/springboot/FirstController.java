package com.codewithizzy.springboot;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/* - It's good practice to always specify the response status.
 * - RestController used at the class level to indicate an annotated class is a controller.
*/

@RestController
public class FirstController {

  private final StudentRepository repository;

  // Constructor
  public FirstController(StudentRepository repository) {
    this.repository = repository;
  }

  @PostMapping("/students")
  public Student post(
      @RequestBody Student student) {
    return repository.save(student);
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
}
