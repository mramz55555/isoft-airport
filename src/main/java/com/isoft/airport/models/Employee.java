package com.isoft.airport.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Employee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;
    private String firstname;                                                           
    private String lastname;                                                          
    private LocalDate birthdate;
    private char gender;                                                                    
    private String street;                                                           
    private String city;                                                          
    private int zip;
    private String country;
    private String emailAddress;
    private String telephoneno;                                                           
    private double salary;                                                           
    private Department department;    
    private String username;                                                             
    private String password; 
    
    private static enum Department{
        Marketing("Marketing"),
        Buchhaltung("Buchhaltung"),
        Management("Management"),
        Logistik("Logistik"),
        Flugfeld("Flugfeld");

        private String name;
        Department(String name) {
            this.name = name;
        }
    }
}
