import clases.Cliente;
import clases.Viajes;
import clases.Utilidades;

import java.sql.Connection;
import java.util.Scanner;

/**
 * Clase principal del programa (punto de entrada).
 *
 * Explicación simple:
 * - Conecta a la base de datos.
 * - Muestra un menú principal con 3 opciones: cliente, administrador o salir.
 * - Cliente: ingresa datos personales, ve vuelos y reserva asientos.
 * - Admin (clave 1234): gestiona viajes (crear/eliminar/listar) y revisa clientes/ventas.
 */
public class ProyectoFinal {

    public static void main(String[] args) {

        // Objeto con métodos de BD (insertar, listar, reservas, etc.)
        Utilidades util = new Utilidades();

        // Conexión a la base de datos (se usa durante todo el programa)
        Connection conn = null;

        // Scanner para leer entradas por consola
        Scanner sc = new Scanner(System.in);

        // Intentar conectar a la base de datos
        conn = util.getConnection();

        // Mensaje simple para confirmar si hay conexión
        if (conn != null) {
            System.out.println("Conectados ..!!");
        } else {
            System.out.println("NO Conectado ...!!");
        }

        // Métodos de prueba: borran y reinician tablas (por eso se dejan comentados)
        // NOTA: si los activas, se pierde toda la información guardada.
        // util.resetearventas(conn);
        // util.resetearviajes(conn);
        // util.resetearClientes(conn);

        int opc; // variable reutilizada para leer opciones del menú

        // Objeto Cliente reutilizable para guardar los datos ingresados en consola
        Cliente cliente1 = new Cliente();

        // Ciclo principal: se repite hasta que el usuario elija "Salir"
        do {
            System.out.println("\n-----Menu Principal-----");
            System.out.println("Seleccione una opcion:");
            System.out.println("1. Ingresar como cliente");
            System.out.println("2. Ingresar como administrador");
            System.out.println("3. Salir");
            System.out.print("Opcion: ");

            // Leer opción del usuario (número)
            opc = sc.nextInt();
            sc.nextLine(); // Limpia el buffer (evita que nextLine() lea un salto de línea pendiente)

            switch (opc) {
                case 1:
                    // =========================
                    //        MODO CLIENTE
                    // =========================
                    System.out.println("Ingresando como cliente...");

                    // Se piden datos básicos del cliente para registrarlos en BD luego
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

                    // Permite volver a mostrar la lista de vuelos si el usuario lo elige
                    int volverALista;
                    do {
                        // Si reservarAsientosCliente retorna 1 => vuelve a listar vuelos
                        volverALista = reservarAsientosCliente(sc, util, conn, cliente1);
                    } while (volverALista == 1);

                    break;

                case 2:
                    // =========================
                    //        MODO ADMIN
                    // =========================
                    System.out.println("\nIngresando como administrador...");

                    // Se valida contraseña (simple, hardcodeada)
                    System.out.println("Ingrese la contraseña de administrador: ");
                    int password = Integer.parseInt(sc.nextLine());

                    // Si la contraseña no coincide, vuelve al menú principal
                    if (password != 1234) {
                        System.out.println("Contraseña incorrecta. Volviendo al menu principal.");
                        break;
                    } else {
                        System.out.println("Contraseña correcta. Acceso concedido.");

                        // Menú interno del administrador (se repite hasta que elija volver)
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

                            // Reutilizamos opc para leer opción del admin
                            opc = sc.nextInt();
                            sc.nextLine(); // Limpia buffer

                            switch (opc) {
                                case 1:
                                    // Muestra clientes registrados
                                    System.out.println("Lista de Clientes");
                                    util.obtenerDatos(conn);
                                    System.out.println();
                                    break;

                                case 2:
                                    // Registrar un nuevo viaje
                                    System.out.println("Registrar Viaje");

                                    Viajes viaje1 = new Viajes();

                                    // Se pide origen y destino
                                    System.out.println("Ingrese origen del viaje: ");
                                    String origenViaje = sc.nextLine();
                                    viaje1.setOrigen(origenViaje);

                                    System.out.println("Ingrese destino del viaje: ");
                                    String destinoViaje = sc.nextLine();
                                    viaje1.setDestino(destinoViaje);

                                    // Se piden asientos por clase
                                    System.out.println("Ingrese cantidad de asientos vip del viaje: ");
                                    int asientosClasePremium = sc.nextInt();
                                    viaje1.setAsientosClasePremium(asientosClasePremium);

                                    System.out.println("Ingrese cantidad de asientos economicos del viaje: ");
                                    int asientosClaseEconomica = sc.nextInt();
                                    viaje1.setAsientosClaseEconomica(asientosClaseEconomica);

                                    // Se piden precios por clase
                                    System.out.println("Ingrese precio asientos vip del viaje: ");
                                    double precioPremium = sc.nextDouble();
                                    viaje1.setPrecioPremium(precioPremium);

                                    System.out.println("Ingrese precio asientos economicos del viaje: ");
                                    double precioEconomica = sc.nextDouble();
                                    viaje1.setPrecioEconomica(precioEconomica);

                                    // Total de asientos es la suma de ambas clases
                                    viaje1.setCantidadTotal(asientosClaseEconomica + asientosClasePremium);

                                    // Insertar viaje en la BD
                                    util.insetarDatosViaje(viaje1, conn);

                                    sc.nextLine(); // Limpia buffer
                                    break;

                                case 3:
                                    // Eliminar viaje por ID
                                    System.out.println("Eliminar Viaje");
                                    System.out.println("Ingrese el ID del viaje a eliminar: ");
                                    int id = sc.nextInt();

                                    util.eliminaDatosViaje(id, conn);

                                    sc.nextLine(); // Limpia buffer
                                    break;

                                case 4:
                                    // Listar viajes (vista admin, incluye ganancias)
                                    System.out.println("Lista de Viajes");
                                    util.obtenerDatosViaje(conn);
                                    break;

                                case 5:
                                    // Admin también puede reservar (mismo flujo que el cliente)
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
                                    // Mostrar lista de ventas registradas
                                    System.out.println("Lista de Ventas");
                                    util.obtenerVentas(conn);
                                    break;

                                case 7:
                                    // Salir del menú admin y volver al principal
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
                    // Termina el programa
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opcion no valida, intente de nuevo.");
                    break;
            }

        } while (opc != 3);
    }

    /**
     * Flujo para reservar asientos (sirve tanto para cliente como para admin).
     *
     * Retorna:
     * - 1 si el usuario quiere volver a ver la lista de vuelos (loop en main).
     * - 0 si el usuario quiere salir al menú principal.
     */
    public static int reservarAsientosCliente(Scanner sc, Utilidades util, Connection conn, Cliente cliente1) {

        // 1) Mostrar viajes disponibles
        System.out.println("Lista de Vuelos disponibles:");

        // Devuelve 1 si hay vuelos, 0 si no hay
        int existenVuelos = util.obtenerDatosViajeCliente(conn);

        // Si no hay vuelos, salimos al menú principal
        if (existenVuelos == 0) {
            System.out.println("No hay vuelos disponibles actualmente.");
            return 0;
        }

        // 2) Elegir vuelo por ID
        System.out.print("Ingrese el ID del vuelo que desea reservar: ");
        int idVuelo = sc.nextInt();
        sc.nextLine(); // limpiar buffer

        int claseInicial; // clase con la que inicia la reserva (eco o premium)
        int opc;          // variable para opciones en sub-menús

        // Cantidad de asientos que el usuario quiere comprar (por clase)
        int cantidadEconomica = 0;
        int cantidadPremium = 0;

        // Bandera para cortar el flujo si el usuario cancela
        int procesoCancelado = 0;

        // 3) Elegir la clase inicial (solo permite 1 o 2)
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

        // 4) Dependiendo de la clase inicial, primero se pide esa clase
        switch (claseInicial) {
            case 1:
                // -------------------------
                //   INICIA EN ECONÓMICA
                // -------------------------

                // Consultar disponibilidad en económica
                int disponiblesEco = util.asientosDisponiblesClaseEconomica(idVuelo, conn);
                System.out.println("Asientos Clase Economica disponibles: " + disponiblesEco);

                // Pedir cantidad en económica
                System.out.print("Cuantos asientos en Clase Economica desea reservar? ");
                cantidadEconomica = sc.nextInt();
                sc.nextLine();

                /*
                 * Validación:
                 * - Si el usuario pide más asientos de los disponibles, se le da un menú para decidir qué hacer.
                 * - Si disponiblesEco = 0, se le da un menú distinto (no puede ajustar a 0 como compra "válida").
                 */
                if (cantidadEconomica > disponiblesEco) {

                    // Caso 1: sí había asientos, pero no los suficientes
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
                                // Ajusta la compra al máximo posible
                                cantidadEconomica = disponiblesEco;
                                System.out.println("Se ajustó la cantidad a " + cantidadEconomica);

                            } else if (opc == 2) {
                                // Volver a listar vuelos
                                System.out.println("Regresando a lista de vuelos...");
                                return 1;

                            } else if (opc == 3) {
                                // Cancelar y volver al menú principal
                                System.out.println("Saliendo al menu principal...");
                                procesoCancelado = 1;
                                break;

                            } else {
                                System.out.println("Opcion invalida.");
                            }

                        } while (opc != 1 && opc != 2 && opc != 3);

                    } else {
                        // Caso 2: no hay ningún asiento económico disponible
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

                // Si el usuario canceló, se corta aquí
                if (procesoCancelado == 1) return 0;

                // Pregunta extra: agregar asientos de la otra clase (premium)
                System.out.println("Desea agregar asientos en Clase Premium también?");
                do {
                    System.out.println("1 = SI, 2 = NO");
                    System.out.print("Seleccione una opcion: ");
                    opc = sc.nextInt();
                    sc.nextLine();
                } while (opc != 1 && opc != 2);

                // Si sí quiere premium, se pide y se valida
                if (opc == 1) {
                    int disponiblesPrem = util.asientosDisponiblesClasePremium(idVuelo, conn);
                    System.out.println("Asientos premium disponibles: " + disponiblesPrem);

                    System.out.print("Cuantos asientos en Clase Premium desea reservar? ");
                    cantidadPremium = sc.nextInt();
                    sc.nextLine();

                    // Si el usuario escribe negativo, se fuerza a 0 (evita errores en compra)
                    if (cantidadPremium < 0) {
                        cantidadPremium = 0;
                    }

                    // Si pide más de lo disponible, mostrar opciones
                    if (cantidadPremium > disponiblesPrem) {

                        // Caso: hay algunos disponibles pero no los suficientes
                        if (disponiblesPrem > 0) {
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
                                    // Ajusta a lo máximo posible
                                    cantidadPremium = disponiblesPrem;
                                    System.out.println("Se ajustó la cantidad a " + cantidadPremium);
                                    break;

                                } else if (opc == 2) {
                                    // Ignora premium y sigue con económica
                                    cantidadPremium = 0;
                                    System.out.println("Se continúa sin asientos premium.");
                                    break;

                                } else if (opc == 3) {
                                    // Repetir proceso desde lista de vuelos
                                    System.out.println("Regresando a lista de vuelos...");
                                    return 1;

                                } else if (opc == 4) {
                                    // Sale al menú principal
                                    System.out.println("Saliendo al menu principal...");
                                    return 0;

                                } else {
                                    System.out.println("Opcion invalida.");
                                }
                            }

                        } else {
                            // Caso: no hay premium disponible
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
                // -------------------------
                //    INICIA EN PREMIUM
                // -------------------------

                int disponiblesPrem = util.asientosDisponiblesClasePremium(idVuelo, conn);
                System.out.println("Asientos Clase Premium disponibles: " + disponiblesPrem);

                System.out.print("Cuantos asientos en Clase Premium desea reservar? ");
                cantidadPremium = sc.nextInt();
                sc.nextLine();

                /*
                 * Misma lógica de validación que la clase económica:
                 * - Si pide más de lo disponible, se le da un menú.
                 * - Si no hay asientos, se le da un menú de salida/cancelación.
                 */
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
                                return 1;

                            } else if (opc == 3) {
                                System.out.println("Saliendo al menu principal...");
                                procesoCancelado = 1;
                                break;

                            } else {
                                System.out.println("Opcion invalida.");
                            }

                        } while (opc != 1 && opc != 2 && opc != 3);

                    } else {
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

                // Pregunta extra: agregar económica
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

                    // Evitar negativos
                    if (cantidadEconomica < 0) {
                        cantidadEconomica = 0;
                    }

                    if (cantidadEconomica > disponiblesEco2) {

                        if (disponiblesEco2 > 0) {
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

        // =========================
        //   RESUMEN Y CONFIRMACIÓN
        // =========================

        int totalAsientosSeleccionados = cantidadEconomica + cantidadPremium;

        // Obtener precios unitarios desde BD para calcular total real
        double precioEcoUnit = util.obtenerPrecioPorClase(idVuelo, 1, conn);
        double precioPremUnit = util.obtenerPrecioPorClase(idVuelo, 2, conn);

        // Total a pagar = precioEco * cantEco + precioPrem * cantPrem
        double totalPagar = (precioEcoUnit * cantidadEconomica) + (precioPremUnit * cantidadPremium);

        System.out.println("\nResumen de la reserva:");
        System.out.println("Asientos Clase Economica: " + cantidadEconomica + " Precio Unitario: " + precioEcoUnit);
        System.out.println("Asientos Clase Premium:   " + cantidadPremium + " Precio Unitario: " + precioPremUnit);
        System.out.println("Total asientos: " + totalAsientosSeleccionados);
        System.out.println("Total a pagar: " + totalPagar);

        // Confirmación final antes de guardar en BD
        do {
            System.out.print("Confirma la compra? 1 = SI, 2 = NO : ");
            opc = sc.nextInt();
            sc.nextLine();

            if (opc == 2) {
                // Si cancela aquí, no se guarda nada
                System.out.println("Compra cancelada.");
                return 0;
            }
        } while (opc != 1 && opc != 2);

        // Guardar en el objeto cliente lo que se compró (para insertar en BD)
        cliente1.setAsientosClaseEconomica(cantidadEconomica);
        cliente1.setAsientosClasePremium(cantidadPremium);
        cliente1.setAsientosComprados(totalAsientosSeleccionados);
        cliente1.setPrecio(totalPagar);

        System.out.println("Venta registrada");

        // 1) Inserta/actualiza datos del cliente (y recupera su id)
        util.insetarDatos(cliente1, conn);

        // 2) Inserta la venta y actualiza el viaje (asientos y ganancias) con transacción
        util.insertarDatosVenta(idVuelo, cantidadEconomica, cantidadPremium, cliente1, conn);

        // Finaliza flujo volviendo al menú principal
        return 0;
    }
}
