package clases;

public class Producto {
    private String nombreproducto;
    private int cantidad;
    private double precio;
    private int id;

    public Producto(String nombreproducto, int cantidad, double precio) {
        this.nombreproducto = nombreproducto;
        this.cantidad = cantidad;
        this.precio = precio;
    }
    public Producto(int id, String nombreproducto, int cantidad, double precio) {
        this.id = id;
        this.nombreproducto = nombreproducto;
        this.cantidad = cantidad;
        this.precio = precio;
    }
    public int getId() {
        return id;
    }
    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }


}
