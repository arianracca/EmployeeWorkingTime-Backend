package com.neolabs.employeesworktime.repository;

import com.neolabs.employeesworktime.entity.WorkingTime;
import com.neolabs.employeesworktime.entity.WorkingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface WorkingTypeRepository extends JpaRepository<WorkingType, Long> {

    boolean existsByType(String type);
    WorkingType findWorkingTypeByType(String type);


}
