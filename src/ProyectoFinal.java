import clases.Cliente;
import clases.Viajes;
import clases.Utilidades;

import java.sql.Connection;
import java.util.Scanner;

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
            System.out.println("2. Registrar viaje");
            System.out.println("3. Eliminar viaje");
            System.out.println("4. Lista Viajes");
            System.out.println("5. Reservar vuelo");
            System.out.println("6. Lista Ventas");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opcion: ");
            opc = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer
            switch (opc)
            {
                case 1:
                    System.out.println("Lista de Clientes");
                   util.obtenerDatos(conn);
                    System.out.println();
                    break;
                case 2:
                    System.out.println("Registrar Viaje");
                    Viajes viaje1 = new Viajes();
                    System.out.println("Ingrese nombre del viaje: ");
                    String nombreViaje = sc.nextLine();
                    viaje1.setDestino(nombreViaje);
                    System.out.println("Ingrese cantidad de asientos del viaje: ");
                    int cantidadViaje = sc.nextInt();
                    viaje1.setCantidad(cantidadViaje);
                    System.out.println("Ingrese precio del viaje: ");
                    double precioViaje = sc.nextDouble();
                    viaje1.setPrecio(precioViaje);
                    util.insetarDatosViaje(viaje1, conn);
                    sc.nextLine(); // Limpiar el buffer
                    break;
                case 3:
                    System.out.println("Eliminar Viaje");
                    System.out.println("Ingrese el ID del viaje a eliminar: ");
                    int id = sc.nextInt();
                    util.eliminaDatosViaje(id, conn);
                    sc.nextLine(); // Limpiar el buffer
                    break;
                case 4:
                    System.out.println("Lista de Viajes");
                    util.obtenerDatosViaje(conn);
                    break;
                case 5:
                    System.out.println("Reservar vuelo");
                    System.out.print("Ingrese ID del cliente: ");
                    int idCliente = sc.nextInt();
                    System.out.print("Ingrese ID del viaje a reservar: ");
                    int idViaje = sc.nextInt();
                    System.out.print("Ingrese cantidad de asientos a reservar: ");
                    int asientos = sc.nextInt();
                    sc.nextLine(); // limpiar buffer
                    util.reservarAsientosViaje(idViaje, asientos, idCliente, conn);
                    System.out.println();
                    break;
                case 6:
                    System.out.println("Lista de Ventas");
                    // Por ahora mostramos mensaje; se puede implementar registro de ventas en BD
                    System.out.println("Funcionalidad de ventas no implementada completamente.");
                    break;
                case 7:
                    System.out.println("Salir");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }while (opc != 7);

    }
}
