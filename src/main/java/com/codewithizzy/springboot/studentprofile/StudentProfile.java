package com.codewithizzy.springboot.studentprofile;

import com.codewithizzy.springboot.student.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class StudentProfile {

  @Id
  @GeneratedValue
  private Integer id;
  private String bio;

  @OneToOne
  @JoinColumn(name = "student_id")
  private Student student;

  // 'Default' Constructor
  public StudentProfile() {
  }

  // Constructor
  public StudentProfile(String bio) {
    this.bio = bio;
  }

  // Getters/Setters
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String setBio() {
    return bio;
  }
}
