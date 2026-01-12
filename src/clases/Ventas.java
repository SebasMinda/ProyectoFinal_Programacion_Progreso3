package clases;

import java.sql.Timestamp;

/**
 * Representa una venta realizada en el sistema (compra de asientos).
 *
 * Explicación simple (pero clara):
 * - Guarda la referencia al cliente y al viaje mediante sus IDs.
 * - Guarda cuántos asientos se compraron en total y por cada clase (Económica y Premium).
 * - Guarda el total pagado por la compra.
 * - Guarda la fecha y hora exacta en la que se realizó la venta.
 *
 * Esta clase se usa tanto para:
 * - Insertar una nueva venta en la base de datos.
 * - Mostrar ventas ya registradas (listado de ventas).
 */
public class Ventas {

    // ID único de la venta (normalmente generado por la base de datos)
    private int idVenta;

    // ID del cliente que realizó la compra
    private int idCliente;

    // ID del viaje (vuelo) asociado a la venta
    private int idViaje;

    // Datos históricos del cliente en el momento de la venta
    private String nombre;
    private String apellido;
    private String identificacion;

    // Cantidad total de asientos comprados (eco + premium)
    private int cantidadAsientos;

    // Cantidad de asientos comprados en clase Económica
    private int asientosClaseEconomica;

    // Cantidad de asientos comprados en clase Premium
    private int asientosClasePremium;

    // Monto total pagado por la venta
    private double totalVenta;

    // Fecha y hora exacta en la que se registró la venta
    private Timestamp fecha;

       /**
     * Constructor simplificado.
     *
     * Se utiliza cuando:
     * - Se va a insertar una nueva venta en la base de datos.
     * - El idVenta y la fecha aún no existen (los genera la BD).
     */
    public Ventas(int idCliente,
                  int idViaje,
                  int cantidadAsientos,
                  int asientosClaseEconomica,
                  int asientosClasePremium,
                  double totalVenta) {

        this.idCliente = idCliente;
        this.idViaje = idViaje;
        this.cantidadAsientos = cantidadAsientos;
        this.asientosClaseEconomica = asientosClaseEconomica;
        this.asientosClasePremium = asientosClasePremium;
        this.totalVenta = totalVenta;
    }
    /**
     * Constructor completo.
     *
     * Se utiliza normalmente cuando:
     * - Se leen ventas desde la base de datos.
     * - Ya se conoce el idVenta y la fecha registrada.
     */
    public Ventas(int idVenta,
                  int idCliente,
                  int idViaje,
                  String nombre,
                  String apellido,
                  String identificacion,
                  int cantidadAsientos,
                  int asientosClaseEconomica,
                  int asientosClasePremium,
                  double totalVenta,
                  Timestamp fecha) {

        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.idViaje = idViaje;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.cantidadAsientos = cantidadAsientos;
        this.asientosClaseEconomica = asientosClaseEconomica;
        this.asientosClasePremium = asientosClasePremium;
        this.totalVenta = totalVenta;
        this.fecha = fecha;
    }
    // =========================
    //         GETTERS
    // =========================

    /**
     * Obtiene el ID del cliente que realizó la compra.
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Obtiene el ID del viaje asociado a la venta.
     */
    public int getIdViaje() {
        return idViaje;
    }

    /**
     * Obtiene la cantidad total de asientos comprados.
     */
    public int getCantidadAsientos() {
        return cantidadAsientos;
    }

    /**
     * Obtiene la cantidad de asientos comprados en clase Económica.
     */
    public int getAsientosClaseEconomica() {
        return asientosClaseEconomica;
    }

    /**
     * Obtiene la cantidad de asientos comprados en clase Premium.
     */
    public int getAsientosClasePremium() {
        return asientosClasePremium;
    }

    /**
     * Obtiene el total pagado por la venta.
     */
    public double getTotalVenta() {
        return totalVenta;
    }

    // =========================
    //        REPRESENTACIÓN
    // =========================

    /**
     * Representación en texto de la venta.
     *
     * Útil para:
     * - Mostrar la lista de ventas en consola.
     * - Depuración del sistema.
     *
     * Muestra:
     * - ID de la venta
     * - Cliente y viaje asociados
     * - Cantidad total y por clase
     * - Total pagado
     * - Fecha de la venta
     */

    public String toString() {
        return "Ventas{" +
                "idVenta=" + idVenta +
                ", idCliente=" + idCliente +
                ", idViaje=" + idViaje +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", cantidadAsientos=" + cantidadAsientos +
                ", asientosClaseEconomica= " + asientosClaseEconomica +
                " asientosClasePremium= " + asientosClasePremium +
                ", totalVenta=" + totalVenta +
                ", fecha=" + fecha +
                '}';
    }
}
