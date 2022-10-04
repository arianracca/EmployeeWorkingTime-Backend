package com.neolabs.employeesworktime.controller;

import com.neolabs.employeesworktime.entity.Employee;
import com.neolabs.employeesworktime.exception.ResourceNotFoundException;
import com.neolabs.employeesworktime.repository.EmployeeRepository;
import com.neolabs.employeesworktime.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/apiemployees/v1")
@CrossOrigin(origins = "http://localhost:8080")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;


    /**Método para OBTENER TODOS los recursos de Empleado
     * */
    @GetMapping("/employees")
    public List<Employee> listAllEmployees() {
        return employeeService.findAllEmployees();
    }

    /**Método para OBTENER UN ÚNICO recurso de Empleado
     * */
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    /**Método para CREAR el recurso de Empleado
     * */
    @PostMapping("/employees")
    public ResponseEntity<Object> addEmployee(@Valid @RequestBody Employee employee) {
        Employee employeeAdded = employeeService.addEmployee(employee);
        if (employeeAdded != null) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set(HttpHeaders.LOCATION, String.format("/employees/%d", employeeAdded.getId()));
            return new ResponseEntity<>(employeeAdded, responseHeaders, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().body("Error al crear el Empleado.");
    }

    /**Método para ACTUALIZAR (SOLAMENTE) el recurso de Empleado
     * */
    @PutMapping("/employees/{id}")
    public ResponseEntity<Object> updateEmployee(@Valid @PathVariable Long id, @Valid @RequestBody Employee detailsEmployee){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el empleado solicitado"));

        employee.setName(detailsEmployee.getName());
        employee.setLastName(detailsEmployee.getLastName());
        employee.setEmail(detailsEmployee.getEmail());
        employee.setPhoneNumber(detailsEmployee.getPhoneNumber());

        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    /**Método para ELIMINAR el recurso de Empleado
     * */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(@Valid @PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                //Arroja una excepción si el empleado no fue encontrado
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado."));

        employeeRepository.delete(employee); //Elimina el Empleado
        Map<String, Boolean> response = new HashMap<>();
        //Indica si el empleado fue eliminado correctamente
        response.put("Eliminado correctamente", !employeeRepository.findById(id).isPresent());
        return ResponseEntity.ok(response);
    }

}
