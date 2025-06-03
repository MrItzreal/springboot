package com.codewithizzy.springboot.student;

import com.codewithizzy.springboot.school.School;
import com.codewithizzy.springboot.studentprofile.StudentProfile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;

// We need to specify a primary key when working with entities

@Entity
// @Table(name = "IzzyDev")
public class Student {

  @Id
  @GeneratedValue
  private Integer id;
  @Column(name = "c_fname", length = 20)
  private String firstName;
  private String lastName;
  @Column(unique = true)
  private String email;
  private int age;

  // StudentProfile 'object'
  @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
  private StudentProfile studentProfile;

  // Link between School entity & Student
  @ManyToOne
  @JoinColumn(name = "school_id")
  @JsonBackReference
  private School school;

  // 'Default' Constructor
  public Student() {
  }

  // Constructor
  public Student(
      String firstName,
      String lastName,
      String email,
      int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.age = age;
  }

  // Getters/Setters
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public StudentProfile getStudentProfile() {
    return studentProfile;
  }

  public void setStudentProfile(StudentProfile studentProfile) {
    this.studentProfile = studentProfile;
  }

  public School getSchool() {
    return school;
  }

  public void setSchool(School school) {
    this.school = school;
  }
}
