package com.neolabs.employeesworktime.service.impl;

import com.neolabs.employeesworktime.entity.WorkingTime;
import com.neolabs.employeesworktime.model.WorkingTypePred;
import com.neolabs.employeesworktime.repository.WorkingTimeRepository;
import com.neolabs.employeesworktime.service.WorkingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class WorkingTimeServiceImpl implements WorkingTimeService {

    @Autowired
    WorkingTimeRepository workingTimeRepository;

    @Autowired
    WorkingTypePred workingTypePred;


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
        // Deja fuera del chequeo a las vacaciones y los días libres
        if (workingTime.getId() != 3003L && workingTime.getId() != 3002L) {
            return workingTime.getHours() > workingTime.getType().getMinHours()
                    && workingTime.getHours() < workingTime.getType().getMaxHours();
        } return true;
    }

    /**Método para chequear la disponibilidad del Empleado a la hora de asignar la
     * jornada laboral
     * */
//    public boolean checkEmployeeAvailability(WorkingTime workingTime, WorkingTimeRepository workingTimeEmployeeRepository) {
//        workingTimeRepository.findWorkingTimeByEmployeeIDAndDate(workingTime);
//
//        return false;
//    }

    /** Crea lista de jornadas laborales por cada Empleado
     * */

}
