package com.neolabs.employeesworktime.service.impl;

import com.neolabs.employeesworktime.entity.WorkingTime;
import com.neolabs.employeesworktime.repository.WorkingTimeRepository;
import com.neolabs.employeesworktime.repository.WorkingTypeRepository;
import com.neolabs.employeesworktime.service.WorkingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class WorkingTimeServiceImpl implements WorkingTimeService {

    @Autowired
    WorkingTimeRepository workingTimeRepository;


    /**
     * Método que crea la Jornada Laboral asociada al Empleado
     * */
    public WorkingTime addWorkingTime(WorkingTime workingTime) {
        if (checkWorkingTimeMinMaxHours(workingTime)) {
            return workingTimeRepository.save(workingTime);
        }
        return null;
    }


    public WorkingTime updateWorkingTime(WorkingTime workingTime) {
        return null;
    }

    /**Método para chequear que la jornada laboral que se asigna se ajusta
     * a los criterios del Tipo de Turno
     * */
    public boolean checkWorkingTimeMinMaxHours(WorkingTime workingTime) {
        return workingTime.getHours() > workingTime.getType().getMinHours()
                && workingTime.getHours() < workingTime.getType().getMaxHours();
    }
    /**Método para chequear la disponibilidad del Empleado a la hora de asignar la
     * jornada laboral
     * */
//    public boolean checkEmployeeAvailability(WorkingTime workingTime, WorkingTimeRepository workingTimeEmployeeRepository) {
//        workingTimeEmployeeRepository.findByEmployee(workingTime.getEmployee())
//
//        return false;
//    }

    /** Crea lista de jornadas laborales por cada Empleado
     * */
//    public List<> findByEmployee(Long employeeId, WorkingTimeRepository workingTimeRepository) {
//        List<WorkingTimeByEmployee> workingTimeByEmployee = workingTimeRepository.stream()
//                .filter(workingTimeByEmployee -> workingTimeByEmployee.getEmployee()
//                        .collect(Collectors.toList());
//
//
////        WorkingTime workingTimeByEmployee = workingTimeRepository.findByEmployee(employeeId);
//        return (WorkingTime) workingTimeByEmployee;
//    }

}
