package clases;

import java.util.List;

public class Ventas
{
private int cantidadproducto;
private float totalVenta;
private List<String> productosVendidos;

    public Ventas(int cantidadproducto, float totalVenta) {
        this.cantidadproducto = cantidadproducto;
        this.totalVenta = totalVenta;
    }

    public int getCantidadproducto() {
        return cantidadproducto;
    }

    public void setCantidadproducto(int cantidadproducto) {
        this.cantidadproducto = cantidadproducto;
    }

    public float getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(float totalVenta) {
        this.totalVenta = totalVenta;
    }
}
