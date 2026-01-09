import clases.Cliente;
import clases.Viajes;
import clases.Utilidades;
import java.sql.Connection;
import java.util.Scanner;

public class ProyectoFinal {
    public static void main(String[] args) {
        Utilidades util = new Utilidades();
       Connection conn = null;
        conn = util.getConnection();
        if(conn != null){
            System.out.println("Conectados ..!!");
        }
        else {
            System.out.println("NO Conectado ...!!");
        }
        Scanner sc = new Scanner(System.in);
        int opc = 0;
        do {
            System.out.println("-----Menu Principal-----");
            System.out.println("Seleccione una opcion:");
            System.out.println("1. Ingresar como cliente");
            System.out.println("2. Ingresar como administrador");
            System.out.println("3. Salir");
            System.out.print("Opcion: ");
            opc = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer
            switch (opc)
            {
                case 1:
                    System.out.println("Ingresando como cliente...");
                    Cliente cliente1 = new Cliente();
                    System.out.println("Ingrese su nombre: ");
                    String nombre = sc.nextLine();
                    cliente1.setNombre(nombre);
                    System.out.println("Ingrese su apellido: ");
                    String apellido = sc.nextLine();
                    cliente1.setApellido(apellido);
                    System.out.println("Ingrese su correo electronico: ");
                    String email = sc.nextLine();
                    cliente1.setEmail(email);
                    System.out.println("Ingrese su identificacion: ");
                    String identificacion = sc.nextLine();
                    cliente1.setIdentificacion(identificacion);
                    System.out.println("Lista de Vuelos disponibles:");
                    util.obtenerDatosViajeCliente(conn);
                    System.out.println("Ingrese el ID del vuelo que desea reservar: ");
                    int idVuelo = sc.nextInt();
                    int opcAsiento = 0;
                    System.out.println("Seleccione la clase de asiento:");
                    System.out.println("1. Clase Economica");
                    System.out.println("2. Clase Premium");
                    System.out.print("Opcion: ");
                    opcAsiento = sc.nextInt();
                    sc.nextLine();

                    int cantidadDeseada = 0;
                    if (opcAsiento == 1) {
                        System.out.println("Ha seleccionado Clase Economica.");
                        System.out.println("Cuantos asientos desea reservar en Clase Economica?");
                        cantidadDeseada = sc.nextInt();
                        if (cantidadDeseada > 0) {
                            cliente1.setAsientosClaseEconomica(cantidadDeseada);
                        } else {
                            System.out.println("Cantidad inválida de asientos.");
                        }
                    } else if (opcAsiento == 2) {
                        System.out.println("Ha seleccionado Clase Premium.");
                        System.out.println("Cuantos asientos desea reservar en Clase Premium?");
                        cantidadDeseada = sc.nextInt();
                        if (cantidadDeseada > 0) {
                            cliente1.setAsientosClasePremium(cantidadDeseada);
                        } else {
                            System.out.println("Cantidad inválida de asientos.");
                        }
                    } else {
                        System.out.println("Opcion no valida");
                    }

                    if (cantidadDeseada > 0) {
                        // Llamamos a reservarAsientosViaje pasando el tipo de asiento elegido
                        util.reservarAsientosViaje(idVuelo, cantidadDeseada, cliente1.getId(), opcAsiento, conn);
                        util.insetarDatos(cliente1, conn);
                    }
                    break;
                case 2:
                    System.out.println("Ingresando como administrador...");
                    System.out.println("");
                    System.out.println("Ingrese la contraseña de administrador: ");
                    Integer password = Integer.valueOf(sc.nextLine());
                    if (password != 1234) {
                        System.out.println("Contraseña incorrecta. Volviendo al menu principal.");
                        break;
                    }
                    else {
                        System.out.println("Contraseña correcta. Acceso concedido.");
                        System.out.println("");
                        do {
                            System.out.println("-----Menu de opciones-----");
                            System.out.println("1. Lista Clientes");
                            System.out.println("2. Registrar viaje");
                            System.out.println("3. Eliminar viaje");
                            System.out.println("4. Lista Viajes");
                            System.out.println("5. Reservar vuelo");
                            System.out.println("6. Lista Ventas");
                            System.out.println("7. Volver al menu principal");
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
                                    System.out.println("Ingrese origen del viaje: ");
                                    String origenViaje = sc.nextLine();
                                    viaje1.setOrigen(origenViaje);
                                    System.out.println("Ingrese destino del viaje: ");
                                    String destinoViaje = sc.nextLine();
                                    viaje1.setDestino(destinoViaje);
                                    System.out.println("Ingrese cantidad de asientos vip del viaje: ");
                                    int asientosPremium = sc.nextInt();
                                    viaje1.setAsientosPremium(asientosPremium);
                                    System.out.println("Ingrese cantidad de asientos economicos del viaje: ");
                                    int asientosEconomica = sc.nextInt();
                                    viaje1.setAsientosEconomica(asientosEconomica);
                                    System.out.println("Ingrese precio asientos vip del viaje: ");
                                    double precioPremium = sc.nextDouble();
                                    viaje1.setPrecioPremium(precioPremium);
                                    System.out.println("Ingrese precio asientos economicos del viaje: ");
                                    double precioEconomica = sc.nextDouble();
                                    viaje1.setPrecioEconomica(precioEconomica);
                                    viaje1.setCantidadTotal(asientosEconomica+asientosPremium);
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
                                    System.out.println("Lista de Vuelos disponibles:");
                                    util.obtenerDatosViaje(conn);
                                    System.out.print("Ingrese ID del cliente: ");
                                    int idClienteAdmin = sc.nextInt();
                                    System.out.print("Ingrese ID del viaje a reservar: ");
                                    int idViajeAdmin = sc.nextInt();

                                    System.out.println("Seleccione la clase de asiento:");
                                    System.out.println("1. Clase Economica");
                                    System.out.println("2. Clase Premium");
                                    System.out.print("Opcion: ");
                                    int opcAsientoAdmin = sc.nextInt();
                                    sc.nextLine(); // limpiar buffer

                                    System.out.print("Ingrese cantidad de asientos a reservar: ");
                                    int asientos = sc.nextInt();
                                    sc.nextLine(); // limpiar buffer

                                    // Se corrigió la llamada para incluir el tipo de asiento (1=Eco, 2=Premium)
                                    util.reservarAsientosViaje(idViajeAdmin, asientos, idClienteAdmin, opcAsientoAdmin, conn);
                                    System.out.println();
                                    break;
                                case 6:
                                    System.out.println("Lista de Ventas");
                                    // Por ahora mostramos mensaje; se puede implementar registro de ventas en BD
                                    System.out.println("Funcionalidad de ventas no implementada completamente.");
                                    break;
                                case 7:
                                    System.out.println("Volviendo al menu principal...");
                                    break;
                                default:
                                    System.out.println("Opcion no valida");
                                    break;
                            }
                        }while (opc != 7);
                    }
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opcion no valida, intente de nuevo.");
                    break;
            }
        }while (opc!=3);


    }
}
