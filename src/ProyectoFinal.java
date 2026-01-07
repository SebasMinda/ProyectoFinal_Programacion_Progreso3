import clases.Cliente;
import clases.Producto;
import clases.Utilidades;

import java.sql.Connection;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class ProyectoFinal {
    public static void main(String[] args) {
        Utilidades util = new Utilidades();
        Cliente cli1 = new Cliente("Cualquiera","Cualquiera","cucu@correo.com","1236ccc");
        //Cliente cli2 = new Cliente(6,"Cuarto");

        Connection conn = null;
        conn = util.getConnection();
        if(conn != null){
            System.out.println("Conectados ..!!");
            util.insetarDatos(cli1, conn);
            System.out.println();
            util.obtenerDatos(conn);
            System.out.println();
            util.actualizaDatos(conn);
            System.out.println();
            util.obtenerDatos(conn);
            System.out.println();
            util.eliminaDatos(conn);
            System.out.println();
            util.obtenerDatos(conn);

        }else {
            System.out.println("NO Conectado ...!!");
        }
        Scanner sc = new Scanner(System.in);
        int opc = 0;
        do {
            System.out.println("-----Menu de opciones-----");
            System.out.println("1. Lista Clientes");
            System.out.println("2. Registrar productos");
            System.out.println("3. Lista Productos");
            System.out.println("4. Hacer Venta");
            System.out.println("5. Lista Ventas");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");
            opc = sc.nextInt();
            switch (opc)
            {
                case 1:
                    System.out.println("Lista de Clientes");
                   util.obtenerDatos(conn);
                    System.out.println();
                    break;
                case 2:
                    System.out.println("Registrar Productos");
                    Producto prod1 = new Producto("Cualquiera",1234,1234.5);
                    util.insetarDatosproducto(prod1, conn);
                    break;
                case 3:
                    System.out.println("Lista de Productos");
                    util.obtenerDatosproducto(conn);
                    break;
                case 4:
                    System.out.println("Hacer Venta");
                    break;
                case 5:
                    System.out.println("Lista de Ventas");
                    break;
                case 6:
                    System.out.println("Salir");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }while (opc != 6);

    }
}
