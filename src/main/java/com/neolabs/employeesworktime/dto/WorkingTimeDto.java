package com.neolabs.employeesworktime.dto;

import com.neolabs.employeesworktime.entity.Employee;
import com.neolabs.employeesworktime.entity.WorkingType;

import java.io.Serializable;
import java.util.Date;

public class WorkingTimeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Employee employee;
    private WorkingType type;
    private Date date;
    private Date startTime;
    private Date endTime;
    private Long hours;

    public WorkingTimeDto(Employee employee, WorkingType type, Date date, Date startTime, Date endTime) {
        this.employee = employee;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
