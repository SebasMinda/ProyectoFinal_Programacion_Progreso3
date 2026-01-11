package clases;

/**
 * Representa un viaje (vuelo) con sus asientos y precios.
 *
 * Explicación simple:
 * - Guarda origen, destino y cantidades de asientos por clase.
 * - Guarda precios por clase y las ganancias acumuladas del viaje.
 * - Tiene toString simplificado para mostrar información al usuario (admin/cliente).
 */
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
    /**
     * Obtiene el id del viaje.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el id del viaje.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el destino del viaje.
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Establece el destino del viaje.
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * Obtiene el origen del viaje.
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * Establece el origen del viaje.
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * Obtiene la cantidad total de asientos (eco + premium).
     * Si cantidadTotal es 0, se calcula como la suma de las dos categorías para conservar compatibilidad.
     */
    public int getCantidadTotal() {
        // Mantener compatibilidad: cantidad es la suma de categorías
        if (cantidadTotal == 0) {
            return asientosClaseEconomica + asientosClasePremium;
        }
        return cantidadTotal;
    }

    /**
     * Establece la cantidad total (no recomendado: usar setters de cada clase para mantener consistencia).
     */
    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    /**
     * Obtiene los asientos en clase economica.
     */
    public int getAsientosClaseEconomica() {
        return asientosClaseEconomica;
    }

    /**
     * Establece los asientos en clase economica y recalcula cantidad total.
     */
    public void setAsientosClaseEconomica(int asientosClaseEconomica) {
        this.asientosClaseEconomica = asientosClaseEconomica;
        this.cantidadTotal = this.asientosClaseEconomica + this.asientosClasePremium;
    }

    /**
     * Obtiene los asientos en clase premium.
     */
    public int getAsientosClasePremium() {
        return asientosClasePremium;
    }

    /**
     * Establece los asientos en clase premium y recalcula cantidad total.
     */
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
