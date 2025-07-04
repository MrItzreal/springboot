package com.codewithizzy.springboot.student;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
  private final StudentRepository repository;
  private final StudentMapper studentMapper;

  // Constructor for repository & studentMapper
  public StudentService(StudentRepository repository, StudentMapper studentMapper) {
    this.repository = repository;
    this.studentMapper = studentMapper;
  }

  public StudentResponseDto saveStudent(StudentDto dto) {
    var student = studentMapper.toStudent(dto);
    var savedStudent = repository.save(student);
    return studentMapper.toStudentResponseDto(savedStudent);
  }

  public List<StudentResponseDto> findAllStudent() {
    return repository.findAll()
        .stream().map(studentMapper::toStudentResponseDto).collect(Collectors.toList());
  }

  public StudentResponseDto findStudentById(Integer id) {
    return repository.findById(id).map(studentMapper::toStudentResponseDto)
        .orElse(null);
  }

  public List<StudentResponseDto> findStudentsByName(String name) {
    return repository.findAllByFirstNameContaining(name)
        .stream().map(studentMapper::toStudentResponseDto).collect(Collectors.toList());
  }

  public void delete(Integer id) {
    repository.deleteById(id);
  }
}