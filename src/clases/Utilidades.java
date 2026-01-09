package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    public void insetarDatos(Cliente cliente, Connection conn){
        // Se agregaron columnas asientosClaseEconomica, asientosClasePremium y precio al INSERT
        String sql = "INSERT INTO cliente (nombre, apellido, correo, identificacion, asientosComprados, asientosClaseEconomica, asientosClasePremium) VALUES (?,?,?,?,?,?,?)";
        try{

            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1,cliente.getNombre());
            ps.setString(2,cliente.getApellido());
            ps.setString(3,cliente.getEmail());
            ps.setString(4,cliente.getIdentificacion());
            ps.setInt(5, cliente.getAsientosComprados());
            ps.setInt(6, cliente.getAsientosClaseEconomica());
            ps.setInt(7, cliente.getAsientosClasePremium());

            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("El cliente se ha insertado correctamente..");
            }else {
                System.out.println("El Cliente no se inserto..");
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void obtenerDatos(Connection conn){
        String sql = "SELECT * FROM cliente.cliente "; //WHERE identificacion = \"1233\" ";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            while(rs.next()){
                /*Cliente cli = new Cliente (rs.getInt("idcliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("identificacion"));*/
                Cliente cli = new Cliente (rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5));
                System.out.println(cli.toString());
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public void insetarDatosViaje(Viajes viaje, Connection conn){
        // Cambio: tabla 'viajes' con columnas (destino, cantidad, precio)
        String sql = "INSERT INTO viajes (origen, destino, cantidadTotal, asientosvip, asientosnormal, precioEconomica, precioPremium, ganancias) VALUES (?,?,?,?,?,?,?,?)";
        try{

            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1, viaje.getOrigen());
            ps.setString(2, viaje.getDestino());
            ps.setInt(3, viaje.getCantidadTotal());
            ps.setInt(4, viaje.getAsientosPremium());
            ps.setInt(5, viaje.getAsientosEconomica());
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



    public void obtenerDatosViaje(Connection conn){
        String sql = "SELECT * FROM cliente.viajes";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery(sql)){

            while(rs.next()){
                Viajes vjs = new Viajes (rs.getInt(1),
                        rs.getString(3),
                        rs.getString(2),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getDouble(9));

                System.out.println(vjs.toString());
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public void obtenerDatosViajeCliente(Connection conn){
        String sql = "SELECT * FROM cliente.viajes";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                // Constructor: id, destino, origen, cantidadTotal, eco, prem, $eco, $prem
                // BD: 1:id, 2:origen, 3:destino, 4:total, 5:vip, 6:normal, 7:$eco, 8:$prem
                Viajes vjs1 = new Viajes (rs.getInt(1),
                        rs.getString(3), // destino
                        rs.getString(2), // origen
                        rs.getInt(4),
                        rs.getInt(6),    // Pasamos columna 6 (normal/eco) al parámetro Eco
                        rs.getInt(5),    // Pasamos columna 5 (vip/prem) al parámetro Premium
                        rs.getDouble(7),
                        rs.getDouble(8));

                System.out.println(vjs1.toString2());
            }

        } catch(Exception ex) {
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

        // Modificamos para aceptar tipoAsiento (1: Eco, 2: Premium)
        public void reservarAsientosViaje(int idViaje, int cantidad, int idCliente, int tipoAsiento, Connection conn) {
            if (cantidad <= 0) {
                System.out.println("La cantidad a reservar debe ser mayor que 0.");
                return;
            }

            // Seleccionamos nombre de columna según tipo
            String columnaAsientos = (tipoAsiento == 1) ? "asientosnormal" : "asientosvip";

            // Verificamos disponibilidad específica
            String sqlCheck = "SELECT " + columnaAsientos + ", cantidadTotal FROM viajes WHERE idviaje = ?";

            // SQL para actualizar: resta del total Y de la categoría específica
            String sqlUpdate = "UPDATE viajes SET cantidadTotal = cantidadTotal - ?, " + columnaAsientos + " = " + columnaAsientos + " - ? WHERE idviaje = ? AND " + columnaAsientos + " >= ?";

            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {
                psCheck.setInt(1, idViaje);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("No existe el viaje con id " + idViaje);
                        return;
                    }
                    int disponiblesCategoria = rs.getInt(1);
                    if (disponiblesCategoria < cantidad) {
                        System.out.println("No hay suficientes asientos disponibles en esa clase. Disponibles: " + disponiblesCategoria);
                        return;
                    }
                }

                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                psUpdate.setInt(1, cantidad); // Restar al total
                psUpdate.setInt(2, cantidad); // Restar a la categoría
                psUpdate.setInt(3, idViaje);
                psUpdate.setInt(4, cantidad); // Condición extra de seguridad

                int actualizado = psUpdate.executeUpdate();
                if (actualizado > 0) {
                    System.out.println("Reserva realizada correctamente. Disfrute su vuelo.");
                } else {
                    System.out.println("No se pudo completar la reserva. Verifique disponibilidad.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
}
