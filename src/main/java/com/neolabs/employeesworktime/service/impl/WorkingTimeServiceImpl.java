package com.neolabs.employeesworktime.service.impl;

import com.neolabs.employeesworktime.dto.WorkingTimeDto;
import com.neolabs.employeesworktime.entity.Employee;
import com.neolabs.employeesworktime.entity.WorkingTime;
import com.neolabs.employeesworktime.entity.WorkingType;
import com.neolabs.employeesworktime.repository.EmployeeRepository;
import com.neolabs.employeesworktime.repository.WorkingTimeRepository;
import com.neolabs.employeesworktime.repository.WorkingTypeRepository;
import com.neolabs.employeesworktime.service.WorkingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkingTimeServiceImpl implements WorkingTimeService {

	@Autowired
	WorkingTimeRepository workingTimeRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	WorkingTypeRepository workingTypeRepository;

	/**
	 * Método que crea la Jornada Laboral asociada al Empleado
	 * mediante el uso de DTO
	 */
	public WorkingTime addWorkingTime(WorkingTimeDto workingTimeDTO) {
		
		//Aca voy a buscar el tipo y el empleado a la base de datos con el id que agarre en postman
		Optional<Employee> employee = employeeRepository.findById(workingTimeDTO.getEmployee());
		Optional<WorkingType> type = workingTypeRepository.findById(workingTimeDTO.getType());
		
		//si no existen retorno null 
		if ((!employee.isPresent()) || (!type.isPresent())
			&& checkWorkingTimeTypeValidDay
				(workingTimeDTO, workingTimeRepository))
			return null;
		// seteo lo que vino del DTO a la clase working time
		WorkingTime workingTime = new WorkingTime();
		workingTime.setEmployee(employee.get());
		workingTime.setType(type.get());
		workingTime.setStartTime(workingTimeDTO.getStartTime());
		workingTime.setEndTime(workingTimeDTO.getEndTime());
		workingTime.setDate(workingTimeDTO.getDate());
		workingTime.setHours(workingTimeDTO.getStartTime(), workingTimeDTO.getEndTime());
		System.out.println(workingTimeDTO.getHours());
		return workingTimeRepository.save(workingTime);
	}


	// TODAVIA NO IMPLEMENTADO (SE OPERA DIRECTO DEL CONTROLLER) TODO IMPLEMENTAR
	// DESDE SERVICE
	public WorkingTime updateWorkingTime(WorkingTime workingTime) {
		return null;
	}

	public Optional<WorkingTime> getWorkingTimeById(Long id) {
		return workingTimeRepository.findById(id);
	}

	/**Enlace entre controller y repositorio para obtener Todas las Jornadas Laborales
	 * para un Empleado
	 * @param employeeId Id del empleado
	 * */
	public List<WorkingTime> getWorkingTimeByEmployeeId(Long employeeId) {
		return workingTimeRepository.findWorkingTimeByEmployeeId(employeeId);
	}

	/**Enlace entre controller y repositorio para obtener filtrando por Turno y Empleado
	 * @param employeeId Id del empleado
	 * @param workingTypeId Id tipo de turno
	 * */
	public List<WorkingTime> getWorkingTimeByEmployeeIdAndWorkingType(Long employeeId, Long workingTypeId) {
		return workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingType(employeeId, workingTypeId);
	}

	/**
	 * Método para chequear que la jornada laboral que se asigna se ajusta a los
	 * criterios del Tipo de Turno y las validaciones de cada caso predefinido
	 * Turno Normal, Turno Extra, Dia libre y Vacaciones
	 * en funcion del período de tiempo DIA.
	 */
	public boolean checkWorkingTimeTypeValidDay(WorkingTimeDto workingTimeDTO, WorkingTimeRepository workingTimeRepository) {
		//Traigo los datos de empleado y tipo de cada lista para poder operar con más facilidad
		Optional<Employee> employee = employeeRepository.findById(workingTimeDTO.getEmployee());
		Optional<WorkingType> type = workingTypeRepository.findById(workingTimeDTO.getType());

		if (workingTimeDTO.getType() == 1 //Valida en caso de turno normal
				&& workingTimeDTO.getHours() >= type.get().getMinHours()
				&& workingTimeDTO.getHours() <= type.get().getMaxHours()
				&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
				(workingTimeDTO.getEmployee(), workingTimeDTO.getType(), workingTimeDTO.getDate()).isEmpty()
				&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
				(workingTimeDTO.getEmployee(), 3L, workingTimeDTO.getDate()).isEmpty()
				&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
				(workingTimeDTO.getEmployee(), 4L, workingTimeDTO.getDate()).isEmpty()
				&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
				(workingTimeDTO.getEmployee(), 1L, workingTimeDTO.getDate()).isEmpty()
		)	return true;
			if (workingTimeDTO.getType() == 2 //Valida en caso de turno extra
					&& workingTimeDTO.getHours() >= type.get().getMinHours()
					&& workingTimeDTO.getHours() <= type.get().getMaxHours()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), workingTimeDTO.getType(), workingTimeDTO.getDate()).isEmpty()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), 3L, workingTimeDTO.getDate()).isEmpty()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), 4L, workingTimeDTO.getDate()).isEmpty()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), 2L, workingTimeDTO.getDate()).isEmpty()
			)	return true;
			if (workingTimeDTO.getType() == 3 //Valida en caso de dia libre
					&& workingTimeDTO.getHours() >= type.get().getMinHours()
					&& workingTimeDTO.getHours() <= type.get().getMaxHours()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), workingTimeDTO.getType(), workingTimeDTO.getDate()).isEmpty()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), 3L, workingTimeDTO.getDate()).isEmpty()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), 4L, workingTimeDTO.getDate()).isEmpty()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), 2L, workingTimeDTO.getDate()).isEmpty()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), 1L, workingTimeDTO.getDate()).isEmpty()
			)	return true;
			if (workingTimeDTO.getType() == 4 //Valida en caso de vacaciones
					&& workingTimeDTO.getHours() >= type.get().getMinHours()
					&& workingTimeDTO.getHours() <= type.get().getMaxHours()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), workingTimeDTO.getType(), workingTimeDTO.getDate()).isEmpty()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), 3L, workingTimeDTO.getDate()).isEmpty()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), 4L, workingTimeDTO.getDate()).isEmpty()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), 2L, workingTimeDTO.getDate()).isEmpty()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), 1L, workingTimeDTO.getDate()).isEmpty()
			)	return true;

		return false;
	}
}


