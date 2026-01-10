package clases;

import java.sql.Timestamp;

public class Ventas {
    private int idVenta;
    private int idCliente;
    private int idViaje;
    private int cantidadAsientos;
    private String clase; // "economica" o "premium"
    private double totalVenta;
    private Timestamp fechaVenta;

    // Constructor completo
    public Ventas(int idVenta, int idCliente, int idViaje, int cantidadAsientos, String clase, double totalVenta, Timestamp fechaVenta) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.idViaje = idViaje;
        this.cantidadAsientos = cantidadAsientos;
        this.clase = clase;
        this.totalVenta = totalVenta;
        this.fechaVenta = fechaVenta;
    }

    // Constructor sin id ni fecha (para insertar)
    public Ventas(int idCliente, int idViaje, int cantidadAsientos, String clase, double totalVenta) {
        this(0, idCliente, idViaje, cantidadAsientos, clase, totalVenta, null);
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

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public Timestamp getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Timestamp fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String toString() {
        return "Venta{" +
                "idVenta=" + idVenta +
                ", idCliente=" + idCliente +
                ", idViaje=" + idViaje +
                ", cantidadAsientos=" + cantidadAsientos +
                ", clase='" + clase + '\'' +
                ", totalVenta=" + totalVenta +
                ", fechaVenta=" + fechaVenta +
                '}';
    }
}
