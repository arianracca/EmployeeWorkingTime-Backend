package com.neolabs.employeesworktime.repository;

import com.neolabs.employeesworktime.entity.WorkingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingTypeRepository extends JpaRepository<WorkingType, Long> {

    boolean existsByType(String type);
}
