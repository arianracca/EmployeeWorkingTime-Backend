package com.neolabs.employeesworktime.model;

import com.neolabs.employeesworktime.entity.WorkingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**Clase en la que ya creé los tipos de turno predefinidos
 * Estos son los tipos de turno que requieren chequeos propios en el programa
 * aquellos personalizados no tendrán más chequeo que el de horas máximas y mínimas al asignarse
 * */
@Component
public class WorkingTypePred {

    WorkingType turnoNormal = new WorkingType(3000L, "TURNO_NORMAL", 6L, 8L);
    WorkingType turnoExtra = new WorkingType(3001L, "TURNO_EXTRA", 2L, 6L);
    WorkingType diaLibre = new WorkingType(3002L, "DIA_LIBRE", 0L, 0L);
    WorkingType vacaciones = new WorkingType(3003L, "VACACIONES", 0L, 0L);



}
