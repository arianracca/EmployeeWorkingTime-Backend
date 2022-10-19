# EMPLOYEES WORKTIME

_________________________________________________________
## PASO A PASO:

- Descomprimir los archivos en una carpeta
- Dar inicio al servidor
- Usar la colección de Postman para probar los Endpoints y sus respuestas

## Referencia rápida:
Javadocs en carpeta: ./javadoc/index.html

En el código encontrará

- Employee (Empleado)
- WorkingType (Tipo de Turno)
- WorkingTime (Jornada Laboral)

## ENDPOINTS:

http://localhost:8080/apiemployees/v1/employees
(GET todos los empleados)
(POST de recurso empleado)

http://localhost:8080/apiemployees/v1/employees/{employeeId}
(GET un recurso de empleado por {employeeId})
(PUT recurso de empleado por {employeeId})
(DELETE escrito pero no implementado por seguridad TODO: implementar roles para permitir borrado seguro)

http://localhost:8080/apiemployees/v1/workingtypes
(GET todos los tipos de turno)
(POST de recurso tipo de turno)

http://localhost:8080/apiemployees/v1/workingtypes/{typeId}
(GET un recurso de tipo de turno por {typeId})
(PUT recurso de tipo de turno por {typeId})
(DELETE escrito pero no implementado por seguridad TODO: implementar roles para permitir borrado seguro)

http://localhost:8080/apiemployees/v1/workingtimes
(GET todos los jornada laboral)
(POST de recurso jornada laboral)

http://localhost:8080/apiemployees/v1/workingtypes/{Id}
(GET un recurso de jornada laboral por {Id})
(PUT recurso de jornada laboral por {Id})
(DELETE escrito pero no implementado por seguridad TODO: implementar roles para permitir borrado seguro)

http://localhost:8080/apiemployees/v1/workingtimes/hours/{employeeId}
(GET una lista de registros de jornada laboral por empleado por {employeeId})

http://localhost:8080/apiemployees/v1/workingtimes/hours/{employeeId}/{workingTypeId}
(GET una lista de registros de jornada laboral por empleado por {employeeId} y por tipo de turno {workingTypeId})

### Base de datos
http://localhost:8080/h2-console/

Driver Class: org.h2.Driver
JDBC URL:jdbc:h2:file:./data/employeesworktime
User Name:sa
Password:

_________________________________________________________
## DISEÑO:
Se realizó la API teniendo en cuenta las siguientes consideraciones:

Se optó por un diseño lo más eficiente posible, que permita la máxima flexibilidad para la organización de tiempos de trabajo por parte de la Empresa y de los Empleados.
También se tuvo en cuenta que para este diseño resultaba importante generar una estructura de tablas con tres entidades.

### Entidades:

- Existen 3 entidades: Employee, WorkingType (incluye toda actividad que se quiera asignar a un empleado), WorkingTime, donde se registran las asignasiones de Tipos de Turnos a los Empleados.
- Los Empleados (Employee) --> se pueden dar de alta mediante una petición POST, ingresando los datos mínimos (nombre, apellido, email (no puede repetirse para otros empleados), teléfono).
- Los Tipos de Turno (WorkingType) --> son las actividades registrables para cada empleado. Es posible dar de alta actividades teniendo en cuenta las particularidades de cada empresa mediante petición POST, ingresando los datos mínimos requeridos (nombre) y como opcional (tiempo mínimo y tiempo máximo). Existen cuatro Tipos de Turno predefinidos (Turno Normal, Turno Extra, Día Libre, Vacaciones) que tienen validaciones específicas a la hora de asignarse a un empleado.
- La Jornada Laboral (WorkingTime) --> es el registro de actividades o Tipos de Turnos asignados ya a un empleado de forma tal que se le da un horario de entrada y un horario de salida (siempre dentro del tiempo mínimo y máximo propio del Tipo de Turno o actividad, y siempre que se cumpla con las demás validaciones específicas atinentes a horas trabajables por día, por semana, cantidad de trabajadores por turno, etc.) Es posible realizar la asignación de Tipo de Turno a un Empleado mediante una petición POST ingresando los datos mínimos (ID del empleado, ID del Tipo de Turno, fecha en formato yyyy-MM-dd, hora de entrada en formato HH-mm, hora de salida en formato HH-mm)

- Turnos y Actividades Predeterminadas Ya cargado en base de datos (Turno Normal, Turno Extra, Día Libre, Vacaciones)
- Empleado Id 1 precargado en base de datos.

### Validación de asignasiones:

- Los Turnos Normales sólo pueden asignarse con un mínimo de 6 y un máximo de 8hs. V
- Los Turnos Extras sólo pueden asignarse con un mínimo de 2 y un máximo de 8hs. V
- No se puede asignar turnos para un empleado en una jornada que tiene cargadas vacaciones o día libre. 
- No se puede asignar a un empleado un turno del mismo tipo para la misma fecha. V
- 

- Ya que no se especificó lo contrario y se puede querer registrar horarios fuera de tiempo (en fechas ya pasadas), es posible realizarlo.

_________________________________________________________
VULNERABILIDADES ENCONTRADAS Y ANALIZADAS
ver detalle de las vulnerabilidades en: [employeesworktime/target/dependency-check-report.html]

Se han encontrado problemas de vulnerabilidad en:
spring-web-5.3.23.jar (Cítica)
No hay alternativas encontradas por el momento.
snakeyaml-1.33.jar (Alta)
No hay alternativas encontradas por el momento.
