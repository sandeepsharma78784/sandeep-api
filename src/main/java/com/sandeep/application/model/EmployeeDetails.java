package com.sandeep.application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

//for getter setter 
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EmployeeDetails {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;

private String empId;
private String familyName;
private Double hike;


// default constructor
public EmployeeDetails() {}
public EmployeeDetails(String empId, String familyName, Double hike) {
    this.empId = empId;
    this.familyName = familyName;
    this.hike = hike;
}
}