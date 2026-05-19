# Proyecto 3er Trimestre — Acceso a Datos

Proyecto de gestión de bases de datos y persistencia de objetos desarrollado en Java, dividido en dos unidades didácticas.

---

## UD6 · Gestión de bases de datos — Eventos y Asistentes

Aplicación para la gestión de eventos, asistentes e inscripciones usando **JDBC** y **MySQL**.

### Estructura

```
├── modelos/
│   ├── Evento.java
│   └── Asistente.java
├── dao/
│   ├── EventoDAO.java
│   └── AsistenteDAO.java
└── Main.java
```

### Modelo de datos

**Evento:** `id`, `nombre`, `ubicacion`, `fecha`, `precio`

**Asistente:** `id`, `nombre`, `email`, `edad`

**Inscripcion:** `asistente_id`, `evento_id`, `fecha_inscripcion`

### Operaciones implementadas

**EventoDAO**
- Inserción, actualización y borrado de un evento
- Obtener todos los eventos con su número total de asistentes
- Obtener todos los asistentes de un evento por su ID
- Obtener eventos con más de 2 asistentes
- Obtener los 3 eventos con más ingresos
- Obtener el evento más caro de una ubicación dada

**AsistenteDAO**
- Inserción, actualización y borrado de un asistente
- Inscribir a un asistente en un evento con fecha
- Eliminar la inscripción de un asistente en un evento
- Obtener todos los asistentes con su gasto total
- Obtener la edad media de los asistentes
- Obtener asistentes sin ninguna inscripción

### Scripts SQL

Los scripts de creación de tablas e inserción de datos de prueba se encuentran en `sql/ud6_script.sql`.

---

## UD7 · Persistencia de objetos — Proyectos y Desarrolladores

Aplicación para la gestión de proyectos y desarrolladores usando **JPA** con **ObjectDB**.

### Estructura

```
├── modelos/
│   ├── Proyecto.java
│   └── Desarrollador.java
├── dao/
│   ├── ProyectoDAO.java
│   └── DesarrolladorDAO.java
├── META-INF/
│   └── persistence.xml
└── Main.java
```

### Modelo de datos

**Proyecto:** `id`, `nombre`, `presupuesto`, `lenguajePrincipal`, `desarrolladores`

**Desarrollador:** `id`, `nombre`, `anyosExperiencia`, `salario`, `proyectos`

Relación **ManyToMany** bidireccional entre `Proyecto` y `Desarrollador`.

### Operaciones implementadas

**ProyectoDAO**
- Inserción, actualización y borrado de un proyecto
- Obtener el número de desarrolladores de cada proyecto
- Obtener todos los desarrolladores de un proyecto por su ID
- Obtener proyectos con más de 5 desarrolladores
- Obtener los 3 proyectos con el presupuesto más alto
- Obtener el proyecto con el presupuesto más bajo de un lenguaje dado

**DesarrolladorDAO**
- Inserción, actualización y borrado de un desarrollador
- Asignar un desarrollador a un proyecto
- Eliminar la asignación de un desarrollador en un proyecto
- Obtener todos los proyectos de un desarrollador por su ID
- Obtener la media de años de experiencia
- Obtener desarrolladores sin ningún proyecto asignado

### Datos de prueba

Los datos de prueba (10 proyectos, 10 desarrolladores y sus asignaciones) se insertan desde la clase `Main.java` usando los métodos `insertarProyecto`, `insertarDesarrollador` y `asignarDesarrollador`.

> ⚠️ Los IDs en ObjectDB se asignan de forma secuencial global. En los datos de prueba los proyectos obtienen los IDs `1-10` y los desarrolladores los IDs `11-20`.

---

## Tecnologías

| Unidad | Tecnología |
|--------|-----------|
| UD6 | Java, JDBC, MySQL |
| UD7 | Java, JPA, ObjectDB |

## Requisitos

- Java 17+
- Maven o gestión de dependencias equivalente
- MySQL (para UD6)
- ObjectDB (para UD7)
