package com.neolabs.employeesworktime.controller;

import com.neolabs.employeesworktime.entity.WorkingTime;
import com.neolabs.employeesworktime.exception.ResourceNotFoundException;
import com.neolabs.employeesworktime.repository.WorkingTimeRepository;
import com.neolabs.employeesworktime.service.WorkingTimeService;
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
public class WorkingTimeController {

    @Autowired
    WorkingTimeRepository workingTimeRepository;

    @Autowired
    WorkingTimeService workingTimeService;

    /**Método para OBTENER TODOS los recursos de Jornada Laboral
     * */
    @GetMapping("/workingtimes")
    public List<WorkingTime> listAllWorkingTimes() {
        return workingTimeRepository.findAll();
    }

    /**Método para OBTENER UN ÚNICO recurso de Jornada Laboral
     * */
    @GetMapping("/workingtimes/{id}")
    public ResponseEntity<WorkingTime> getWorkingTime(@PathVariable("id") Long id) {
        Optional<WorkingTime> workingTime = workingTimeRepository.findById(id);
        return workingTime.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }

    /**Método para CREAR el recurso de Jornada Laboral
     * */
    @PostMapping("/workingtimes")
    public ResponseEntity<Object> addWorkingTime(@Valid @RequestBody WorkingTime workingTime) {
        WorkingTime workingTimeAdded = workingTimeService.addWorkingTime(workingTime);
        if (workingTimeAdded != null) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set(HttpHeaders.LOCATION, String.format("/workingtimes/%d", workingTimeAdded.getId()));
            return new ResponseEntity<>(workingTimeAdded, responseHeaders, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().body("Error al crear la Jornada Laboral");
    }

    /**Método para ACTUALIZAR (SOLAMENTE) la Jornada Laboral ya existente
     * */
    @PutMapping("/workingtimes/{id}")
    public ResponseEntity<Object> updateWorkingTime(@Valid @PathVariable Long id, @Valid @RequestBody WorkingTime detailsWorkingTime){
        WorkingTime workingTime = workingTimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el registro solicitado"));

        workingTime.setType(detailsWorkingTime.getType());
        workingTime.setStartTime(detailsWorkingTime.getStartTime());
        workingTime.setEndTime(detailsWorkingTime.getEndTime());

        WorkingTime updatedWorkingTime = workingTimeRepository.save(workingTime);
        return ResponseEntity.ok(updatedWorkingTime);
    }

    /**Método para ELIMINAR el recurso de Tipo de Turno
     * */
    @DeleteMapping("/workingtimes/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteWorkingTime(@Valid @PathVariable Long id){
        WorkingTime workingTime = workingTimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el registro solicitado"));

        workingTimeRepository.delete(workingTime);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Eliminado correctamente", !workingTimeRepository.findById(id).isPresent());
        return ResponseEntity.ok(response);
    }

}