//	public boolean checkWorkingTimeMinMaxHours(WorkingTimeDto workingTimeDTO) {
//		// Deja fuera del chequeo a las vacaciones y los días libres
//		if (workingTimeDTO.getType() != 3L && workingTimeDTO.getType() != 4L) {
//			return workingTimeDTO.getHours() > workingTimeDTO.getType().getMinHours()
//					&& workingTimeDTO.getHours() < workingTimeDTO.getType().getMaxHours();
//		}
//		return true;
//	}

	/**
	 * Método para chequear la disponibilidad del Empleado a la hora de asignar la
	 * jornada laboral
	 */
//    public boolean checkEmployeeAvailability(WorkingTime workingTimeDTO, WorkingTimeRepository workingTimeRepository) {
//        workingTimeRepository.findWorkingTimeByEmployeeIdAndDate(workingTime);
//
//        return false;
//    }


	/**Método para obtener sumadas la cantidad de horas de trabajo que tiene cada empleado
	 * por Tipo de Turno
	 *
	 * */
//    public List<EmployeeHoursByWorkingType> getEmployeeHoursByWorkingType() {
//        // Obtengo todos los employees
//        List<Employee> employees = this.employeeRepository.findAll();
//        // Obtengo todos los tipos de workingTime
//        List<WorkingType> workingTypes = this.workingTypeRepository.findAll();
//        // Creo una lista para devolver la respuesta
//        List<EmployeeHoursByWorkingType> response = new ArrayList<>();
//
//        // Este algoritmo es muy costoso pero funciona
//        for (int i = 0; i < employees.size(); i++) {
//            Employee employee = employees.get(i);
//            ArrayList<HoursByWorkingTime> hoursByWorkingTime = new ArrayList<>();
//
//            // Encontrar las horas cargadas por cada workingType de workingTime
//            for (int j = 0; j < workingTypes.size(); j++) {
//                WorkingType workingType = workingTypes.get(j);
//
//                // Obtengo todas las workingTime del employee de cada workingType
//                List<WorkingTime> workingTime = this.workingTimeRepository. findWorkingTimeByEmployeeIdAndWorkingType(employee.getId(), workingType.getId());
//
//                Long totalHours = 0L;
//
//                // Sumo todas las horas de trabajo en el dia
//                for (int k = 0; k < workingTime.size(); k++) {
//                    WorkingTime workingTime = workingTime.get(k);
//                    totalHours += ChronoUnit.HOURS.between(workingTime.getStartTime(), workingTime.getEndTime());
//                }
//
//                // Agrego las horas por el workingType
//                hoursByWorkingTime.add(new HoursByWorkingTime(workingType.getName(), totalHours));
//            }
//
//            // Agrego las horas por workingType de workingTime a la respuesta
//            response.add(new EmployeeHoursByWorkingType(employee, hoursByWorkingTime));
//        }
//
//        return response;
//    }







