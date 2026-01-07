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
        String sql = "INSERT INTO cliente (nombre, apellido, correo, identificacion) VALUES (?,?,?,?)";
        try{

            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1,cliente.getNombre());
            ps.setString(2,cliente.getApellido());
            ps.setString(3,cliente.getEmail());
            ps.setString(4,cliente.getIdentificacion());

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
    public void insetarDatosproducto(Producto producto, Connection conn){
        String sql = "INSERT INTO producto (nombre, cantidad, precio) VALUES (?,?,?)";
        try{

            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1,producto.getNombreproducto());
            ps.setInt(2,producto.getCantidad());
            ps.setDouble(3,producto.getPrecio());

            int resultado = ps.executeUpdate();

            if(resultado > 0 ){
                System.out.println("El producto se ha insertado correctamente..");
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

    public void obtenerDatosproducto(Connection conn){
        String sql = "SELECT * FROM cliente.producto "; //WHERE identificacion = \"1233\" ";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery(sql)){

            while(rs.next()){
                /*Cliente cli = new Cliente (rs.getInt("idcliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("identificacion"));*/
                Producto pro = new Producto (rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getDouble(4));
                System.out.println(pro.toString());
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

        public void eliminaDatosproducto(int id, Connection conn) {
            String sqlDelete = "DELETE FROM producto WHERE idproducto = ?";
            // Esta consulta recorre los IDs siguientes restándoles 1
            String sqlUpdate = "UPDATE producto SET idproducto = idproducto - 1 WHERE idproducto > ?";
            // Este comando fuerza a MySQL a resetear el contador al siguiente número disponible real
            String sqlResetCounter = "ALTER TABLE producto AUTO_INCREMENT = 1";

            try {
                // 1. Intentar eliminar el producto
                PreparedStatement psDelete = conn.prepareStatement(sqlDelete);
                psDelete.setInt(1, id);
                int resultado = psDelete.executeUpdate();

                if (resultado > 0) {
                    System.out.println("El producto se ha eliminado correctamente.");

                    // 2. Si se eliminó, reordenar los IDs siguientes
                    PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                    psUpdate.setInt(1, id);
                    psUpdate.executeUpdate();
                    // 3. Resetear el contador para que el SIGUIENTE registro no pegue saltos (ej: no vaya al 8)
                    java.sql.Statement stmt = conn.createStatement();
                    stmt.executeUpdate(sqlResetCounter);
                    System.out.println("Se han reordenado los índices de los productos siguientes.");

                } else {
                    System.out.println("El producto no se ha eliminado (no existe ese ID).");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
}

