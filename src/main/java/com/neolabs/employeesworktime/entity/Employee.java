package com.neolabs.employeesworktime.entity;


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
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @NumberFormat
    private Long phoneNumber;
    

    public Long getId() {
		return id;
	}


	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    /**Método constructor formateado con estandarizacion
     * de letras mayúsculas en nombre y apellido
     * y letras minúsculas para email
     * */
	public Employee(String name, String lastName, String email, Long phoneNumber) {
        this.name = name.toUpperCase();
        this.lastName = lastName.toUpperCase();
        this.email = email.toLowerCase();
        this.phoneNumber = phoneNumber;
    }


    //Constructor de la clase
    public Employee() {
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
