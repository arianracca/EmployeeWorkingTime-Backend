package com.neolabs.employeesworktime.service.impl;

import com.neolabs.employeesworktime.entity.WorkingType;
import com.neolabs.employeesworktime.repository.WorkingTypeRepository;
import com.neolabs.employeesworktime.service.WorkingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkingTypeServiceImpl implements WorkingTypeService {

    @Autowired
    WorkingTypeRepository workingTypeRepository;

    /** Método que chequea si es posible crear el Tipo de Turno y de ser así crea el recurso */
    public WorkingType addWorkingType(WorkingType workingType) {
        //Chequea que no exista el tipo de turno
        if (!workingTypeRepository.existsByType(workingType.getType())
                //Chequea que las horas mínimas no sean más que las máximas
                && workingType.getMinHours() <= workingType.getMaxHours()) {
            return workingTypeRepository.save(workingType);
        }
        return null;
    }

    public WorkingType updateWorkingType(WorkingType workingType) {
        //Chequea primero que exista el tipo de turno
        // Luego chequea que las horas mínimas no sean más que las máximas
        if (workingTypeRepository.existsByType(workingType.getType())
                && workingType.getMinHours() <= workingType.getMaxHours()) {
            return (WorkingType) workingTypeRepository;
        }
        return null;
    }
}
