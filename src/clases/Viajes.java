package clases;

public class Viajes {
    private int id;
    private String destino;
    private String origen;
    private int cantidadTotal;
    private int asientosClaseEconomica;
    private int asientosClasePremium;
    private double precioEconomica;
    private double precioPremium;
    private double ganancias;

    public Viajes(int id, String destino, String origen, int cantidadTotal, int asientosClaseEconomica, int asientosClasePremium, double precioEconomica, double precioPremium) {
        this.id = id;
        this.destino = destino;
        this.origen = origen;
        this.cantidadTotal = cantidadTotal;
        this.asientosClaseEconomica = asientosClaseEconomica;
        this.asientosClasePremium = asientosClasePremium;
        this.precioEconomica = precioEconomica;
        this.precioPremium = precioPremium;
    }

    public Viajes() {
    }

    // Constructor sin id (para insertar nuevos viajes) usando solo 'cantidad' por compatibilidad
    public Viajes(String destino, int cantidadTotal, double ganancias) {
        this.destino = destino;
        this.cantidadTotal = cantidadTotal;
        this.asientosClaseEconomica = cantidadTotal;
        this.asientosClasePremium = 0;
        this.ganancias = ganancias;
    }

    // Nuevo constructor que permite especificar ambas categorías
    public Viajes(String destino, int asientosClaseEconomica, int asientosClasePremium, double ganancias) {
        this.destino = destino;
        this.asientosClaseEconomica = asientosClaseEconomica;
        this.asientosClasePremium = asientosClasePremium;
        this.cantidadTotal = asientosClaseEconomica + asientosClasePremium;
        this.ganancias = ganancias;
    }

    // Constructor con id, destino, cantidad, ganancias (usado por Utilidades.obtenerDatosViaje)
    public Viajes(int id, String destino, int cantidadTotal, double ganancias) {
        this.id = id;
        this.destino = destino;
        this.cantidadTotal = cantidadTotal;
        this.asientosClaseEconomica = cantidadTotal;
        this.asientosClasePremium = 0;
        this.ganancias = ganancias;
    }

    // Constructor completo con origen opcional
    public Viajes(int id, String destino, String origen, int cantidadTotal, double ganancias) {
        this.id = id;
        this.destino = destino;
        this.origen = origen;
        this.cantidadTotal = cantidadTotal;
        this.asientosClaseEconomica = cantidadTotal;
        this.asientosClasePremium = 0;
        this.ganancias = ganancias;
    }

    public Viajes(int id, String destino, String origen, int cantidadTotal, int asientosClaseEconomica, int asientosClasePremium, double precioEconomica, double precioPremium, double ganancias) {
        this.id = id;
        this.destino = destino;
        this.origen = origen;
        this.cantidadTotal = cantidadTotal;
        this.asientosClaseEconomica = asientosClaseEconomica;
        this.asientosClasePremium = asientosClasePremium;
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
            return asientosClaseEconomica + asientosClasePremium;
        }
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public int getAsientosClaseEconomica() {
        return asientosClaseEconomica;
    }

    public void setAsientosClaseEconomica(int asientosClaseEconomica) {
        this.asientosClaseEconomica = asientosClaseEconomica;
        this.cantidadTotal = this.asientosClaseEconomica + this.asientosClasePremium;
    }

    public int getAsientosClasePremium() {
        return asientosClasePremium;
    }

    public void setAsientosClasePremium(int asientosClasePremium) {
        this.asientosClasePremium = asientosClasePremium;
        this.cantidadTotal = this.asientosClaseEconomica + this.asientosClasePremium;
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
                ", cantidad(total)=" + (asientosClaseEconomica + asientosClasePremium) +
                ", asientosClasePremium=" + asientosClasePremium +
                ", asientosClaseEconomica=" + asientosClaseEconomica +
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
                ", cantidad(total)=" + (asientosClaseEconomica + asientosClasePremium) +
                ", asientosClasePremium=" + asientosClasePremium +
                ", asientosClaseEconomica=" + asientosClaseEconomica +
                ", precioEconomica=" + precioEconomica +
                ", precioPremium=" + precioPremium +
                '}'+"\n";
    }

}
