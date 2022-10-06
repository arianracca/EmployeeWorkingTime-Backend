package com.neolabs.employeesworktime.entity;

import javax.persistence.*;


/**
 * Esta es la entidad de Tipos de Turno de trabajo.
 * A partir de aquí se instancian las distintas jornadas laborales
 * ya sean las predefinidas por el ejercicio o todas aquellas que se requieran posteriormente
 * */

@Entity
@Table(name = "workingtypes")
public class WorkingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "user_generator", initialValue = 2000)
    private Long id;

    @Column (nullable = false, length = 50)
    private String type;

    @Column (nullable = false, length = 5)
    private Long minHours;

    @Column (nullable = false, length = 5)
    private Long maxHours;

    public WorkingType(Long id, String type, Long minHours, Long maxHours) {
        this.id = id;
        this.type = type;
        this.minHours = minHours;
        this.maxHours = maxHours;
    }


    public WorkingType() {
	}


	public String getType() {
        return type.toUpperCase();
    }

    public void setType(String type) {
        this.type = type.toUpperCase();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMinHours() {
		return minHours;
	}

	public void setMinHours(Long minHours) {
		this.minHours = minHours;
	}

	public Long getMaxHours() {
		return maxHours;
	}

	public void setMaxHours(Long maxHours) {
		this.maxHours = maxHours;
	}



}
