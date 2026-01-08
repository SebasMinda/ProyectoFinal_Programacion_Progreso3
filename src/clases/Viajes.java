package clases;

public class Viajes {
    private int id;
    private String destino;
    private String origen;
    private int cantidad;
    private double precio;

    public Viajes() {
    }

    // Constructor sin id (para insertar nuevos viajes)
    public Viajes(String destino, int cantidad, double precio) {
        this.destino = destino;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // Constructor con id, destino, cantidad, precio (usado por Utilidades.obtenerDatosViaje)
    public Viajes(int id, String destino, int cantidad, double precio) {
        this.id = id;
        this.destino = destino;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // Constructor completo con origen opcional
    public Viajes(int id, String destino, String origen, int cantidad, double precio) {
        this.id = id;
        this.destino = destino;
        this.origen = origen;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
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

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String toString() {
        return "Viaje{" +
                "id=" + id +
                ", destino='" + destino + '\'' +
                ", origen='" + origen + '\'' +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                '}';
    }

}
