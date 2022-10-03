package com.neolabs.employeesworktime.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Esta es la entidad de Jornadas Laborales de Empleados
 * aquí se pueden listar todos los datos referidos a los distintos
 * turnos realizados por cada empleado, sus vacaciones, días libres, etc.
  * */


@Entity
@NoArgsConstructor
@Table(name = "employeesworktimes")
public class WorkingTime {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank
    @Getter
    @Setter
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeID")
    private Employee employee;

    @NotNull
    @NotBlank
    @Getter
    @Setter
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workingTypeID")
    private WorkingType type;

    @NotNull
    @NotBlank
    @Getter
    @Setter
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date;

    @NotNull
    @NotBlank
    @Getter
    @Setter
    @DateTimeFormat(pattern = "hh-mm")
    private Date startTime;

    @NotNull
    @NotBlank
    @Getter
    @Setter
    @DateTimeFormat(pattern = "hh-mm")
    private Date endTime;

    private Long hours;

    public WorkingTime(Employee employee, WorkingType type, Date date, Date startTime, Date endTime) {
        this.employee = employee;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /** Método para setear las Horas automaticamente a partir de la hora de inicio y la de final
     * de una determinada actividad programada para la jornada laboral de un empleado específico
     * @param startTime la hora de inicio de la actividad programada
     * @param endTime la hora de finalización de la actividad programada
     * @return hours la cantidad de horas que requiere la actividad en formato Long
     */
    public Long setHours(Date startTime, Date endTime) {
        long numberOfMilliseconds = endTime.getTime() - startTime.getTime();
        this.hours = TimeUnit.HOURS.convert(numberOfMilliseconds, TimeUnit.MILLISECONDS);
        return hours;
    }

    public Long getHours() {
        this.hours = setHours(startTime, endTime);
        return hours;
    }

}
