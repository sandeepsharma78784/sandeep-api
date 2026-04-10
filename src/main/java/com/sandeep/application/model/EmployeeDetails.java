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
/*
maan lo
private String department; maan lo ye db side se nahi aa raha he and hum db resonse ko EmployeeDetails me s
serialize krna chahte he to is field ko ignore krna padega during serialization and deserialization
iske liye automatic null set ho jayega jab bhi db se response aayega aur jab bhi hum EmployeeDetails ka object banayenge to is field ko set nahi karenge to ye null hi rhega
*/
// default constructor
public EmployeeDetails() {}
public EmployeeDetails(String empId, String familyName, Double hike) {
    this.empId = empId;
    this.familyName = familyName;
    this.hike = hike;
}
}