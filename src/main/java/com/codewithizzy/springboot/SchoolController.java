package com.codewithizzy.springboot;

import org.springframework.web.bind.annotation.RestController;

import com.SchoolService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class SchoolController {

  private final SchoolService schoolService;

  public SchoolController(SchoolService schoolService) {
    this.schoolService = schoolService;
  }

  @PostMapping("/schools")
  public SchoolDto create(
      @RequestBody SchoolDto dto) {

    return schoolService.create(dto);
  }

  @GetMapping("/schools")
  public List<SchoolDto> findAll() {
    return schoolService.findAll();
  }
}