package com.neolabs.employeesworktime.dto;

import com.neolabs.employeesworktime.entity.Employee;
import com.neolabs.employeesworktime.entity.WorkingType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**Clase para poder tomar los datos desde el body */
public class WorkingTimeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long employee;
    private Long type;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long hours;

    public WorkingTimeDto(Long employee, Long type, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.employee = employee;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
		this.hours = setHours(startTime, endTime);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmployee() {
		return employee;
	}

	public void setEmployee(Long employee) {
		this.employee = employee;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Long getHours() {
		this.hours = setHours(this.startTime, this.endTime);
		return hours;
	}

	public Long setHours(LocalTime startTime, LocalTime endTime) {
		Long hours =  ChronoUnit.HOURS.between(startTime, endTime);
		return hours;
	}
    
}
