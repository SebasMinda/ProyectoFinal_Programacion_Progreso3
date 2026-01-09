package clases;

public class Viajes {
    private int id;
    private String destino;
    private String origen;
    private int cantidadTotal;
    private int asientosEconomica;
    private int asientosPremium;
    private double precioEconomica;
    private double precioPremium;
    private double ganancias;

    public Viajes(int id, String destino, String origen, int cantidadTotal, int asientosEconomica, int asientosPremium, double precioEconomica, double precioPremium) {
        this.id = id;
        this.destino = destino;
        this.origen = origen;
        this.cantidadTotal = cantidadTotal;
        this.asientosEconomica = asientosEconomica;
        this.asientosPremium = asientosPremium;
        this.precioEconomica = precioEconomica;
        this.precioPremium = precioPremium;
    }

    public Viajes() {
    }

    // Constructor sin id (para insertar nuevos viajes) usando solo 'cantidad' por compatibilidad
    public Viajes(String destino, int cantidadTotal, double ganancias) {
        this.destino = destino;
        this.cantidadTotal = cantidadTotal;
        this.asientosEconomica = cantidadTotal;
        this.asientosPremium = 0;
        this.ganancias = ganancias;
    }

    // Nuevo constructor que permite especificar ambas categorías
    public Viajes(String destino, int asientosEconomica, int asientosPremium, double ganancias) {
        this.destino = destino;
        this.asientosEconomica = asientosEconomica;
        this.asientosPremium = asientosPremium;
        this.cantidadTotal = asientosEconomica + asientosPremium;
        this.ganancias = ganancias;
    }

    // Constructor con id, destino, cantidad, ganancias (usado por Utilidades.obtenerDatosViaje)
    public Viajes(int id, String destino, int cantidadTotal, double ganancias) {
        this.id = id;
        this.destino = destino;
        this.cantidadTotal = cantidadTotal;
        this.asientosEconomica = cantidadTotal;
        this.asientosPremium = 0;
        this.ganancias = ganancias;
    }

    // Constructor completo con origen opcional
    public Viajes(int id, String destino, String origen, int cantidadTotal, double ganancias) {
        this.id = id;
        this.destino = destino;
        this.origen = origen;
        this.cantidadTotal = cantidadTotal;
        this.asientosEconomica = cantidadTotal;
        this.asientosPremium = 0;
        this.ganancias = ganancias;
    }

    public Viajes(int id, String destino, String origen, int cantidadTotal, int asientosEconomica, int asientosPremium, double precioEconomica, double precioPremium, double ganancias) {
        this.id = id;
        this.destino = destino;
        this.origen = origen;
        this.cantidadTotal = cantidadTotal;
        this.asientosEconomica = asientosEconomica;
        this.asientosPremium = asientosPremium;
        this.precioEconomica = precioEconomica;
        this.precioPremium = precioPremium;
        this.ganancias = ganancias;
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

    public int getCantidadTotal() {
        // Mantener compatibilidad: cantidad es la suma de categorías
        if (cantidadTotal == 0) {
            return asientosEconomica + asientosPremium;
        }
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public int getAsientosEconomica() {
        return asientosEconomica;
    }

    public void setAsientosEconomica(int asientosEconomica) {
        this.asientosEconomica = asientosEconomica;
        this.cantidadTotal = this.asientosEconomica + this.asientosPremium;
    }

    public int getAsientosPremium() {
        return asientosPremium;
    }

    public void setAsientosPremium(int asientosPremium) {
        this.asientosPremium = asientosPremium;
        this.cantidadTotal = this.asientosEconomica + this.asientosPremium;
    }

    public double getPrecioEconomica() {
        return precioEconomica;
    }

    public void setPrecioEconomica(double precioEconomica) {
        this.precioEconomica = precioEconomica;
    }

    public double getPrecioPremium() {
        return precioPremium;
    }

    public void setPrecioPremium(double precioPremium) {
        this.precioPremium = precioPremium;
    }

    public double getGanancias() {
        return ganancias;
    }

    public void setGanancias(double ganancias) {
        this.ganancias = ganancias;
    }

    public String toString() {
        return "Viaje{" +
                "id=" + id +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", cantidad(total)=" + (asientosEconomica + asientosPremium) +
                ", asientosVip=" + asientosPremium +
                ", asientosnormales=" + asientosEconomica +
                ", precioEconomica=" + precioEconomica +
                ", precioPremium=" + precioPremium +
                ", ganancias=" + ganancias +
                '}'+"\n";
    }
    public String toString2() {
        return "Viaje{" +
                "id=" + id +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", cantidad(total)=" + (asientosEconomica + asientosPremium) +
                ", asientosVip=" + asientosPremium +
                ", asientosnormales=" + asientosEconomica +
                ", precioEconomica=" + precioEconomica +
                ", precioPremium=" + precioPremium +
                '}'+"\n";
    }

}
