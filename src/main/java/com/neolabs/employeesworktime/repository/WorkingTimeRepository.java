package com.neolabs.employeesworktime.repository;

import com.neolabs.employeesworktime.entity.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingTimeRepository extends JpaRepository<WorkingTime, Long> {

//    <WorkingTimeByEmployee> List<WorkingTimeByEmployee> findByEmployee(Long employeeId);

}
