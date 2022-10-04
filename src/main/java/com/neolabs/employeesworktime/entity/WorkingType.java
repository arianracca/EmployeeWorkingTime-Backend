package com.neolabs.employeesworktime.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


/**
 * Esta es la entidad de Tipos de Turno de trabajo.
 * A partir de aquí se instancian las distintas jornadas laborales
 * ya sean las predefinidas por el ejercicio o todas aquellas que se requieran posteriormente
 * */

@Entity
@NoArgsConstructor
@Table(name = "workingtypes")
public class WorkingType {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long workingTypeID;

    @Column (nullable = false, length = 50)
    private String type;

    @Setter
    @Getter
    @Column (nullable = false, length = 5)
    private Long minHours;
    
    @Setter
    @Getter
    @Column (nullable = false, length = 5)
    private Long maxHours;

    public String getType() {
        return type.toUpperCase();
    }

    public void setType(String type) {
        this.type = type.toUpperCase();
    }
}
