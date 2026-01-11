package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class Utilidades {
    public Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/cliente";
        String user = "root";
        String passwd = "sasa";

        Connection conn = null;

        try{
                conn = DriverManager.getConnection(url, user, passwd);
                return conn;
        } catch (Exception ex){
                ex.printStackTrace();
        }
        return null;
    }

    // -----------------------------
    // Métodos de inserción / actualización / reservas
    // -----------------------------

    // Inserta cliente y devuelve id generado o -1 en fallo
    public int insetarDatos(Cliente cliente, Connection conn){
        String sql = "INSERT INTO cliente (nombre, apellido, correo, identificacion, asientosComprados, asientosClaseEconomica, asientosClasePremium) VALUES (?,?,?,?,?,?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,cliente.getNombre());
            ps.setString(2,cliente.getApellido());
            ps.setString(3,cliente.getEmail());
            ps.setString(4,cliente.getIdentificacion());
            int totalAsientos = cliente.getAsientosComprados();
            if (totalAsientos <= 0) {
                totalAsientos = cliente.getAsientosClaseEconomica() + cliente.getAsientosClasePremium();
            }

            ps.setInt(5, totalAsientos);
            ps.setInt(6, cliente.getAsientosClaseEconomica());
            ps.setInt(7, cliente.getAsientosClasePremium());

            int resultado = ps.executeUpdate();
            if(resultado > 0 ){
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        int newId = keys.getInt(1);
                        cliente.setId(newId);
                        System.out.println("El cliente se ha insertado correctamente con id=" + newId);
                        return newId;
                    }
                }
                System.out.println("El cliente se ha insertado correctamente (sin id recuperado).");
                return -1;
            }else {
                System.out.println("El Cliente no se inserto..");
                return -1;
            }

        } catch(Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }

    public void insetarDatosViaje(Viajes viaje, Connection conn){
        // Cambio: tabla 'viajes' con columnas (origen, destino, cantidadTotal, asientosClasePremium, asientosClaseEconomica, precioEconomica, precioPremium, ganancias)
        String sql = "INSERT INTO viajes (origen, destino, cantidadTotal, asientosClasePremium, asientosClaseEconomica, precioEconomica, precioPremium, ganancias) VALUES (?,?,?,?,?,?,?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, viaje.getOrigen());
            ps.setString(2, viaje.getDestino());
            ps.setInt(3, viaje.getCantidadTotal());
            // pasamos el valor Java asientosClasePremium/ClaseEconomica a las columnas SQL renombradas
            ps.setInt(4, viaje.getAsientosClasePremium());
            ps.setInt(5, viaje.getAsientosClaseEconomica());
            ps.setDouble(6, viaje.getPrecioEconomica());
            ps.setDouble(7, viaje.getPrecioPremium());
            ps.setDouble(8, viaje.getGanancias());

            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("El viaje se ha insertado correctamente..");
            }else {
                System.out.println("El viaje no se inserto..");
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    // Inserta una venta en la tabla 'ventas'. Asume columnas: idventa (AUTO_INCREMENT), idcliente, idviaje, cantidad, clase, total, fecha
    public void insertarVenta(Ventas venta, Connection conn) {
        String sql = "INSERT INTO ventas (idcliente, idviaje, cantidad, clase, total, fecha) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, venta.getIdCliente());
            ps.setInt(2, venta.getIdViaje());
            ps.setInt(3, venta.getCantidadAsientos());
            ps.setString(4, venta.getClase());
            ps.setDouble(5, venta.getTotalVenta());
            Timestamp now = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(6, now);
            int res = ps.executeUpdate();
            if (res > 0) System.out.println("Venta registrada correctamente.");
            else System.out.println("No se pudo registrar la venta.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void actualizaDatos(Connection conn) {
        String sql = "UPDATE cliente SET nombre=\"QUINCE\" WHERE idcliente=15";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("El cliente se ha actualizado correctamente..");
            }else {
                System.out.println("El Cliente no se ha actualizado ..");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void eliminaDatos(Connection conn) {
        String sql = "DELETE  FROM cliente WHERE idcliente=11";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("El cliente se ha eliminado correctamente..");
            }else {
                System.out.println("El Cliente no se ha eliminado ..");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void eliminaDatosViaje(int id, Connection conn) {
        try {
            String sqlDelete = "DELETE FROM viajes WHERE idviaje = ?";
            // 1. Intentar eliminar el viaje
            PreparedStatement psDelete = conn.prepareStatement(sqlDelete);
            psDelete.setInt(1, id);
            int resultado = psDelete.executeUpdate();

            if (resultado > 0) {
                System.out.println("El viaje se ha eliminado correctamente.");
                // Esta consulta recorre los IDs siguientes restándoles 1
                String sqlUpdate = "UPDATE viajes SET idviaje = idviaje - 1 WHERE idviaje > ?";
                // 2. Si se eliminó, reordenar los IDs siguientes
                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                psUpdate.setInt(1, id);
                psUpdate.executeUpdate();
                // 3. Resetear el contador para que el SIGUIENTE registro no pegue saltos (ej: no vaya al 8)
                java.sql.Statement stmt = conn.createStatement();
                // Este comando fuerza a MySQL a resetear el contador al siguiente número disponible real
                String sqlResetCounter = "ALTER TABLE viajes AUTO_INCREMENT = 1";
                stmt.executeUpdate(sqlResetCounter);
                System.out.println("Se han reordenado los índices de los viajes siguientes.");

            } else {
                System.out.println("El viaje no se ha eliminado (no existe ese ID).");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Ahora devuelve boolean y registra venta si la reserva es exitosa
    public boolean reservarAsientosViaje(int idViaje, int cantidad, int idCliente, int tipoAsiento, Connection conn) {
        if (cantidad <= 0) {
            System.out.println("La cantidad a reservar debe ser mayor que 0.");
            return false;
        }

        // Seleccionamos nombre de columna según tipo (ahora columnas renombradas en la BD)
        String columnaAsientos = (tipoAsiento == 1) ? "asientosClaseEconomica" : "asientosClasePremium";

        // Verificamos disponibilidad específica
        String sqlCheck = "SELECT " + columnaAsientos + ", cantidadTotal FROM viajes WHERE idviaje = ?";

        // SQL para actualizar: resta del total Y de la categoría específica
        String sqlUpdate = "UPDATE viajes SET cantidadTotal = cantidadTotal - ?, " + columnaAsientos + " = " + columnaAsientos + " - ? WHERE idviaje = ? AND " + columnaAsientos + " >= ?";

        try (PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {
            psCheck.setInt(1, idViaje);
            try (ResultSet rs = psCheck.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("No existe el viaje con id " + idViaje);
                    return false;
                }
                int disponiblesCategoria = rs.getInt(1);
                if (disponiblesCategoria < cantidad) {
                    System.out.println("No hay suficientes asientos disponibles en esa clase. Disponibles: " + disponiblesCategoria);
                    return false;
                }
            }

            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                psUpdate.setInt(1, cantidad); // Restar al total
                psUpdate.setInt(2, cantidad); // Restar a la categoría
                psUpdate.setInt(3, idViaje);
                psUpdate.setInt(4, cantidad); // Condición extra de seguridad

                int actualizado = psUpdate.executeUpdate();
                if (actualizado > 0) {
                    System.out.println("Reserva realizada correctamente. Disfrute su vuelo.");
                    // calcular precio y registrar venta
                    double precioUnit = obtenerPrecioPorClase(idViaje, tipoAsiento, conn);
                    if (precioUnit >= 0) {
                        double total = precioUnit * cantidad;
                        String claseStr = (tipoAsiento == 1) ? "economica" : "premium";
                        Ventas venta = new Ventas(idCliente, idViaje, cantidad, claseStr, total);
                        insertarVenta(venta, conn);
                    } else {
                        System.out.println("No se pudo obtener el precio del viaje para registrar la venta.");
                    }
                    return true;
                } else {
                    System.out.println("No se pudo completar la reserva. Verifique disponibilidad.");
                    return false;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // -----------------------------
    // Métodos de obtención / lectura (no modificar contenido)
    // -----------------------------

    public void obtenerDatos(Connection conn){
        // Seleccionamos columnas explícitas para evitar problemas con el orden
        String sql = "SELECT idcliente, nombre, apellido, correo, identificacion FROM cliente";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                Cliente cli = new Cliente (rs.getInt("idcliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("identificacion"));
                System.out.println(cli.toString());
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public void obtenerDatosViaje(Connection conn){
        String sql = "SELECT idviaje, origen, destino, cantidadTotal, asientosClasePremium, asientosClaseEconomica, precioEconomica, precioPremium, ganancias FROM viajes";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                // Leemos las columnas renombradas directamente y las pasamos al constructor
                Viajes vjs = new Viajes (rs.getInt("idviaje"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getInt("cantidadTotal"),
                        rs.getInt("asientosClaseEconomica"),
                        rs.getInt("asientosClasePremium"),
                        rs.getDouble("precioEconomica"),
                        rs.getDouble("precioPremium"),
                        rs.getDouble("ganancias"));

                System.out.println(vjs.toString());
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public void obtenerDatosViajeCliente(Connection conn){
        String sql = "SELECT idviaje, origen, destino, cantidadTotal, asientosClasePremium, asientosClaseEconomica, precioEconomica, precioPremium FROM viajes";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                    Viajes vjs1 = new Viajes (rs.getInt("idviaje"),
                            rs.getString("origen"), // origen
                            rs.getString("destino"), // destino
                            rs.getInt("cantidadTotal"),
                            rs.getInt("asientosClaseEconomica"),    // columna economica
                            rs.getInt("asientosClasePremium"),    // columna premium
                            rs.getDouble("precioEconomica"),
                            rs.getDouble("precioPremium"));

                    System.out.println(vjs1.toString2());
                }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    // Lista las ventas registradas
    public void obtenerVentas(Connection conn) {
        String sql = "SELECT idventa, idcliente, idviaje, cantidad, clase, total, fecha FROM ventas";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ventas v = new Ventas(rs.getInt("idventa"), rs.getInt("idcliente"), rs.getInt("idviaje"), rs.getInt("cantidad"), rs.getString("clase"), rs.getDouble("total"), rs.getTimestamp("fecha"));
                System.out.println(v.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int asientosDisponiblesClaseEconomica(int idviaje, Connection conn){
        String sql = "SELECT asientosClaseEconomica FROM viajes WHERE idviaje = ?"; // ahora usa la columna renombrada para económica
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, idviaje);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    // Devuelve el número de asientos economicos disponibles para ese viaje
                    return rs.getInt("asientosClaseEconomica");
                } else {
                    // No existe el viaje
                    return 0;
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int asientosDisponiblesClasePremium(int idviaje, Connection conn){
        String sql = "SELECT asientosClasePremium FROM viajes WHERE idviaje = ?"; // columna renombrada para premium
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, idviaje);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    // Devuelve el número de asientos premium disponibles para ese viaje (columna SQL asientosvip)
                    return rs.getInt("asientosClasePremium");
                } else {
                    // No existe el viaje
                    return 0;
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    // obtener precio por clase
    public double obtenerPrecioPorClase(int idViaje, int tipoAsiento, Connection conn) {
        String sql = "SELECT precioEconomica, precioPremium FROM viajes WHERE idviaje = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idViaje);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    if (tipoAsiento == 1) return rs.getDouble("precioEconomica");
                    else return rs.getDouble("precioPremium");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

}


