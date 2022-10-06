package com.neolabs.employeesworktime.repository;

import com.neolabs.employeesworktime.entity.WorkingTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkingTimeRepository extends JpaRepository<WorkingTime, Long> {

    /**
     * Query para obtener los registros de TODOS los turnos cargados por EMPLEADO
     * @param employeeId del empleado
     */
    @Query(value = "SELECT * FROM EMPLOYEESWORKINGTIMES WHERE EMPLOYEEID = :employeeId ", nativeQuery = true)
    List<WorkingTime> findWorkingTimeByEmployeeId(Long employeeId);


    /**
     * Query para obtener los registros definiendo TIPO DE TURNO y EMPLEADO
     * @param employeeId    del empleado
     * @param workingTypeId tipo de turno
     */
    @Query(value = "SELECT * FROM EMPLOYEESWORKINGTIMES WHERE EMPLOYEEID = :employeeId AND WORKING_TYPEID = :workingTypeId", nativeQuery = true)
    List<WorkingTime> findWorkingTimeByEmployeeIdAndWorkingType(Long employeeId, Long workingTypeId);

    /**
     * Query para obtener los registros de turnos cargados por empleado y fecha
     * @param employeeId      del empleado
     * @param workingTypeId   tipo de turno
     * @param workingTimeDate fecha de la jornada laboral
     */
    //TODO implementar en el controller
    @Query(value = "SELECT * FROM EMPLOYEESWORKINGTIMES WHERE EMPLOYEEID = :employeeId AND WORKING_TYPEID = :workingTypeId AND DATE = :workingTimeDate", nativeQuery = true)
    List<WorkingTime> findWorkingTimeByEmployeeIdAndWorkingTypeAndDate(Long employeeId, Long workingTypeId, LocalDate workingTimeDate);

    /**
     * Query por empleado y por fecha
     * @param employeeId del empleado
     * @param workingTimeDate fecha
     */
    @Query(value = "SELECT * FROM EMPLOYEESWORKINGTIMES WHERE EMPLOYEEID = :employeeId AND DATE = :workingTimeDate", nativeQuery = true)
    List<WorkingTime> findWorkingTimeByEmployeeAndDate(Long employeeId, LocalDate workingTimeDate);


    // TODO Una query para consultar una semana entera permitiría hacer fácil la validación por semana
}