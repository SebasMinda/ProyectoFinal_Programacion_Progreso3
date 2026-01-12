package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * Clase Utilidades: concentra funciones "de apoyo" para trabajar con la BD.
 *
 * Explicación simple (pero clara):
 * - Abre la conexión a MySQL.
 * - Inserta y consulta datos de: clientes, viajes y ventas.
 * - Permite reiniciar tablas (borrar todo y reiniciar IDs).
 * - Controla disponibilidad de asientos por clase.
 * - Realiza la reserva con transacción para no dejar datos incompletos.
 */
public class Utilidades {

    /**
     * Crea y devuelve una conexión a MySQL usando datos fijos (hardcodeados).
     * - Si la conexión falla (credenciales, BD apagada, etc.) imprime error y retorna null.
     */
    public Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/cliente";
        String user = "root";
        String passwd = "sasa";

        try {
            // DriverManager se encarga de abrir la conexión con la URL + credenciales
            return DriverManager.getConnection(url, user, passwd);
        } catch (Exception ex) {
            // Para depurar rápido: muestra el motivo del fallo en consola
            ex.printStackTrace();
        }
        return null;
    }

    // =========================
    //         CLIENTES
    // =========================

    /**
     * Inserta un cliente en la tabla `cliente`.
     *
     * Qué hace paso a paso:
     * - Guarda nombre, apellido, correo e identificación.
     * - Calcula el total de asientos comprados si no viene definido.
     * - Guarda cuántos asientos fueron económicos y cuántos premium.
     * - Si la inserción fue exitosa, busca el idCliente generado y lo asigna al objeto Cliente.
     *
     * Nota:
     * - No lanza excepciones hacia afuera, solo imprime la traza.
     */
    public void insetarDatos(Cliente cliente, Connection conn) {
        String sql = "INSERT INTO cliente (nombre, apellido, email, identificacion, asientosComprados, asientosClaseEconomica, asientosClasePremium, precio) VALUES (?,?,?,?,?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            // 1) Guardamos los datos básicos del cliente en el INSERT
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getIdentificacion());

            // 2) Total de asientos: si está en 0, lo calculamos sumando eco + premium
            int totalAsientos = cliente.getAsientosComprados();
            if (totalAsientos <= 0) {
                totalAsientos = cliente.getAsientosClaseEconomica() + cliente.getAsientosClasePremium();
            }

            // 3) Guardamos los asientos y el precio en la tabla
            ps.setInt(5, totalAsientos);
            ps.setInt(6, cliente.getAsientosClaseEconomica());
            ps.setInt(7, cliente.getAsientosClasePremium());
            ps.setDouble(8, cliente.getPrecio());

            // 4) Ejecuta el INSERT y devuelve cuántas filas se insertaron
            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                System.out.println("Venta Registrada");

                // 5) Recuperar el ID para que las ventas queden asociadas al cliente correcto
                //    (se busca por identificacion y se toma el último id insertado con esa identificacion)
                try (PreparedStatement psId = conn.prepareStatement(
                        "SELECT idCliente FROM cliente WHERE identificacion = ? ORDER BY idCliente DESC LIMIT 1")) {

                    psId.setString(1, cliente.getIdentificacion());

                    try (ResultSet rs = psId.executeQuery()) {
                        if (rs.next()) {
                            // Guardamos el id generado dentro del objeto cliente en memoria
                            cliente.setIdCliente(rs.getInt("idCliente"));
                        }
                    }

                } catch (Exception exId) {
                    // Si falla solo esta parte, el cliente pudo insertarse igual
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
     * Resetea la tabla `cliente`:
     * - Desactiva temporalmente FOREIGN_KEY_CHECKS para evitar error por llaves foráneas.
     * - TRUNCATE borra todo y reinicia el AUTO_INCREMENT (IDs desde 1).
     * - Luego vuelve a activar FOREIGN_KEY_CHECKS.
     */
    public void resetearClientes(Connection conn) {
        try (Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stmt.executeUpdate("TRUNCATE TABLE cliente");
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");

            System.out.println("Tabla de clientes reseteada (ID reiniciado).");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Resetea la tabla `viajes`:
     * - Borra todos los viajes y reinicia el contador de IDs.
     */
    public void resetearviajes(Connection conn) {
        try (Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stmt.executeUpdate("TRUNCATE TABLE viajes");
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");

            System.out.println("Tabla de viajes reseteada (ID reiniciado).");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Resetea la tabla `ventas`:
     * - Borra todas las ventas y reinicia el contador de IDs.
     */
    public void resetearventas(Connection conn) {
        try (Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stmt.executeUpdate("TRUNCATE TABLE ventas");
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");

            System.out.println("Tabla de clientes ventas (ID reiniciado).");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // =========================
    //      LISTAR CLIENTES
    // =========================

    /**
     * Muestra por consola los clientes registrados.
     * - Consulta campos básicos y los imprime creando un objeto Cliente por cada fila.
     */
    public void obtenerDatos(Connection conn) {
        String sql = "SELECT idCliente, nombre, apellido, email, identificacion, asientosComprados, asientosClaseEconomica, asientosClasePremium, precio FROM cliente";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Creamos el objeto Cliente con los datos de la fila actual
                Cliente cli = new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("identificacion"),
                        rs.getInt("asientosComprados"),
                        rs.getInt("asientosClaseEconomica"),
                        rs.getInt("asientosClasePremium"),
                        rs.getDouble("precio"));
                System.out.println(cli+"\n");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // =========================
    //          VIAJES
    // =========================

    /**
     * Inserta un viaje en la tabla `viajes`.
     *
     * Detalles:
     * - Guarda origen/destino, cupos totales, cupos por clase y precios.
     * - Inicializa asientosvendidos en 0 porque el viaje inicia sin ventas.
     * - Guarda ganancias iniciales (normalmente 0).
     */
    public void insetarDatosViaje(Viajes viaje, Connection conn) {
        String sql = "INSERT INTO viajes (origen, destino, cantidadTotal, asientosClasePremium, asientosClaseEconomica, precioEconomica, precioPremium, asientosvendidos,ganancias) VALUES (?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, viaje.getOrigen());
            ps.setString(2, viaje.getDestino());
            ps.setInt(3, viaje.getCantidadTotal());

            // Cupos por clase
            ps.setInt(4, viaje.getAsientosClasePremium());
            ps.setInt(5, viaje.getAsientosClaseEconomica());

            // Precios por clase
            ps.setDouble(6, viaje.getPrecioEconomica());
            ps.setDouble(7, viaje.getPrecioPremium());

            // Viaje recién creado => 0 vendidos
            ps.setInt(8, 0);

            // Ganancias iniciales (normalmente 0)
            ps.setDouble(9, viaje.getGanancias());

            int resultado = ps.executeUpdate();

            if (resultado > 0) System.out.println("El viaje se ha insertado correctamente..");
            else System.out.println("El viaje no se inserto..");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Muestra todos los viajes con ganancias (vista para administrador).
     *
     * Retorna:
     * - 1 si hay viajes.
     * - 0 si no hay viajes o si ocurre error.
     */
    public int obtenerDatosViaje(Connection conn) {
        String sql = "SELECT idviaje, origen, destino, cantidadTotal, asientosClasePremium, asientosClaseEconomica, precioEconomica, precioPremium, ganancias FROM viajes";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int hayVuelos = 0;

            while (rs.next()) {
                hayVuelos = 1;

                // Creamos el objeto Viajes con toda la info (incluye ganancias)
                Viajes vjs = new Viajes(
                        rs.getInt("idviaje"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getInt("cantidadTotal"),
                        rs.getInt("asientosClaseEconomica"),
                        rs.getInt("asientosClasePremium"),
                        rs.getDouble("precioEconomica"),
                        rs.getDouble("precioPremium"),
                        rs.getDouble("ganancias")
                );

                System.out.println(vjs);
            }

            // Si no entró al while, no hay vuelos
            if (hayVuelos == 0) {
                return 0;
            }

            return hayVuelos;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return 0;
    }

    /**
     * Muestra los viajes sin ganancias (vista para cliente).
     *
     * Retorna:
     * - 1 si hay viajes.
     * - 0 si no hay viajes o si ocurre error.
     */
    public int obtenerDatosViajeCliente(Connection conn) {
        String sql = "SELECT idviaje, origen, destino, cantidadTotal, asientosClasePremium, asientosClaseEconomica, precioEconomica, precioPremium FROM viajes";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int hayVuelos = 0;

            while (rs.next()) {
                hayVuelos = 1;

                // Creamos el objeto Viajes sin ganancias (constructor diferente)
                Viajes vjs1 = new Viajes(
                        rs.getInt("idviaje"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getInt("cantidadTotal"),
                        rs.getInt("asientosClaseEconomica"),
                        rs.getInt("asientosClasePremium"),
                        rs.getDouble("precioEconomica"),
                        rs.getDouble("precioPremium")
                );

                // toString2 imprime un formato más "cliente" (sin ganancias)
                System.out.println(vjs1.toString2());
            }

            // Si no hay vuelos, devolvemos 0
            if (hayVuelos == 0) {
                return 0;
            }

            return hayVuelos;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return 0;
    }

    /**
     * Elimina un viaje por su ID.
     *
     * Qué hace:
     * - Borra el viaje si existe.
     * - Reordena IDs restando 1 a los que estén por encima del ID eliminado.
     * - Reinicia AUTO_INCREMENT para mantener IDs "consecutivos".
     *
     * Nota:
     * - En sistemas reales no suele reordenarse IDs (puede afectar relaciones),
     *   pero para un proyecto académico puede ser aceptable.
     */
    public void eliminaDatosViaje(int id, Connection conn) {
        try (PreparedStatement psDelete = conn.prepareStatement("DELETE FROM viajes WHERE idviaje = ?")) {

            psDelete.setInt(1, id);
            int resultado = psDelete.executeUpdate();

            if (resultado > 0) {

                // Reordenar IDs: si borraste el 3, el 4 pasa a 3, el 5 a 4, etc.
                try (PreparedStatement psUpdate = conn.prepareStatement(
                        "UPDATE viajes SET idviaje = idviaje - 1 WHERE idviaje > ?")) {

                    psUpdate.setInt(1, id);
                    psUpdate.executeUpdate();
                }

                // Reinicia el contador para que el próximo insert no se vaya a un número muy alto
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

    // =========================
    //     RESERVA / VENTAS
    // =========================

    /**
     * Reserva asientos y registra la venta usando una transacción.
     *
     * Por qué transacción:
     * - Para que "actualizar el viaje" y "insertar la venta" se guarden juntos.
     * - Si algo falla, se hace rollback y no queda el sistema a medias.
     *
     * Pasos:
     * 1) Bloquea el viaje con FOR UPDATE.
     * 2) Revisa si hay asientos suficientes por clase.
     * 3) Actualiza cupos y ganancias del viaje.
     * 4) Inserta una fila en ventas.
     */
    public void insertarDatosVenta(int idViaje, int cantidadEconomica, int cantidadPremium, Cliente cliente, Connection conn) {

        // FOR UPDATE bloquea la fila para evitar que dos compras vendan el mismo asiento al mismo tiempo
        String sqlCheck = "SELECT asientosClaseEconomica, asientosClasePremium, precioEconomica, precioPremium FROM viajes WHERE idviaje = ? FOR UPDATE";

        // Actualiza: cantidadTotal, asientos por clase, vendidos y ganancias en una sola instrucción
        String sqlUpdate = "UPDATE viajes SET cantidadTotal = cantidadTotal - ?, asientosClaseEconomica = asientosClaseEconomica - ?, asientosClasePremium = asientosClasePremium - ?, asientosvendidos = asientosvendidos + ?, ganancias = ganancias + ? WHERE idviaje = ?";

        try {
            // Inicia modo transacción manual (ya no se guardan cambios automáticamente)
            conn.setAutoCommit(false);

            // 1) Verificar disponibilidad del viaje seleccionado
            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {
                psCheck.setInt(1, idViaje);

                try (ResultSet rs = psCheck.executeQuery()) {

                    // Si no existe el viaje, cancelamos
                    if (!rs.next()) {
                        System.out.println("No existe el viaje con id " + idViaje);
                        conn.rollback();
                        conn.setAutoCommit(true);
                        return;
                    }

                    // Cupos actuales disponibles
                    int disponiblesEco = rs.getInt("asientosClaseEconomica");
                    int disponiblesPrem = rs.getInt("asientosClasePremium");

                    // Precios unitarios actuales
                    double precioEco = rs.getDouble("precioEconomica");
                    double precioPrem = rs.getDouble("precioPremium");

                    // 2) Validar que haya suficientes asientos en económica
                    if (disponiblesEco < cantidadEconomica) {
                        System.out.println("No hay suficientes asientos económicos. Disponibles: " + disponiblesEco);
                        conn.rollback();
                        conn.setAutoCommit(true);
                        return;
                    }

                    // 3) Validar que haya suficientes asientos en premium
                    if (disponiblesPrem < cantidadPremium) {
                        System.out.println("No hay suficientes asientos premium. Disponibles: " + disponiblesPrem);
                        conn.rollback();
                        conn.setAutoCommit(true);
                        return;
                    }

                    // 4) Calcular total a pagar
                    double totalVenta = (precioEco * cantidadEconomica) + (precioPrem * cantidadPremium);

                    // 5) Actualizar el viaje: restar asientos + sumar vendidos + sumar ganancias
                    try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {

                        int totalAsientos = cantidadEconomica + cantidadPremium;

                        psUpdate.setInt(1, totalAsientos);       // resta a cantidadTotal
                        psUpdate.setInt(2, cantidadEconomica);   // resta a eco
                        psUpdate.setInt(3, cantidadPremium);     // resta a premium
                        psUpdate.setInt(4, totalAsientos);       // suma a asientosvendidos
                        psUpdate.setDouble(5, totalVenta);       // suma a ganancias
                        psUpdate.setInt(6, idViaje);             // viaje a actualizar

                        int updated = psUpdate.executeUpdate();

                        // Si no se actualiza ninguna fila, algo salió mal (id no válido o error)
                        if (updated <= 0) {
                            System.out.println("No se pudo actualizar los asientos del viaje.");
                            conn.rollback();
                            conn.setAutoCommit(true);
                            return;
                        }
                    }

                    // 6) Registrar venta en la tabla ventas
                    int idCliente = (cliente != null) ? cliente.getIdCliente() : 0;

                    Ventas venta = new Ventas(
                            idCliente,
                            idViaje,
                            cantidadEconomica + cantidadPremium,
                            cantidadEconomica,
                            cantidadPremium,
                            totalVenta
                    );

                    insertarVenta(venta, conn);

                    // 7) Confirmar la transacción: se guardan los dos cambios (viaje + venta)
                    conn.commit();
                    conn.setAutoCommit(true);
                }
            }

        } catch (Exception ex) {
            // Si hay error, se debería revertir y volver a autocommit para no dejar la conexión “bloqueada”
            try { conn.rollback(); } catch (Exception ignore) {}
            try { conn.setAutoCommit(true); } catch (Exception ignore) {}
            ex.printStackTrace();
        }
    }

    // =========================
    //       PRECIOS
    // =========================

    /**
     * Devuelve el precio unitario según el tipo de asiento.
     * - tipoAsiento: 1 = económica, 2 = premium.
     *
     * Retorna:
     * - Precio si existe el viaje.
     * - -1 si no encuentra el viaje o ocurre error.
     */
    public double obtenerPrecioPorClase(int idViaje, int tipoAsiento, Connection conn) {
        String sql = "SELECT precioEconomica, precioPremium FROM viajes WHERE idviaje = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idViaje);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Si tipoAsiento == 1 => económica, caso contrario => premium
                    return (tipoAsiento == 1)
                            ? rs.getDouble("precioEconomica")
                            : rs.getDouble("precioPremium");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    // =========================
    //          VENTAS
    // =========================

    /**
     * Inserta una venta en la tabla `ventas`.
     *
     * Detalles:
     * - Busca el nombre/apellido/identificación del cliente para guardarlo en la venta.
     * - Forma la cadena `cEco/cPrem` como "eco / prem" (ej: "2 / 1").
     * - Registra la fecha/hora exacta (Timestamp) del momento de compra.
     */
    public void insertarVenta(Ventas venta, Connection conn) {

        // Consulta para traer información extra del cliente (se guarda en ventas como “histórico”)
        String sqlBuscarCliente = "SELECT nombre, apellido, identificacion FROM cliente WHERE idCliente = ?";

        // Insert: idvuelo es el campo en ventas aunque conceptualmente es idViaje
        String sqlInsertarVenta = "INSERT INTO ventas (idCliente, idvuelo, nombre, apellido, identificacion, cantidadAsientos, `cEco/cPrem`, total, fecha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // 1) Variables que llenaremos con datos del cliente (si existe)
            String nombre = "";
            String apellido = "";
            String identificacion = "";

            // 2) Buscar datos del cliente en tabla cliente
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

            // 3) Preparar el formato "Eco / Prem" (ej: "3 / 0")
            String textoAsientos = venta.getAsientosClaseEconomica() + " / " + venta.getAsientosClasePremium();

            // 4) Tomar fecha/hora actual exacta
            Timestamp ahora = Timestamp.from(java.time.Instant.now());

            // 5) Insertar el registro de venta en BD
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

                if (resultado > 0) System.out.println("La venta se ha registrado correctamente..");
                else System.out.println("La venta no se inserto..");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (Exception ex) {
            System.out.println("Error al insertar venta: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Muestra las ventas registradas en la tabla `ventas`.
     *
     * Detalles:
     * - Lee la columna `cEco/cPrem` (ej: "2 / 1") y la convierte a enteros.
     * - idvuelo en BD se mapea como idViaje en el objeto Ventas.
     */
    public void obtenerVentas(Connection conn) {
        String sql = "SELECT idventa, idCliente, idvuelo, cantidadAsientos, `cEco/cPrem`, total, fecha FROM ventas";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idVenta = rs.getInt("idventa");
                int idCliente = rs.getInt("idCliente");
                int idViaje = rs.getInt("idvuelo");
                int cantidad = rs.getInt("cantidadAsientos");
                String asientosStr = rs.getString("cEco/cPrem");
                double total = rs.getDouble("total");
                Timestamp fecha = rs.getTimestamp("fecha");

                // Convertir "eco / prem" a dos números (ej: "2 / 1" => eco=2, prem=1)
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
                        // Si falla el parseo, no rompemos el programa (se quedan en 0)
                    }
                }

                // Crear el objeto venta con todo y mostrarlo
                Ventas v = new Ventas(idVenta, idCliente, idViaje, cantidad, eco, prem, total, fecha);
                System.out.println(v);
            }

        } catch (Exception ex) {
            System.out.println("Error al obtener ventas: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // =========================
    //   DISPONIBILIDAD ASIENTOS
    // =========================

    /**
     * Devuelve cuántos asientos económicos quedan disponibles en un viaje.
     * - Si no encuentra el viaje o hay error, retorna 0.
     */
    public int asientosDisponiblesClaseEconomica(int idviaje, Connection conn) {
        String sql = "SELECT asientosClaseEconomica FROM viajes WHERE idViaje = ?";

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

    /**
     * Devuelve cuántos asientos premium quedan disponibles en un viaje.
     * - Si no encuentra el viaje o hay error, retorna 0.
     */
    public int asientosDisponiblesClasePremium(int idviaje, Connection conn) {
        String sql = "SELECT asientosClasePremium FROM viajes WHERE idViaje = ?";

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
