package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * Utilidades para manejar la conexión con la base de datos y operaciones CRUD simples.
 * <p>
 * Explicación simple:
 * - Provee método para obtener la conexión a la base de datos.
 * - Métodos para insertar/obtener clientes, viajes y ventas.
 * - Métodos de ayuda para disponibilidad de asientos y reiniciar datos de clientes.
 */
public class Utilidades {
    /**
     * Crea y devuelve una conexión a la base de datos MySQL usando los datos hardcodeados.
     */
    public Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/cliente";
        String user = "root";
        String passwd = "sasa";

        try {
            return DriverManager.getConnection(url, user, passwd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Inserta cliente

    /**
     * Inserta un cliente en la tabla `cliente`.
     * - Asigna id al objeto cliente si la inserción fue exitosa (buscando por identificación).
     * - No lanza excepciones hacia el llamador: sólo muestra la traza en caso de error.
     */
    public void insetarDatos(Cliente cliente, Connection conn) {
        String sql = "INSERT INTO cliente (nombre, apellido, correo, identificacion, asientosComprados, asientosClaseEconomica, asientosClasePremium, precio) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getIdentificacion());
            int totalAsientos = cliente.getAsientosComprados();
            if (totalAsientos <= 0)
                totalAsientos = cliente.getAsientosClaseEconomica() + cliente.getAsientosClasePremium();
            ps.setInt(5, totalAsientos);
            ps.setInt(6, cliente.getAsientosClaseEconomica());
            ps.setInt(7, cliente.getAsientosClasePremium());
            ps.setDouble(8, cliente.getPrecio());

            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                System.out.println("El Cliente se insertó correctamente.");

                // Recuperar ID para que las ventas se asocien bien
                try (PreparedStatement psId = conn.prepareStatement("SELECT idcliente FROM cliente WHERE identificacion = ? ORDER BY idcliente DESC LIMIT 1")) {
                    psId.setString(1, cliente.getIdentificacion());
                    try (ResultSet rs = psId.executeQuery()) {
                        if (rs.next()) {
                            cliente.setId(rs.getInt("idcliente"));
                        }
                    }
                } catch (Exception exId) {
                    exId.printStackTrace();
                }

            } else {
                System.out.println("El Cliente no se insertó.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Resetea la tabla `cliente` (trunca y reinicia el AUTO_INCREMENT).
     * Usar con cuidado: borra todos los datos.
     */
    public void resetearClientes(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            // Desactivamos temporalmente las llaves foráneas para permitir truncar si hay relaciones
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stmt.executeUpdate("TRUNCATE TABLE cliente");
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
            System.out.println("Tabla de clientes reseteada (ID reiniciado).");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Listar clientes

    /**
     * Muestra por consola los clientes registrados (id, nombre, apellido, correo, identificación).
     */
    public void obtenerDatos(Connection conn) {
        String sql = "SELECT idcliente, nombre, apellido, correo, identificacion FROM cliente";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cli = new Cliente(rs.getInt("idcliente"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("correo"), rs.getString("identificacion"));
                System.out.println(cli);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Inserta viaje

    /**
     * Inserta un viaje en la tabla `viajes`.
     * Inicializa `asientosvendidos` en 0.
     */
    public void insetarDatosViaje(Viajes viaje, Connection conn) {
        String sql = "INSERT INTO viajes (origen, destino, cantidadTotal, asientosClasePremium, asientosClaseEconomica, precioEconomica, precioPremium, asientosvendidos,ganancias) VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, viaje.getOrigen());
            ps.setString(2, viaje.getDestino());
            ps.setInt(3, viaje.getCantidadTotal());
            ps.setInt(4, viaje.getAsientosClasePremium());
            ps.setInt(5, viaje.getAsientosClaseEconomica());
            ps.setDouble(6, viaje.getPrecioEconomica());
            ps.setDouble(7, viaje.getPrecioPremium());
            ps.setInt(8, 0);
            ps.setDouble(9, viaje.getGanancias());
            int resultado = ps.executeUpdate();
            if (resultado > 0) System.out.println("El viaje se ha insertado correctamente..");
            else System.out.println("El viaje no se inserto..");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Listar viajes (para admin)

    /**
     * Muestra todos los viajes con sus ganancias (para admin).
     */
    public void obtenerDatosViaje(Connection conn) {
        String sql = "SELECT idviaje, origen, destino, cantidadTotal, asientosClasePremium, asientosClaseEconomica, precioEconomica, precioPremium, ganancias FROM viajes";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Viajes vjs = new Viajes(rs.getInt("idviaje"), rs.getString("origen"), rs.getString("destino"), rs.getInt("cantidadTotal"), rs.getInt("asientosClaseEconomica"), rs.getInt("asientosClasePremium"), rs.getDouble("precioEconomica"), rs.getDouble("precioPremium"), rs.getDouble("ganancias"));
                System.out.println(vjs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Listar viajes para cliente (sin mostrar ganancias)

    /**
     * Muestra los viajes sin las ganancias (para clientes normales).
     */
    public void obtenerDatosViajeCliente(Connection conn) {
        String sql = "SELECT idviaje, origen, destino, cantidadTotal, asientosClasePremium, asientosClaseEconomica, precioEconomica, precioPremium FROM viajes";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Viajes vjs1 = new Viajes(rs.getInt("idviaje"), rs.getString("origen"), rs.getString("destino"), rs.getInt("cantidadTotal"), rs.getInt("asientosClaseEconomica"), rs.getInt("asientosClasePremium"), rs.getDouble("precioEconomica"), rs.getDouble("precioPremium"));
                System.out.println(vjs1.toString2());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void eliminaDatosViaje(int id, Connection conn) {
        try (PreparedStatement psDelete = conn.prepareStatement("DELETE FROM viajes WHERE idviaje = ?")) {
            psDelete.setInt(1, id);
            int resultado = psDelete.executeUpdate();
            if (resultado > 0) {
                // Reordenar id
                try (PreparedStatement psUpdate = conn.prepareStatement("UPDATE viajes SET idviaje = idviaje - 1 WHERE idviaje > ?")) {
                    psUpdate.setInt(1, id);
                    psUpdate.executeUpdate();
                }
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate("ALTER TABLE viajes AUTO_INCREMENT = 1");
                }
                System.out.println("El viaje se ha eliminado y los índices han sido reordenados.");
            } else {
                System.out.println("El viaje no se ha eliminado (no existe ese ID).");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // Reserva combinada: cantidadEconomica y cantidadPremium pueden ser 0

    /**
     * Intenta reservar asientos para un viaje, actualiza la tabla viajes (asientos y ganancias)
     * y registra una fila en la tabla ventas.
     * <p>
     * Nota: este método usa transacción simple para evitar inconsistencias.
     */
    public void insertarDatosVenta(int idViaje, int cantidadEconomica, int cantidadPremium, Cliente cliente, Connection conn) {
        // Validaciones simples
        String sqlCheck = "SELECT asientosClaseEconomica, asientosClasePremium, precioEconomica, precioPremium FROM viajes WHERE idviaje = ? FOR UPDATE";
        String sqlUpdate = "UPDATE viajes SET cantidadTotal = cantidadTotal - ?, asientosClaseEconomica = asientosClaseEconomica - ?, asientosClasePremium = asientosClasePremium - ?, asientosvendidos = asientosvendidos + ?, ganancias = ganancias + ? WHERE idviaje = ?";

        try {
            conn.setAutoCommit(false);

            // Verificar disponibilidad
            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {
                psCheck.setInt(1, idViaje);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("No existe el viaje con id " + idViaje);
                        conn.rollback();
                        conn.setAutoCommit(true);
                        return;
                    }

                    int disponiblesEco = rs.getInt("asientosClaseEconomica");
                    int disponiblesPrem = rs.getInt("asientosClasePremium");
                    double precioEco = rs.getDouble("precioEconomica");
                    double precioPrem = rs.getDouble("precioPremium");

                    if (disponiblesEco < cantidadEconomica) {
                        System.out.println("No hay suficientes asientos económicos. Disponibles: " + disponiblesEco);
                        conn.rollback();
                        conn.setAutoCommit(true);
                        return;
                    }
                    if (disponiblesPrem < cantidadPremium) {
                        System.out.println("No hay suficientes asientos premium. Disponibles: " + disponiblesPrem);
                        conn.rollback();
                        conn.setAutoCommit(true);
                        return;
                    }

                    double totalVenta = precioEco * cantidadEconomica + precioPrem * cantidadPremium;

                    // Actualizar viaje
                    try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                        psUpdate.setInt(1, cantidadEconomica + cantidadPremium);
                        psUpdate.setInt(2, cantidadEconomica);
                        psUpdate.setInt(3, cantidadPremium);
                        psUpdate.setInt(4, cantidadEconomica + cantidadPremium);
                        psUpdate.setDouble(5, totalVenta);
                        psUpdate.setInt(6, idViaje);
                        int updated = psUpdate.executeUpdate();
                        if (updated <= 0) {
                            System.out.println("No se pudo actualizar los asientos del viaje.");
                            conn.rollback();
                            conn.setAutoCommit(true);
                            return;
                        }
                    }

                    // Registrar venta
                    int idCliente = (cliente != null) ? cliente.getId() : 0;
                    Ventas venta = new Ventas(idCliente, idViaje, cantidadEconomica + cantidadPremium, cantidadEconomica, cantidadPremium, totalVenta);
                    insertarVenta(venta, conn);

                    conn.commit();
                    conn.setAutoCommit(true);
                }
            }

        } catch (Exception ex) {
        }
    }

    // obtener precio por clase

    /**
     * Devuelve el precio unitario según la clase (1 = economica, 2 = premium) para un viaje.
     */
    public double obtenerPrecioPorClase(int idViaje, int tipoAsiento, Connection conn) {
        String sql = "SELECT precioEconomica, precioPremium FROM viajes WHERE idviaje = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idViaje);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return (tipoAsiento == 1) ? rs.getDouble("precioEconomica") : rs.getDouble("precioPremium");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    // Inserta una venta en la tabla 'ventas' de forma simple

    /**
     * Inserta un registro en la tabla `ventas` con los datos del cliente y los asientos comprados.
     * Formatea la columna `cEco/cPrem` como "eco / prem".
     */
    public void insertarVenta(Ventas venta, Connection conn) {
        String sqlBuscarCliente = "SELECT nombre, apellido, identificacion FROM cliente WHERE idcliente = ?";
        // Se asume el orden: idcliente, idvuelo, nombre, apellido, identificacion, cantidadAsientos, asientos (string), total, fecha
        String sqlInsertarVenta = "INSERT INTO ventas (idcliente, idvuelo, nombre, apellido, identificacion, cantidadAsientos, `cEco/cPrem`, total, fecha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // 1. Obtener datos extra del cliente
            String nombre = "";
            String apellido = "";
            String identificacion = "";

            try (PreparedStatement psCli = conn.prepareStatement(sqlBuscarCliente)) {
                psCli.setInt(1, venta.getIdCliente());
                try (ResultSet rs = psCli.executeQuery()) {
                    if (rs.next()) {
                        nombre = rs.getString("nombre");
                        apellido = rs.getString("apellido");
                        identificacion = rs.getString("identificacion");
                    }
                }
            }

            // 2. Preparar el formato de asientos "Economica / Premium y la hora a la que se realiza la venta"
            String textoAsientos = venta.getAsientosClaseEconomica() + " / " + venta.getAsientosClasePremium();
            java.sql.Timestamp ahora = java.sql.Timestamp.from(java.time.Instant.now());
            // 3. Insertar venta
            try (PreparedStatement ps = conn.prepareStatement(sqlInsertarVenta)) {
                ps.setInt(1, venta.getIdCliente());
                ps.setInt(2, venta.getIdViaje());
                ps.setString(3, nombre);
                ps.setString(4, apellido);
                ps.setString(5, identificacion);
                ps.setInt(6, venta.getCantidadAsientos());
                ps.setString(7, textoAsientos);
                ps.setDouble(8, venta.getTotalVenta());
                ps.setTimestamp(9, ahora);

                int resultado = ps.executeUpdate();

                if (resultado > 0) {
                    System.out.println("El cliente se ha insertado correctamente..");
                } else {
                    System.out.println("El Cliente no se inserto..");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (Exception ex) {
            System.out.println("Error al insertar venta: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Listar ventas

    /**
     * Muestra las ventas registradas en la tabla `ventas`.
     * El método parsea la columna `cEco/cPrem` que contiene "eco / prem".
     */
    public void obtenerVentas(Connection conn) {
        // Ajustamos la consulta a los nombres reales de la tabla y usamos backticks para `cEco/cPrem` y idvuelo en vez de idviaje
        String sql = "SELECT idventa, idcliente, idvuelo, cantidadAsientos, `cEco/cPrem`, total, fecha FROM ventas";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idVenta = rs.getInt("idventa");
                int idCliente = rs.getInt("idcliente");
                int idViaje = rs.getInt("idvuelo"); // Mapeamos idvuelo a idViaje
                int cantidad = rs.getInt("cantidadAsientos");
                String asientosStr = rs.getString("cEco/cPrem");
                double total = rs.getDouble("total");
                Timestamp fecha = rs.getTimestamp("fecha");

                // Parsear la cadena "Economica / Premium" (ej: "2 / 1")
                int eco = 0;
                int prem = 0;
                if (asientosStr != null && asientosStr.contains("/")) {
                    try {
                        String[] partes = asientosStr.split("/");
                        if (partes.length >= 2) {
                            eco = Integer.parseInt(partes[0].trim());
                            prem = Integer.parseInt(partes[1].trim());
                        }
                    } catch (NumberFormatException e) {
                        // Error silencioso al parsear, se quedan en 0
                    }
                }

                // Crear objeto Ventas
                Ventas v = new Ventas(idVenta, idCliente, idViaje, cantidad, eco, prem, total, fecha);
                System.out.println(v);
            }
        } catch (Exception ex) {
            System.out.println("Error al obtener ventas: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Disponibilidades
    public int asientosDisponiblesClaseEconomica(int idviaje, Connection conn) {
        String sql = "SELECT asientosClaseEconomica FROM viajes WHERE idviaje = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idviaje);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("asientosClaseEconomica");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int asientosDisponiblesClasePremium(int idviaje, Connection conn) {
        String sql = "SELECT asientosClasePremium FROM viajes WHERE idviaje = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idviaje);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("asientosClasePremium");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

}