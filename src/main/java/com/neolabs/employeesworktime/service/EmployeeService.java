package com.neolabs.employeesworktime.service;

import com.neolabs.employeesworktime.entity.Employee;

import java.util.List;


public interface EmployeeService {

    Employee addEmployee(Employee employee);

    List<Employee> findAll();
}
