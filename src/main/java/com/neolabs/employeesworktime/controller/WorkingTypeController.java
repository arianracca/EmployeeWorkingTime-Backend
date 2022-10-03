package com.neolabs.employeesworktime.controller;

import com.neolabs.employeesworktime.entity.WorkingType;
import com.neolabs.employeesworktime.exception.ResourceNotFoundException;
import com.neolabs.employeesworktime.repository.WorkingTypeRepository;
import com.neolabs.employeesworktime.service.WorkingTypeService;
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
public class WorkingTypeController {

    @Autowired
    WorkingTypeRepository workingTypeRepository;

    @Autowired
    private WorkingTypeService workingTypeService;

    /**Método para OBTENER TODOS los recursos de Tipos de Turnos
     * */
    @GetMapping("/workingtypes")
    public List<WorkingType> listAllWorkingTypes() {
        return workingTypeRepository.findAll();
    }

    /**Método para OBTENER UN ÚNICO recurso de Tipo de Turno
     * */
    @GetMapping("/workingtypes/{id}")
    public ResponseEntity<WorkingType> getWorkingType(@PathVariable("id") Long id) {
        Optional<WorkingType> workingType = workingTypeRepository.findById(id);
        return workingType.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }

    /**Método para CREAR el recurso de Tipo de Turno
     * */
    @PostMapping("/workingtypes")
    public ResponseEntity<Object> addWorkingType(@Valid @RequestBody WorkingType workingType) {
        WorkingType workingTypeAdded = workingTypeService.addWorkingType(workingType);
        if (workingTypeAdded != null) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set(HttpHeaders.LOCATION, String.format("/workingtypes/%d", workingTypeAdded.getWorkingTypeID()));
            return new ResponseEntity<>(workingTypeAdded, responseHeaders, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().body("Error al crear el Tipo de Turno");
    }

    /**Método para ACTUALIZAR (SOLAMENTE) el Tipo de Turno ya existente
     * */
    @PutMapping("/workingtypes/{id}")
    public ResponseEntity<Object> updateWorkingType(@Valid @PathVariable Long id, @Valid @RequestBody WorkingType detailsWorkingType) {
        // Chequea mediante el service que sea correcto hacer la actualización
        if (workingTypeService.updateWorkingType(detailsWorkingType) != null) {
            WorkingType workingType = workingTypeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Error al crear el Tipo de Turno"));

            workingType.setType(detailsWorkingType.getType());
            workingType.setMinHours(detailsWorkingType.getMinHours());
            workingType.setMaxHours(detailsWorkingType.getMaxHours());

            WorkingType updatedWorkingType = workingTypeRepository.save(workingType);
            return ResponseEntity.ok(updatedWorkingType);
        }
        return ResponseEntity.badRequest().body("Error al crear el Tipo de Turno");
    }

    /**Método para ELIMINAR el recurso de Tipo de Turno
     * */
    @DeleteMapping("/workingtypes/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteWorkingType(@Valid @PathVariable Long id){
        WorkingType workingType = workingTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el Tipo de Turno solicitado"));

        workingTypeRepository.delete(workingType);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Eliminado correctamente", !workingTypeRepository.findById(id).isPresent());
        return ResponseEntity.ok(response);
    }
}
