package com.neolabs.employeesworktime.service;

import com.neolabs.employeesworktime.entity.WorkingTime;

public interface WorkingTimeService {

    WorkingTime addWorkingTime(WorkingTime workingTime);

    WorkingTime updateWorkingTime(WorkingTime workingTime);
}
