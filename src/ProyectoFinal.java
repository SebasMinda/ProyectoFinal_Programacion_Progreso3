import clases.Cliente;
import clases.Viajes;
import clases.Utilidades;
import java.sql.Connection;
import java.util.Scanner;

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
                    Cliente cliente1 = new Cliente();
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
                    System.out.println("Lista de Vuelos disponibles:");
                    do {
                    util.obtenerDatosViajeCliente(conn);
                    int salir;
                        System.out.print("Ingrese el ID del vuelo que desea reservar: ");
                        int idVuelo = sc.nextInt();
                        sc.nextLine();
                        salir= reservarAsientosCliente(util, conn, sc, cliente1, idVuelo);
                    }while (salir==2);
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

                                    boolean reservadoAdmin = util.reservarAsientosViaje(idViajeAdmin, asientos, idClienteAdmin, opcAsientoAdmin, conn);
                                    if (reservadoAdmin) {
                                        System.out.println("Reserva realizada y venta registrada (si corresponde).");
                                    } else {
                                        System.out.println("No se pudo completar la reserva.");
                                    }
                                    System.out.println();
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

        public static int reservarAsientosCliente(Utilidades util, Connection conn, Scanner sc, Cliente cliente1, int idVuelo) {

            int claseInicial;
            int opc;
            int cantidadEconomica = 0;
            int cantidadPremium = 0;
            // Variable de control para saber si el usuario decidió salir/cancelar a medio proceso
            boolean procesoCancelado = false;

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
                }while (claseInicial != 1 && claseInicial != 2);

                if (claseInicial == 1) {
                    // ---------------------------------------------------------
                    // BLOQUE 1: El usuario eligió iniciar con Clase Económica
                    // ---------------------------------------------------------
                    int disponiblesEco = util.asientosDisponiblesClaseEconomica(idVuelo, conn);
                    System.out.println("Asientos Clase Economica disponibles: " + disponiblesEco);
                    System.out.print("Cuantos asientos en Clase Economica desea reservar? ");
                    cantidadEconomica = sc.nextInt();
                    sc.nextLine();
                    if (cantidadEconomica > disponiblesEco) {
                        if (disponiblesEco > 0) {
                            System.out.println("No hay suficientes asientos en Clase Economica. Disponibles: " + disponiblesEco);
                            while (true) {
                                System.out.println("Seleccione una opcion:");
                                System.out.println("1. Ajustar a los asientos disponibles (" + disponiblesEco + ")");
                                System.out.println("2. Salir a lista de vuelos");
                                System.out.println("3. Salir al menu principal");
                                opc = sc.nextInt();
                                sc.nextLine();
                                if (opc == 1) {
                                    cantidadEconomica = disponiblesEco;
                                    System.out.println("Se ajustó la cantidad a " + cantidadEconomica);
                                    break;
                                } else if (opc == 2) {
                                    System.out.println("Regresando a lista de vuelos...");
                                    return 2; // Retorna 2 (volver a lista de vuelos)
                                } else if (opc == 3) {
                                    System.out.println("Saliendo al menu principal...");
                                    procesoCancelado = true; // Marca cancelación para no seguir
                                    break;
                                } else {
                                    System.out.println("Opcion invalida.");
                                }
                            }
                        } else {
                            System.out.println("No hay asientos en Clase Economica disponibles (0).");
                            while (true) {
                                System.out.println("Seleccione una opcion:");
                                System.out.println("1. Salir a lista de vuelos");
                                System.out.println("2. Salir al menu principal");
                                opc = sc.nextInt();
                                sc.nextLine();
                                if (opc == 1) {
                                    System.out.println("Regresando a lista de vuelos...");
                                    return 2;
                                } else if (opc == 2) {
                                    System.out.println("Saliendo al menu principal...");
                                    procesoCancelado = true;
                                    break;
                                } else {
                                    System.out.println("Opcion invalida.");
                                }
                            }
                        }
                    }

                    if (procesoCancelado) return 1; // Salir si canceló

                    System.out.println("Desea agregar asientos en Clase Premium también?");
                    do {
                        System.out.println("1 = SI, 2 = NO");
                        opc = sc.nextInt();
                        sc.nextLine();
                    }while (opc!=1 && opc!=2);
                    if (opc == 1) {
                        int disponiblesPrem = util.asientosDisponiblesClasePremium(idVuelo, conn);
                        System.out.println("Asientos premium disponibles: " + disponiblesPrem);
                        System.out.print("Cuantos asientos en Clase Premium desea reservar? ");
                        cantidadPremium = sc.nextInt();
                        sc.nextLine();
                        if (cantidadPremium < 0) cantidadPremium = 0;
                        if (cantidadPremium > disponiblesPrem) {
                            if (disponiblesPrem > 0) {
                                System.out.println("No hay suficientes asientos premium. Disponibles: " + disponiblesPrem);
                                while (true) {
                                    System.out.println("Seleccione una opcion:");
                                    System.out.println("1. Ajustar a los asientos disponibles (" + disponiblesPrem + ")");
                                    System.out.println("2. Seguir la compra sin los asientos premium");
                                    System.out.println("3. Salir a lista de vuelos");
                                    System.out.println("4. Salir al menu principal");
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
                                        return 2;
                                    } else if (opc == 4) {
                                        System.out.println("Saliendo al menu principal...");
                                        return 1;
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
                                    opc = sc.nextInt();
                                    sc.nextLine();
                                    if (opc == 1) {
                                        cantidadPremium = 0;
                                        System.out.println("Se continúa sin asientos premium.");
                                        break;
                                    } else if (opc == 2) {
                                        System.out.println("Regresando a lista de vuelos...");
                                        return 2;
                                    } else if (opc == 3) {
                                        System.out.println("Saliendo al menu principal...");
                                        return 1;
                                    } else {
                                        System.out.println("Opcion invalida.");
                                    }
                                }
                            }
                        }
                    }

                } else {
                    // ---------------------------------------------------------
                    // BLOQUE 2: El usuario eligió iniciar con Clase Premium
                    // ---------------------------------------------------------
                    int disponiblesPrem = util.asientosDisponiblesClasePremium(idVuelo, conn);
                    System.out.println("Asientos Clase Premium disponibles: " + disponiblesPrem);
                    System.out.print("Cuantos asientos en Clase Premium desea reservar? ");
                    cantidadPremium = sc.nextInt();
                    sc.nextLine();
                    if (cantidadPremium > disponiblesPrem) {
                        if (disponiblesPrem > 0) {
                            System.out.println("No hay suficientes asientos en Clase Premium. Disponibles: " + disponiblesPrem);
                            while (true) {
                                System.out.println("Seleccione una opcion:");
                                System.out.println("1. Ajustar a los asientos disponibles (" + disponiblesPrem + ")");
                                System.out.println("2. Salir a lista de vuelos");
                                System.out.println("3. Salir al menu principal");
                                opc = sc.nextInt();
                                sc.nextLine();
                                if (opc == 1) {
                                    cantidadPremium = disponiblesPrem;
                                    System.out.println("Se ajustó la cantidad a " + cantidadPremium);
                                    break;
                                } else if (opc == 2) {
                                    System.out.println("Regresando a lista de vuelos...");
                                    return 2;
                                } else if (opc == 3) {
                                    System.out.println("Saliendo al menu principal...");
                                    procesoCancelado = true;
                                    break;
                                } else {
                                    System.out.println("Opcion invalida.");
                                }
                            }
                        } else {
                            System.out.println("No hay asientos en Clase Premium disponibles (0).");
                            while (true) {
                                System.out.println("Seleccione una opcion:");
                                System.out.println("1. Salir a lista de vuelos");
                                System.out.println("2. Salir al menu principal");
                                opc = sc.nextInt();
                                sc.nextLine();
                                if (opc == 1) {
                                    System.out.println("Regresando a lista de vuelos...");
                                    return 2;
                                } else if (opc == 2) {
                                    System.out.println("Saliendo al menu principal...");
                                    procesoCancelado = true;
                                    break;
                                } else {
                                    System.out.println("Opcion invalida.");
                                }
                            }
                        }
                    }

                    if (procesoCancelado) return 1;

                    System.out.println("Desea agregar asientos en Clase Economica tambien?");
                    do {
                        System.out.println("1 = SI, 2 = NO");
                        opc = sc.nextInt();
                        sc.nextLine();
                    }while (opc!=1 && opc!=2);
                    if (opc == 1) {
                        int disponiblesEco2 = util.asientosDisponiblesClaseEconomica(idVuelo, conn);
                        System.out.println("Asientos economicos disponibles: " + disponiblesEco2);
                        System.out.print("Cuantos asientos en Clase Economica desea reservar? ");
                        cantidadEconomica = sc.nextInt();
                        sc.nextLine();
                        if (cantidadEconomica < 0) cantidadEconomica = 0;
                        if (cantidadEconomica > disponiblesEco2) {
                            if (disponiblesEco2 > 0) {
                                System.out.println("No hay suficientes asientos economicos. Disponibles: " + disponiblesEco2);
                                while (true) {
                                    System.out.println("Seleccione una opcion:");
                                    System.out.println("1. Ajustar a los asientos disponibles (" + disponiblesEco2 + ")");
                                    System.out.println("2. Seguir la compra sin los asientos economicos");
                                    System.out.println("3. Salir a lista de vuelos");
                                    System.out.println("4. Salir al menu principal");
                                    opc = sc.nextInt();
                                    sc.nextLine();
                                    if (opc == 1) {
                                        cantidadEconomica = disponiblesEco2;
                                        System.out.println("Se ajustó la cantidad a " + cantidadEconomica);
                                        break;
                                    } else if (opc == 2) {
                                        cantidadEconomica = 0;
                                        System.out.println("Se continúa sin asientos económicos.");
                                        break;
                                    } else if (opc == 3) {
                                        System.out.println("Regresando a lista de vuelos...");
                                        return 2;
                                    } else if (opc == 4) {
                                        System.out.println("Saliendo al menu principal...");
                                        return 1;
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
                                    opc = sc.nextInt();
                                    sc.nextLine();
                                    if (opc == 1) {
                                        cantidadEconomica = 0;
                                        System.out.println("Se continúa sin asientos económicos.");
                                        break;
                                    } else if (opc == 2) {
                                        System.out.println("Regresando a lista de vuelos...");
                                        return 2;
                                    } else if (opc == 3) {
                                        System.out.println("Saliendo al menu principal...");
                                        return 1;
                                    } else {
                                        System.out.println("Opcion invalida.");
                                    }
                                }
                            }
                        }
                    }

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

                System.out.print("Confirma la compra? 1 = SI, 2 = NO : ");
                opc = sc.nextInt();
                sc.nextLine();
                if (opc != 1) {
                    System.out.println("Compra cancelada.");
                    return 1;
                }
                cliente1.setAsientosClaseEconomica(cantidadEconomica);
                cliente1.setAsientosClasePremium(cantidadPremium);
                cliente1.setAsientosComprados(totalAsientosSeleccionados);

                int nuevoIdCliente = util.insetarDatos(cliente1, conn);
                if (nuevoIdCliente <= 0) {
                    System.out.println("No se pudo registrar el cliente. No se realiza la reserva.");
                    return 1;
                }
                return 0; // Exito
        }
}
