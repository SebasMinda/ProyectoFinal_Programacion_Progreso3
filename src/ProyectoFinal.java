import clases.Cliente;
import clases.Viajes;
import clases.Utilidades;

import java.sql.Connection;
import java.util.Scanner;

/**
 * Clase principal que contiene el menú y orquesta las operaciones del programa.
 *
 * Explicación simple:
 * - Muestra un menú inicial que permite ingresar como cliente o administrador.
 * - El cliente puede ver viajes y reservar asientos.
 * - El admin (clave 1234) puede gestionar viajes y ver ventas/clientes.
 */
public class ProyectoFinal {
    public static void main(String[] args) {
        Utilidades util = new Utilidades();
        Connection conn = null;
        Scanner sc = new Scanner(System.in);
        conn = util.getConnection();
        if (conn != null) {
            System.out.println("Conectados ..!!");
        } else {
            System.out.println("NO Conectado ...!!");
        }

        int opc;
        Cliente cliente1 = new Cliente();
        do {
            System.out.println("-----Menu Principal-----");
            System.out.println("Seleccione una opcion:");
            System.out.println("1. Ingresar como cliente");
            System.out.println("2. Ingresar como administrador");
            System.out.println("3. Salir");
            System.out.print("Opcion: ");
            opc = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer
            switch (opc) {
                case 1:
                    System.out.println("Ingresando como cliente...");
                    System.out.print("Ingrese su nombre: ");
                    String nombre = sc.nextLine();
                    cliente1.setNombre(nombre);
                    System.out.print("Ingrese su apellido: ");
                    String apellido = sc.nextLine();
                    cliente1.setApellido(apellido);
                    System.out.print("Ingrese su correo electronico: ");
                    String email = sc.nextLine();
                    cliente1.setEmail(email);
                    System.out.print("Ingrese su identificacion: ");
                    String identificacion = sc.nextLine();
                    cliente1.setIdentificacion(identificacion);
                    int volverALista;
                    do {
                        volverALista = reservarAsientosCliente(sc, util, conn, cliente1);
                    } while (volverALista == 1);
                    break;
                case 2:
                    System.out.println("\nIngresando como administrador...");
                    System.out.println("Ingrese la contraseña de administrador: ");
                    int password = Integer.parseInt(sc.nextLine());
                    if (password != 1234) {
                        System.out.println("Contraseña incorrecta. Volviendo al menu principal.");
                        break;
                    } else {
                        System.out.println("Contraseña correcta. Acceso concedido.");
                        do {
                            System.out.println("\n-----Menu de opciones-----");
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
                            switch (opc) {
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
                                    int asientosClasePremium = sc.nextInt();
                                    viaje1.setAsientosClasePremium(asientosClasePremium);
                                    System.out.println("Ingrese cantidad de asientos economicos del viaje: ");
                                    int asientosClaseEconomica = sc.nextInt();
                                    viaje1.setAsientosClaseEconomica(asientosClaseEconomica);
                                    System.out.println("Ingrese precio asientos vip del viaje: ");
                                    double precioPremium = sc.nextDouble();
                                    viaje1.setPrecioPremium(precioPremium);
                                    System.out.println("Ingrese precio asientos economicos del viaje: ");
                                    double precioEconomica = sc.nextDouble();
                                    viaje1.setPrecioEconomica(precioEconomica);
                                    viaje1.setCantidadTotal(asientosClaseEconomica + asientosClasePremium);
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
                                    System.out.print("Ingrese su nombre: ");
                                    nombre = sc.nextLine();
                                    cliente1.setNombre(nombre);
                                    System.out.print("Ingrese su apellido: ");
                                    apellido = sc.nextLine();
                                    cliente1.setApellido(apellido);
                                    System.out.print("Ingrese su correo electronico: ");
                                    email = sc.nextLine();
                                    cliente1.setEmail(email);
                                    System.out.print("Ingrese su identificacion: ");
                                    identificacion = sc.nextLine();
                                    cliente1.setIdentificacion(identificacion);
                                    do {
                                        volverALista = reservarAsientosCliente(sc, util, conn, cliente1);
                                    } while (volverALista == 1);
                                    break;
                                case 6:
                                    System.out.println("Lista de Ventas");
                                    util.obtenerVentas(conn);
                                    break;
                                case 7:
                                    System.out.println("Volviendo al menu principal...");
                                    break;
                                default:
                                    System.out.println("Opcion no valida");
                                    break;
                            }
                        } while (opc != 7);
                    }
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opcion no valida, intente de nuevo.");
                    break;
            }
        } while (opc != 3);
    }

