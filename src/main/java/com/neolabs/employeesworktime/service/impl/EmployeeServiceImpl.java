package com.neolabs.employeesworktime.service.impl;

import com.neolabs.employeesworktime.entity.Employee;
import com.neolabs.employeesworktime.repository.EmployeeRepository;
import com.neolabs.employeesworktime.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    /** Método que chequea si es posible crear el Empleado y de ser así crea el recurso */
    public Employee addEmployee(Employee employee) {
        if (!employeeRepository.existsByEmail(employee.getEmail())) {
            return employeeRepository.save(employee);
        }
        return null;
    }
}
