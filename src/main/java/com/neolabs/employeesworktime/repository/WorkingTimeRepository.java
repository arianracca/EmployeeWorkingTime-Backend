package com.neolabs.employeesworktime.repository;

import com.neolabs.employeesworktime.entity.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkingTimeRepository extends JpaRepository<WorkingTime, Long> {

//    List<WorkingTime> findWorkingTimeByEmployeeIDAndDate(Long id, LocalDate date);
//    List<WorkingTime> findWorkingTimeByEmployeeIDAndDateBetween(Long id, LocalDate date1, LocalDate date2);
//    List<WorkingTime> findWorkingTimeByDateBetween(LocalDate date1, LocalDate date2);
//    List<WorkingTime> findWorkingTimeByWorkingTypeIDAndDate(Long id, LocalDate date);
//    List<WorkingTime> findWorkingTimeByWorkingTypeIDAndDateBetween(Long id, LocalDate date1, LocalDate date2);
//    List<WorkingTime> findWorkingTimeByEmployeeIdAndWorkingTypeId(Long employeeID, Long workingTypeID);

}
