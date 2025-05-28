package com.codewithizzy.springboot;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class School {

  @Id
  @GeneratedValue
  private Integer id;

  private String name;

  // One school can server more than one student
  @OneToMany(mappedBy = "school")
  @JsonManagedReference
  private List<Student> students;

  // 'Default' Constructor
  public School() {
  }

  // Constructor
  public School(String name) {
    this.name = name;
  }

  // Getters/Setters
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Student> getStudents() {
    return students;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }
}
