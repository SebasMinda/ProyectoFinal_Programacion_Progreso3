package clases;

import java.util.List;

public class Ventas {
    private int cantidadViajes;
    private float totalVenta;
    private List<String> viajesVendidos;

    public Ventas(int cantidadViajes, float totalVenta) {
        this.cantidadViajes = cantidadViajes;
        this.totalVenta = totalVenta;
    }

    public int getCantidadViajes() {
        return cantidadViajes;
    }

    public void setCantidadViajes(int cantidadViajes) {
        this.cantidadViajes = cantidadViajes;
    }

    public float getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(float totalVenta) {
        this.totalVenta = totalVenta;
    }

    public List<String> getViajesVendidos() {
        return viajesVendidos;
    }

    public void setViajesVendidos(List<String> viajesVendidos) {
        this.viajesVendidos = viajesVendidos;
    }
}
