package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Utilidades {
    public Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/Cliente";
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
    public void obtenerDatos(Connection conn){
        String sql = "SELECT * FROM Cliente.cliente "; //WHERE identificacion = \"1233\" ";
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
}

