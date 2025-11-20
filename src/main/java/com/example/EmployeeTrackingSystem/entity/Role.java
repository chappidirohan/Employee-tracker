/*package com.example.EmployeeTrackingSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // e.g. "ROLE_ADMIN", "ROLE_USER"
}*/
package com.example.EmployeeTrackingSystem.entity;

import jakarta.persistence.*;

import lombok.*;

@Entity

@Table(name = "roles")

@Data

@NoArgsConstructor

@AllArgsConstructor

public class Role {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    // e.g. "ROLE_USER", "ROLE_ADMIN" or just "User", "Admin" depending on your convention

    @Column(unique = true, nullable = false)

    private String name;

}



