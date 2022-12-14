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

		//si no existen o no cumple los chequeos retorno null
		if (!employee.isPresent() || !type.isPresent())
		{ return null;
		} else if (checkWorkingTimeTypeValidDay(workingTimeDTO, workingTimeRepository)) {
			// seteo lo que vino del DTO a la clase working time
			WorkingTime workingTime = new WorkingTime();
			workingTime.setEmployee(employee.get());
			workingTime.setType(type.get());
			workingTime.setStartTime(workingTimeDTO.getStartTime());
			workingTime.setEndTime(workingTimeDTO.getEndTime());
			workingTime.setDate(workingTimeDTO.getDate());
			workingTime.setHours(workingTimeDTO.getStartTime(), workingTimeDTO.getEndTime());
			System.out.println(workingTime.getHours());
			System.out.println(workingTimeDTO.getHours());
			return workingTimeRepository.save(workingTime);
		} else { return null;
		}
	}


	// TODAVIA NO IMPLEMENTADO (SE OPERA DIRECTO DEL CONTROLLER) TODO IMPLEMENTAR DESDE SERVICE
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
	 * TODO IMPLEMENTAR CHEQUEO DE HORAS POR DIA Y POR SEMANA
	 */
	public boolean checkWorkingTimeTypeValidDay(WorkingTimeDto workingTimeDTO, WorkingTimeRepository workingTimeRepository) {
		//Traigo los datos de empleado y tipo de cada lista para poder operar con más facilidad
		Optional<Employee> employee = employeeRepository.findById(workingTimeDTO.getEmployee());
		Optional<WorkingType> type = workingTypeRepository.findById(workingTimeDTO.getType());

		if (workingTimeDTO.getType() == 1 //Valida en caso de turno normal
				&& workingTimeDTO.getHours() >= type.get().getMinHours() //Horas no bajen del minimo por tipo
				&& workingTimeDTO.getHours() <= type.get().getMaxHours() //Horas no superen el máximo por tipo
				//Chequea no asignar más de un turno por tipo para un empleado el mismo dia
				&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
				(workingTimeDTO.getEmployee(), workingTimeDTO.getType(), workingTimeDTO.getDate()).isEmpty()
				//Chequea no asignar turno si ya hay asignado un día libre ese día para ese empleado
				&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
				(workingTimeDTO.getEmployee(), 3L, workingTimeDTO.getDate()).isEmpty()
				//Chequea no asignar turno si ya hay asignadas vacaciones para ese empleado ese dia
				&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
				(workingTimeDTO.getEmployee(), 4L, workingTimeDTO.getDate()).isEmpty()
		) { return true;
		} else if (workingTimeDTO.getType() == 2 //Valida en caso de turno extra
					&& workingTimeDTO.getHours() >= type.get().getMinHours()
					&& workingTimeDTO.getHours() <= type.get().getMaxHours()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), workingTimeDTO.getType(), workingTimeDTO.getDate()).isEmpty()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), 3L, workingTimeDTO.getDate()).isEmpty()
					&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
					(workingTimeDTO.getEmployee(), 4L, workingTimeDTO.getDate()).isEmpty()
		) { return true;
		} else if (workingTimeDTO.getType() == 3 //Valida en caso de dia libre
				&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
				(workingTimeDTO.getEmployee(), workingTimeDTO.getType(), workingTimeDTO.getDate()).isEmpty()
				&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
				(workingTimeDTO.getEmployee(), 4L, workingTimeDTO.getDate()).isEmpty()
				&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
				(workingTimeDTO.getEmployee(), 2L, workingTimeDTO.getDate()).isEmpty()
				&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
				(workingTimeDTO.getEmployee(), 1L, workingTimeDTO.getDate()).isEmpty()
		) { return true;
		} else if (workingTimeDTO.getType() == 4 //Valida en caso de vacaciones
						&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
						(workingTimeDTO.getEmployee(), workingTimeDTO.getType(), workingTimeDTO.getDate()).isEmpty()
						&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
						(workingTimeDTO.getEmployee(), 3L, workingTimeDTO.getDate()).isEmpty()
						&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
						(workingTimeDTO.getEmployee(), 2L, workingTimeDTO.getDate()).isEmpty()
						&& workingTimeRepository.findWorkingTimeByEmployeeIdAndWorkingTypeAndDate
						(workingTimeDTO.getEmployee(), 1L, workingTimeDTO.getDate()).isEmpty()
				) {return true;
			} else return false;
	}

}







