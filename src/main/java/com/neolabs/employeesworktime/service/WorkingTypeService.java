package com.neolabs.employeesworktime.service;

import com.neolabs.employeesworktime.entity.WorkingType;

import java.util.List;

public interface WorkingTypeService {

    WorkingType addWorkingType(WorkingType workingType);
    WorkingType updateWorkingType(WorkingType workingType);
    public List<WorkingType> getAllWorkingType();
    public WorkingType getWorkingTypeByName(String nombre);

}
