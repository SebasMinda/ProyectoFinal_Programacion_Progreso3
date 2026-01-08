# Sistema de Reservas de Viajes Aéreos

## Descripción del Proyecto

Este es un sistema de gestión de reservas de viajes aéreos desarrollado en Java que permite administrar clientes, viajes y reservas a través de una interfaz de línea de comandos. El sistema utiliza MySQL como base de datos para el almacenamiento persistente de información.

## Características Principales

### Funcionalidades Implementadas

1. **Gestión de Clientes**
   - Listar todos los clientes registrados
   - Insertar nuevos clientes
   - Actualizar información de clientes
   - Eliminar clientes

2. **Gestión de Viajes**
   - Registrar nuevos viajes con destino, cantidad de asientos y precio
   - Listar todos los viajes disponibles
   - Eliminar viajes existentes

3. **Sistema de Reservas**
   - Reservar asientos en vuelos específicos
   - Asociar reservas a clientes
   - Actualizar disponibilidad de asientos automáticamente

4. **Sistema de Ventas**
   - Estructura básica implementada (en desarrollo)

## Estructura del Proyecto

```
ProyectoFinal_Programacion_Progreso3/
├── src/
│   ├── ProyectoFinal.java           # Clase principal con menú interactivo
│   └── clases/
│       ├── Cliente.java             # Modelo de datos de Cliente
│       ├── Viajes.java              # Modelo de datos de Viajes
│       ├── Ventas.java              # Modelo de datos de Ventas
│       └── Utilidades.java          # Operaciones de BD y lógica de negocio
├── mysql-connector-j-9.5.0/         # Driver de conexión MySQL
└── ProyectoFinal_Programacion_Progreso3.iml
```

## Requisitos del Sistema

### Software Necesario

- **Java Development Kit (JDK)** 8 o superior
- **MySQL Server** 5.7 o superior
- **MySQL Connector/J** 9.5.0 (incluido en el proyecto)
- IDE compatible con Java (IntelliJ IDEA, Eclipse, NetBeans, etc.)

### Dependencias

- MySQL Connector/J 9.5.0 (incluido en `/mysql-connector-j-9.5.0/`)

## Instalación y Configuración

### 1. Configurar la Base de Datos

Crear una base de datos MySQL con las siguientes tablas:

```sql
-- Crear base de datos
CREATE DATABASE sistema_viajes;
USE sistema_viajes;

-- Tabla de clientes
CREATE TABLE clientes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    identificacion VARCHAR(50) UNIQUE NOT NULL,
    asientos_comprados INT DEFAULT 0
);

-- Tabla de viajes
CREATE TABLE viajes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    destino VARCHAR(100) NOT NULL,
    origen VARCHAR(100),
    cantidad INT NOT NULL,
    precio DOUBLE NOT NULL
);

-- Tabla de ventas (opcional, para futuras implementaciones)
CREATE TABLE ventas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente INT,
    id_viaje INT,
    cantidad_asientos INT,
    total DOUBLE,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id),
    FOREIGN KEY (id_viaje) REFERENCES viajes(id)
);
```

### 2. Configurar la Conexión a la Base de Datos

Actualizar las credenciales de conexión en la clase `Utilidades.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/sistema_viajes";
private static final String USER = "tu_usuario";
private static final String PASSWORD = "tu_contraseña";
```

### 3. Compilar y Ejecutar

#### Usando línea de comandos:

```bash
# Compilar
javac -cp ".:mysql-connector-j-9.5.0/mysql-connector-j-9.5.0.jar" src/ProyectoFinal.java src/clases/*.java

# Ejecutar
java -cp ".:mysql-connector-j-9.5.0/mysql-connector-j-9.5.0.jar:src" ProyectoFinal
```

#### Usando un IDE:

1. Abrir el proyecto en tu IDE preferido
2. Agregar `mysql-connector-j-9.5.0.jar` a las librerías del proyecto
3. Ejecutar la clase `ProyectoFinal.java`

## Uso del Sistema

### Menú Principal

Al ejecutar el programa, se presenta el siguiente menú:

```
-----Menu de opciones-----
1. Lista Clientes
2. Registrar viaje
3. Eliminar viaje
4. Lista Viajes
5. Reservar vuelo
6. Lista Ventas
7. Salir
```

### Operaciones Disponibles

#### 1. Lista Clientes
Muestra todos los clientes registrados en la base de datos con su información completa.

#### 2. Registrar Viaje
Permite registrar un nuevo viaje solicitando:
- Nombre del destino
- Cantidad de asientos disponibles
- Precio del viaje

#### 3. Eliminar Viaje
Elimina un viaje existente mediante su ID.

#### 4. Lista Viajes
Muestra todos los viajes registrados con sus detalles (ID, destino, asientos disponibles, precio).

#### 5. Reservar Vuelo
Permite hacer una reserva solicitando:
- ID del cliente
- ID del viaje a reservar
- Cantidad de asientos a reservar

El sistema verifica la disponibilidad y actualiza automáticamente:
- Los asientos disponibles del viaje
- Los asientos comprados por el cliente

#### 6. Lista Ventas
Funcionalidad en desarrollo (no completamente implementada).

#### 7. Salir
Cierra la aplicación.

## Modelos de Datos

### Cliente

```java
public class Cliente {
    private int id;
    private String nombre;
    private String apellido;
    private String identificacion;
    private String email;
    private int asientosComprados;
    
    // Métodos: comprarAsientos(), anularAsientos()
}
```

### Viajes

```java
public class Viajes {
    private int id;
    private String destino;
    private String origen;
    private int cantidad;
    private double precio;
}
```

### Ventas

```java
public class Ventas {
    private int cantidadViajes;
    private float totalVenta;
    private List<String> viajesVendidos;
}
```

## Características Técnicas

### Gestión de Conexiones
- Conexión a MySQL usando JDBC
- Validación de conexión al iniciar
- Manejo de excepciones de base de datos

### Operaciones CRUD
- **Create:** Inserción de clientes y viajes
- **Read:** Consulta de datos de clientes y viajes
- **Update:** Actualización de información de clientes y disponibilidad de viajes
- **Delete:** Eliminación de registros

### Validaciones
- Verificación de disponibilidad de asientos antes de reservar
- Validación de entrada del usuario en el menú
- Prevención de valores negativos en asientos comprados

## Desarrollo Futuro

### Mejoras Pendientes

1. **Sistema de Ventas Completo**
   - Implementar registro completo de ventas en la base de datos
   - Generar reportes de ventas
   - Calcular totales y estadísticas

2. **Validaciones Adicionales**
   - Validación de formato de email
   - Validación de identificación única
   - Manejo de errores más robusto

3. **Mejoras de Usabilidad**
   - Mensajes de error más descriptivos
   - Confirmaciones antes de eliminar registros
   - Búsqueda de clientes y viajes por criterios

4. **Documentación**
   - Agregar JavaDoc a todas las clases y métodos
   - Crear diagramas de clases UML
   - Documentar casos de uso

5. **Pruebas**
   - Implementar pruebas unitarias
   - Pruebas de integración con la base de datos
   - Validación de casos límite

## Contribuciones

Este es un proyecto académico (Proyecto Final de Programación). Para contribuir:

1. Fork el repositorio
2. Crear una rama para tu funcionalidad (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear un Pull Request

## Licencia

Este proyecto es de uso académico.

## Autores

- SebasMinda

## Contacto

Para preguntas o sugerencias sobre el proyecto, crear un issue en el repositorio de GitHub.

---

**Última actualización:** 2026-01-08
