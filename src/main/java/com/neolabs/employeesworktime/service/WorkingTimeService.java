package com.neolabs.employeesworktime.service;

import com.neolabs.employeesworktime.dto.WorkingTimeDto;
import com.neolabs.employeesworktime.entity.WorkingTime;

import java.util.List;
import java.util.Optional;

public interface WorkingTimeService {

    WorkingTime addWorkingTime(WorkingTimeDto workingTimeDTO);
    
    WorkingTime updateWorkingTime(WorkingTime workingTime);

    Optional<WorkingTime> getWorkingTimeById(Long id);

    List<WorkingTime> getWorkingTimeByEmployeeId(Long employeeId);

    List<WorkingTime> getWorkingTimeByEmployeeIdAndWorkingType(Long employeeId, Long workingTypeId);


}
