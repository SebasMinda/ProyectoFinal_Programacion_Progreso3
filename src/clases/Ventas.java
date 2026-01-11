package clases;

import java.sql.Timestamp;

public class Ventas {
    private int idVenta;
    private int idCliente;
    private int idViaje;
    private int cantidadAsientos;
    private int asientosClaseEconomica; // Nuevo campo
    private int asientosClasePremium;   // Nuevo campo
    private double totalVenta;
    private Timestamp fecha;

    // Constructor completo
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

    // Constructor sin ID (para inserción)
    public Ventas(int idCliente, int idViaje, int cantidadAsientos, int asientosClaseEconomica, int asientosClasePremium, double totalVenta) {
        this.idCliente = idCliente;
        this.idViaje = idViaje;
        this.cantidadAsientos = cantidadAsientos;
        this.asientosClaseEconomica = asientosClaseEconomica;
        this.asientosClasePremium = asientosClasePremium;
        this.totalVenta = totalVenta;
    }

    public Ventas() {
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public int getCantidadAsientos() {
        return cantidadAsientos;
    }

    public void setCantidadAsientos(int cantidadAsientos) {
        this.cantidadAsientos = cantidadAsientos;
    }

    public int getAsientosClaseEconomica() {
        return asientosClaseEconomica;
    }

    public void setAsientosClaseEconomica(int asientosClaseEconomica) {
        this.asientosClaseEconomica = asientosClaseEconomica;
    }

    public int getAsientosClasePremium() {
        return asientosClasePremium;
    }

    public void setAsientosClasePremium(int asientosClasePremium) {
        this.asientosClasePremium = asientosClasePremium;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String toString() {
        return "Ventas{" +
                "idVenta=" + idVenta +
                ", idCliente=" + idCliente +
                ", idViaje=" + idViaje +
                ", cantidadAsientos=" + cantidadAsientos +
                ", asientosClaseEconomica=" + asientosClaseEconomica +
                ", asientosClasePremium=" + asientosClasePremium +
                ", totalVenta=" + totalVenta +
                ", fecha=" + fecha +
                '}';
    }
}