    /**
     * Flujo para reservar asientos por parte del cliente o admin (comparten el mismo proceso).
     * Devuelve 1 si se quiere volver a la lista de vuelos, 0 para volver al menu principal.
     */
    public static int reservarAsientosCliente(Scanner sc, Utilidades util, Connection conn, Cliente cliente1) {
        System.out.println("Lista de Vuelos disponibles:");
        util.obtenerDatosViajeCliente(conn);
        System.out.print("Ingrese el ID del vuelo que desea reservar: ");
        int idVuelo = sc.nextInt();
        sc.nextLine();
        int claseInicial;
        int opc;
        int cantidadEconomica = 0;
        int cantidadPremium = 0;
        int procesoCancelado = 0;

        do {
            System.out.println("\nSeleccione la clase con la que desea iniciar la reserva:");
            System.out.println("1. Clase Economica");
            System.out.println("2. Clase Premium");
            System.out.print("Opcion: ");
            claseInicial = sc.nextInt();
            sc.nextLine();
            if (claseInicial != 1 && claseInicial != 2) {
                System.out.println("Opcion invalida. Intente nuevamente.");
            }
        } while (claseInicial != 1 && claseInicial != 2);
        switch (claseInicial) {
            case 1:
                int disponiblesEco = util.asientosDisponiblesClaseEconomica(idVuelo, conn);
                System.out.println("Asientos Clase Economica disponibles: " + disponiblesEco);
                System.out.print("Cuantos asientos en Clase Economica desea reservar? ");
                cantidadEconomica = sc.nextInt();
                sc.nextLine();
                /** Este if dice conmprueba si el cliente escribio mas asientos de los que hay si no hay no se hace nada por eso falta el else para este if
                 * y el siguiente if comprueba si hay asientos disponibles caso contrario se va al else correspondiente */
                if (cantidadEconomica > disponiblesEco) {
                    if (disponiblesEco > 0) {
                        System.out.println("No hay suficientes asientos en Clase Economica. Disponibles: " + disponiblesEco);
                        do {
                            System.out.println("Seleccione una opcion:");
                            System.out.println("1. Ajustar a los asientos disponibles (" + disponiblesEco + ")");
                            System.out.println("2. Salir a lista de vuelos");
                            System.out.println("3. Cancelar compra y salir al menu principal");
                            System.out.print("Seleccione una opcion: ");
                            opc = sc.nextInt();
                            sc.nextLine();
                            if (opc == 1) {
                                cantidadEconomica = disponiblesEco;
                                System.out.println("Se ajustó la cantidad a " + cantidadEconomica);
                            } else if (opc == 2) {
                                System.out.println("Regresando a lista de vuelos...");
                                return 1; // Retorna 1 para volver a pedir loop en main
                            } else if (opc == 3) {
                                System.out.println("Saliendo al menu principal...");
                                procesoCancelado = 1; // Marca cancelación para no seguir
                                break;
                            } else {
                                System.out.println("Opcion invalida.");
                            }
                        } while (opc != 1 && opc != 2 && opc != 3);
                    } else /** Este es si no hay asientos*/ {
                        System.out.println("No hay asientos en Clase Economica disponibles (0).");
                        do {
                            System.out.println("Seleccione una opcion:");
                            System.out.println("1. Salir a lista de vuelos");
                            System.out.println("2. Cancelar compra y salir al menu principal");
                            System.out.print("Seleccione una opcion: ");
                            opc = sc.nextInt();
                            sc.nextLine();
                            if (opc == 1) {
                                System.out.println("Regresando a lista de vuelos...");
                                return 1;
                            } else if (opc == 2) {
                                System.out.println("Saliendo al menu principal...");
                                procesoCancelado = 1;
                                break;
                            } else {
                                System.out.println("Opcion invalida.");
                            }
                        } while (opc != 1 && opc != 2);
                    }

                }
                if (procesoCancelado == 1) return 0;

                /**Aqui se pregunta si quiere asientos de la otra clase*/
                System.out.println("Desea agregar asientos en Clase Premium también?");
                do {
                    System.out.println("1 = SI, 2 = NO");
                    System.out.print("Seleccione una opcion: ");
                    opc = sc.nextInt();
                    sc.nextLine();
                } while (opc != 1 && opc != 2);
                if (opc == 1) {
                    int disponiblesPrem = util.asientosDisponiblesClasePremium(idVuelo, conn);
                    System.out.println("Asientos premium disponibles: " + disponiblesPrem);
                    System.out.print("Cuantos asientos en Clase Premium desea reservar? ");
                    cantidadPremium = sc.nextInt();
                    sc.nextLine();
                    if (cantidadPremium < 0)
                        cantidadPremium = 0; /**es para reestablecer la cantidad de asientos a 0 por si escriben numero negativo se quede en 0*/
                    if (cantidadPremium > disponiblesPrem) { /**Si dectecta que no hay suficientes asientos*/
                        if (disponiblesPrem > 0) {  /** Si hay asientos disponibles*/
                            System.out.println("No hay suficientes asientos premium. Disponibles: " + disponiblesPrem);
                            while (true) {
                                System.out.println("Seleccione una opcion:");
                                System.out.println("1. Ajustar a los asientos disponibles (" + disponiblesPrem + ")");
                                System.out.println("2. Seguir la compra sin los asientos premium");
                                System.out.println("3. Salir a lista de vuelos");
                                System.out.println("4. Salir al menu principal");
                                System.out.print("Seleccione una opcion: ");
                                opc = sc.nextInt();
                                sc.nextLine();
                                if (opc == 1) {
                                    cantidadPremium = disponiblesPrem;
                                    System.out.println("Se ajustó la cantidad a " + cantidadPremium);
                                    break;
                                } else if (opc == 2) {
                                    cantidadPremium = 0;
                                    System.out.println("Se continúa sin asientos premium.");
                                    break;
                                } else if (opc == 3) {
                                    System.out.println("Regresando a lista de vuelos...");
                                    return 1;
                                } else if (opc == 4) {
                                    System.out.println("Saliendo al menu principal...");
                                    return 0;
                                } else {
                                    System.out.println("Opcion invalida.");
                                }
                            }
                        } else {
                            System.out.println("No hay asientos premium disponibles.");
                            while (true) {
                                System.out.println("Seleccione una opcion:");
                                System.out.println("1. Seguir compra sin los asientos");
                                System.out.println("2. Salir a lista de vuelos");
                                System.out.println("3. Salir al menu principal");
                                System.out.print("Seleccione una opcion: ");
                                opc = sc.nextInt();
                                sc.nextLine();
                                if (opc == 1) {
                                    cantidadPremium = 0;
                                    System.out.println("Se continúa sin asientos premium.");
                                    break;
                                } else if (opc == 2) {
                                    System.out.println("Regresando a lista de vuelos...");
                                    return 1;
                                } else if (opc == 3) {
                                    System.out.println("Saliendo al menu principal...");
                                    return 0;
                                } else {
                                    System.out.println("Opcion invalida.");
                                }
                            }
                        }
                    }
                }
                break;
            case 2:
                int disponiblesPrem = util.asientosDisponiblesClasePremium(idVuelo, conn);
                System.out.println("Asientos Clase Premium disponibles: " + disponiblesPrem);
                System.out.print("Cuantos asientos en Clase Premium desea reservar? ");
                cantidadPremium = sc.nextInt();
                sc.nextLine();
                /** Este if dice conmprueba si el cliente escribio mas asientos de los que hay si no hay no se hace nada por eso falta el else para este if
                 * y el siguiente if comprueba si hay asientos disponibles caso contrario se va al else correspondiente */
                if (cantidadPremium > disponiblesPrem) {
                    if (disponiblesPrem > 0) {
                        System.out.println("No hay suficientes asientos en Clase Premium. Disponibles: " + disponiblesPrem);
                        do {
                            System.out.println("Seleccione una opcion:");
                            System.out.println("1. Ajustar a los asientos disponibles (" + disponiblesPrem + ")");
                            System.out.println("2. Salir a lista de vuelos");
                            System.out.println("3. Cancelar compra y salir al menu principal");
                            System.out.print("Seleccione una opcion: ");
                            opc = sc.nextInt();
                            sc.nextLine();
                            if (opc == 1) {
                                cantidadPremium = disponiblesPrem;
                                System.out.println("Se ajustó la cantidad a " + cantidadPremium);
                            } else if (opc == 2) {
                                System.out.println("Regresando a lista de vuelos...");
                                return 1; // Retorna 1 (volver a lista de vuelos)
                            } else if (opc == 3) {
                                System.out.println("Saliendo al menu principal...");
                                procesoCancelado = 1; // Marca cancelación para no seguir
                                break;
                            } else {
                                System.out.println("Opcion invalida.");
                            }
                        } while (opc != 1 && opc != 2 && opc != 3);
                    } else /** Este es si no hay asientos*/ {
                        System.out.println("No hay asientos en Clase Premium disponibles (0).");
                        do {
                            System.out.println("Seleccione una opcion:");
                            System.out.println("1. Salir a lista de vuelos");
                            System.out.println("2. Cancelar compra y salir al menu principal");
                            System.out.print("Seleccione una opcion: ");
                            opc = sc.nextInt();
                            sc.nextLine();
                            if (opc == 1) {
                                System.out.println("Regresando a lista de vuelos...");
                                return 1;
                            } else if (opc == 2) {
                                System.out.println("Saliendo al menu principal...");
                                procesoCancelado = 1;
                                break;
                            } else {
                                System.out.println("Opcion invalida.");
                            }
                        } while (opc != 1 && opc != 2);
                    }

                }
                if (procesoCancelado == 1) return 0;

                /**Aqui se pregunta si quiere asientos de la otra clase*/
                System.out.println("Desea agregar asientos en Clase Economica también?");
                do {
                    System.out.println("1 = SI, 2 = NO");
                    System.out.print("Seleccione una opcion: ");
                    opc = sc.nextInt();
                    sc.nextLine();
                } while (opc != 1 && opc != 2);
                if (opc == 1) {
                    int disponiblesEco2 = util.asientosDisponiblesClaseEconomica(idVuelo, conn);
                    System.out.println("Asientos economicos disponibles: " + disponiblesEco2);
                    System.out.print("Cuantos asientos en Clase Economica desea reservar? ");
                    cantidadEconomica = sc.nextInt();
                    sc.nextLine();
                    if (cantidadEconomica < 0)
                        cantidadEconomica = 0; /**es para reestablecer la cantidad de asientos a 0 por si escriben numero negativo se quede en 0*/
                    if (cantidadEconomica > disponiblesEco2) { /**Si dectecta que no hay suficientes asientos*/
                        if (disponiblesEco2 > 0) {  /** Si hay asientos disponibles*/
                            System.out.println("No hay suficientes asientos economicos. Disponibles: " + disponiblesEco2);
                            while (true) {
                                System.out.println("Seleccione una opcion:");
                                System.out.println("1. Ajustar a los asientos disponibles (" + disponiblesEco2 + ")");
                                System.out.println("2. Seguir la compra sin los asientos economicos");
                                System.out.println("3. Salir a lista de vuelos");
                                System.out.println("4. Salir al menu principal");
                                System.out.print("Seleccione una opcion: ");
                                opc = sc.nextInt();
                                sc.nextLine();
                                if (opc == 1) {
                                    cantidadEconomica = disponiblesEco2;
                                    System.out.println("Se ajustó la cantidad a " + cantidadEconomica);
                                    break;
                                } else if (opc == 2) {
                                    cantidadEconomica = 0;
                                    System.out.println("Se continúa sin asientos economicos.");
                                    break;
                                } else if (opc == 3) {
                                    System.out.println("Regresando a lista de vuelos...");
                                    return 1;
                                } else if (opc == 4) {
                                    System.out.println("Saliendo al menu principal...");
                                    return 0;
                                } else {
                                    System.out.println("Opcion invalida.");
                                }
                            }
                        } else {
                            System.out.println("No hay asientos economicos disponibles.");
                            while (true) {
                                System.out.println("Seleccione una opcion:");
                                System.out.println("1. Seguir compra sin los asientos");
                                System.out.println("2. Salir a lista de vuelos");
                                System.out.println("3. Salir al menu principal");
                                System.out.print("Seleccione una opcion: ");
                                opc = sc.nextInt();
                                sc.nextLine();
                                if (opc == 1) {
                                    cantidadEconomica = 0;
                                    System.out.println("Se continúa sin asientos economicos.");
                                    break;
                                } else if (opc == 2) {
                                    System.out.println("Regresando a lista de vuelos...");
                                    return 1;
                                } else if (opc == 3) {
                                    System.out.println("Saliendo al menu principal...");
                                    return 0;
                                } else {
                                    System.out.println("Opcion invalida.");
                                }
                            }
                        }
                    }
                }
                break;
        }

        int totalAsientosSeleccionados = cantidadEconomica + cantidadPremium;
        double precioEcoUnit = util.obtenerPrecioPorClase(idVuelo, 1, conn);
        double precioPremUnit = util.obtenerPrecioPorClase(idVuelo, 2, conn);
        double totalPagar = precioEcoUnit * cantidadEconomica + precioPremUnit * cantidadPremium;

        System.out.println("\nResumen de la reserva:");
        System.out.println("Asientos Clase Economica: " + cantidadEconomica + " Precio Unitario: " + precioEcoUnit);
        System.out.println("Asientos Clase Premium:   " + cantidadPremium + " Precio Unitario: " + precioPremUnit);
        System.out.println("Total asientos: " + totalAsientosSeleccionados);
        System.out.println("Total a pagar: " + totalPagar);
        do {
            System.out.print("Confirma la compra? 1 = SI, 2 = NO : ");
            opc = sc.nextInt();
            sc.nextLine();
            if (opc == 2) {
                System.out.println("Compra cancelada.");
                return 0;
            }
        } while (opc != 1 && opc != 2);
        cliente1.setAsientosClaseEconomica(cantidadEconomica);
        cliente1.setAsientosClasePremium(cantidadPremium);
        cliente1.setAsientosComprados(totalAsientosSeleccionados);
        cliente1.setPrecio(totalPagar);
        System.out.println("Venta registrada");
        util.insetarDatos(cliente1,conn);
        util.insertarDatosVenta(idVuelo,cantidadEconomica,cantidadPremium,cliente1,conn);
        return 0;
    }
}
