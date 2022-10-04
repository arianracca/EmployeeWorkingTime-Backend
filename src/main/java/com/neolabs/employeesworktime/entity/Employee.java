package com.neolabs.employeesworktime.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

/**
 * Esta es la entidad de Empleados.
 * Permite crear los registros de empleados con sus datos*
 * */

@Entity
@NoArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @NotNull
    @NotBlank
    @Column (nullable = false, length = 50)
    private String name;

    @NotNull
    @NotBlank
    @Column (nullable = false, length = 50)
    private String lastName;

    @NotNull
    @NotBlank
    @Email
    @Column (nullable = false, length = 150)
    private String email;

    @Getter
    @Setter
    @NumberFormat
    private Long phoneNumber;

    public Employee(String name, String lastName, String email, Long phoneNumber) {
        this.name = name.toUpperCase();
        this.lastName = lastName.toUpperCase();
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    //Getters y Setters con formateo normalizado de atributos
    public String getEmail() {
        return email.toLowerCase();
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getName() {
        return name.toUpperCase();
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public String getLastName() {
        return lastName.toUpperCase();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.toUpperCase();
    }
}
