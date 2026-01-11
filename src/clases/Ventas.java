package clases;

import java.sql.Timestamp;

/**
 * Representa una venta (registro de compra de asientos).
 * <p>
 * Explicación simple:
 * - Guarda referencia a cliente e viaje (por id).
 * - Indica cuántos asientos se compraron en total y por clase.
 * - Guarda el total pagado y la fecha de la venta.
 */
public class Ventas {
    private int idVenta;
    private final int idCliente;
    private final int idViaje;
    private final int cantidadAsientos;
    private final int asientosClaseEconomica; // Nuevo campo
    private final int asientosClasePremium;   // Nuevo campo
    private final double totalVenta;
    private Timestamp fecha;

    /**
     * Constructor completo (incluye id y fecha).
     */
    public Ventas(int idVenta, int idCliente, int idViaje, int cantidadAsientos, int asientosClaseEconomica, int asientosClasePremium, double totalVenta, Timestamp fecha) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.idViaje = idViaje;
        this.cantidadAsientos = cantidadAsientos;
        this.asientosClaseEconomica = asientosClaseEconomica;
        this.asientosClasePremium = asientosClasePremium;
        this.totalVenta = totalVenta;
        this.fecha = fecha;
    }

    /**
     * Constructor para insertar una venta (sin id ni fecha).
     */
    public Ventas(int idCliente, int idViaje, int cantidadAsientos, int asientosClaseEconomica, int asientosClasePremium, double totalVenta) {
        this.idCliente = idCliente;
        this.idViaje = idViaje;
        this.cantidadAsientos = cantidadAsientos;
        this.asientosClaseEconomica = asientosClaseEconomica;
        this.asientosClasePremium = asientosClasePremium;
        this.totalVenta = totalVenta;
    }


    public int getIdCliente() {
        return idCliente;
    }


    public int getIdViaje() {
        return idViaje;
    }


    public int getCantidadAsientos() {
        return cantidadAsientos;
    }


    public int getAsientosClaseEconomica() {
        return asientosClaseEconomica;
    }

    public int getAsientosClasePremium() {
        return asientosClasePremium;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    /**
     * Representación en texto de la venta. Muestra id, cliente, viaje, cantidades y fecha.
     */
    public String toString() {
        return "Ventas{" + "idVenta=" + idVenta + ", idCliente=" + idCliente + ", idViaje=" + idViaje + ", cantidadAsientos=" + cantidadAsientos + ", asientosClaseEconomica=" + asientosClaseEconomica + ", asientosClasePremium=" + asientosClasePremium + ", totalVenta=" + totalVenta + ", fecha=" + fecha + '}';
    }
}
