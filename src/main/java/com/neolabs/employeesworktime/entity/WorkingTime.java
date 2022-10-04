package com.neolabs.employeesworktime.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Esta es la entidad de Jornadas Laborales de Empleados
 * esta tabla estructura todos los datos referidos a los distintos
 * turnos realizados por cada empleado, sus vacaciones, días libres, etc.
  * */
@Entity
@NoArgsConstructor
@Table(name = "employeesworkingtimes")
public class WorkingTime {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "user_generator", initialValue = 1000)
    private Long id;

    @NotNull
    @Getter
    @Setter
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeID")
    private Employee employee;

    @NotNull
    @Getter
    @Setter
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workingTypeID")
    private WorkingType type;

    @NotNull
    @Getter
    @Setter
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    @NotNull
    @Getter
    @Setter
    @JsonFormat(pattern = "HH:mm")
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime startTime;

    @NotNull
    @Getter
    @Setter
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime endTime;

    private Long hours;


    /** Método para setear las Horas automaticamente a partir de la hora de inicio y la de final
     * de una determinada actividad programada para la jornada laboral de un empleado específico
     * @param startTime la hora de inicio de la actividad programada
     * @param endTime la hora de finalización de la actividad programada
     * @return hours la cantidad de horas que requiere la actividad en formato Long
     */
    public Long setHours(LocalTime startTime, LocalTime endTime) {
        long hours =  ChronoUnit.HOURS.between(endTime, startTime);
        return hours;
    }

    public Long getHours() {
        this.hours = setHours(this.startTime, this.endTime);
        return hours;
    }

}
