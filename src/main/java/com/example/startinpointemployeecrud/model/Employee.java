package com.example.startinpointemployeecrud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "employee_name", nullable = false, unique = true)
        @Size(min = 5, max = 250)
        private String name;

        @Column(name = "employee_email", nullable = false, unique = true)
        private String email;

        @Column(name = "employee_address", nullable = false)
        private String address;


}
