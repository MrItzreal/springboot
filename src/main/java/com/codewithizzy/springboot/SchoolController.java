package com.codewithizzy.springboot;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class SchoolController {

  private final SchoolRepository schoolRepository;

  // Constructor
  public SchoolController(SchoolRepository schoolRepository) {
    this.schoolRepository = schoolRepository;
  }

  @PostMapping("/schools")
  public School create(
      @RequestBody School school) {
    return schoolRepository.save(school);
  }

  @GetMapping("/schools")
  public List<School> findAll() {
    return schoolRepository.findAll();
  }
}
