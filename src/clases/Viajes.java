package clases;

/**
 * Representa un viaje (vuelo) dentro del sistema.
 *
 * Explicación simple (pero detallada):
 * - Guarda la información principal del vuelo: origen, destino e idViaje.
 * - Guarda la disponibilidad de asientos por clase: Económica y Premium.
 * - Guarda los precios unitarios por clase para calcular el total de una compra.
 * - Guarda las ganancias acumuladas del vuelo (lo que se ha vendido hasta el momento).
 * - Incluye dos métodos de impresión:
 *   - toString(): pensado para admin (incluye ganancias).
 *   - toString2(): pensado para cliente (no muestra ganancias).
 */
public class Viajes {

    // Identificador único del viaje en la base de datos
    private int idViaje;

    // Destino del viaje (a dónde llega el vuelo)
    private String destino;

    // Origen del viaje (de dónde sale el vuelo)
    private String origen;

    // Total de asientos del viaje (se mantiene consistente con eco + premium)
    private int cantidadTotal;

    // Asientos disponibles en clase Económica
    private int asientosClaseEconomica;

    // Asientos disponibles en clase Premium
    private int asientosClasePremium;

    // Precio unitario por asiento en clase Económica
    private double precioEconomica;

    // Precio unitario por asiento en clase Premium
    private double precioPremium;

    private int asientosVendidos;

    // Ganancias acumuladas del viaje (suma de ventas realizadas para este vuelo)
    private double ganancias;

    /**
     * Constructor para crear un viaje SIN ganancias (útil cuando no se necesita mostrar ese dato).
     * - Normalmente se usa para la vista de clientes (lista de vuelos sin ganancias).
     */
    public Viajes(int idViaje, String destino, String origen, int cantidadTotal,
                  int asientosClaseEconomica, int asientosClasePremium,
                  double precioEconomica, double precioPremium) {

        this.idViaje = idViaje;
        this.destino = destino;
        this.origen = origen;
        this.cantidadTotal = cantidadTotal;
        this.asientosClaseEconomica = asientosClaseEconomica;
        this.asientosClasePremium = asientosClasePremium;
        this.precioEconomica = precioEconomica;
        this.precioPremium = precioPremium;
    }

    /**
     * Constructor vacío.
     * - Se usa cuando primero se crea el objeto y luego se llenan los datos con setters.
     */
    public Viajes() {
    }

    /**
     * Constructor para crear un viaje CON ganancias (pensado para la vista de administrador).
     */
    public Viajes(int idViaje, String destino, String origen, int cantidadTotal,
                  int asientosClaseEconomica, int asientosClasePremium,
                  double precioEconomica, double precioPremium, int asientosVendidos,
                  double ganancias) {

        this.idViaje = idViaje;
        this.destino = destino;
        this.origen = origen;
        this.cantidadTotal = cantidadTotal;
        this.asientosClaseEconomica = asientosClaseEconomica;
        this.asientosClasePremium = asientosClasePremium;
        this.precioEconomica = precioEconomica;
        this.precioPremium = precioPremium;
        this.asientosVendidos = asientosVendidos;
        this.ganancias = ganancias;
    }

    // =========================
    //      GETTERS / SETTERS
    // =========================

    /**
     * Obtiene el idViaje del viaje.
     * - Este idViaje generalmente viene de la base de datos.
     */
    public int getIdViaje() {
        return idViaje;
    }

    /**
     * Establece el idViaje del viaje.
     * - Útil cuando se lee el viaje desde la BD y se asigna al objeto.
     */
    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
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
     * Obtiene la cantidad total de asientos del viaje.
     *
     * Detalle:
     * - Para evitar inconsistencias, el total se considera como eco + premium.
     * - Si cantidadTotal se quedó en 0 (por compatibilidad con datos viejos),
     *   se calcula usando la suma actual de ambas clases.
     */
    public int getCantidadTotal() {
        // Mantener compatibilidad: si no viene el total guardado, lo calculamos.
        if (cantidadTotal == 0) {
            return asientosClaseEconomica + asientosClasePremium;
        }
        return cantidadTotal;
    }

    /**
     * Establece la cantidad total de asientos.
     *
     * Nota simple:
     * - No es lo más recomendado, porque el total debería ser eco + premium.
     * - Se mantiene por si en algún punto se necesita setear directamente desde BD.
     */
    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    /**
     * Obtiene los asientos disponibles en clase Económica.
     */
    public int getAsientosClaseEconomica() {
        return asientosClaseEconomica;
    }

    /**
     * Establece los asientos en clase Económica.
     *
     * Detalle:
     * - Luego recalcula cantidadTotal para que siempre quede consistente.
     */
    public void setAsientosClaseEconomica(int asientosClaseEconomica) {
        this.asientosClaseEconomica = asientosClaseEconomica;

        // Mantener coherencia del total: total = eco + premium
        this.cantidadTotal = this.asientosClaseEconomica + this.asientosClasePremium;
    }

    /**
     * Obtiene los asientos disponibles en clase Premium.
     */
    public int getAsientosClasePremium() {
        return asientosClasePremium;
    }

    /**
     * Establece los asientos en clase Premium.
     *
     * Detalle:
     * - Luego recalcula cantidadTotal para que siempre quede consistente.
     */
    public void setAsientosClasePremium(int asientosClasePremium) {
        this.asientosClasePremium = asientosClasePremium;

        // Mantener coherencia del total: total = eco + premium
        this.cantidadTotal = this.asientosClaseEconomica + this.asientosClasePremium;
    }

    /**
     * Obtiene el precio unitario de la clase Económica.
     */
    public double getPrecioEconomica() {
        return precioEconomica;
    }

    /**
     * Establece el precio unitario de la clase Económica.
     */
    public void setPrecioEconomica(double precioEconomica) {
        this.precioEconomica = precioEconomica;
    }

    /**
     * Obtiene el precio unitario de la clase Premium.
     */
    public double getPrecioPremium() {
        return precioPremium;
    }

    /**
     * Establece el precio unitario de la clase Premium.
     */
    public void setPrecioPremium(double precioPremium) {
        this.precioPremium = precioPremium;
    }

    /**
     * Obtiene las ganancias acumuladas del viaje.
     * - Este valor normalmente se actualiza cuando se registran ventas.
     */
    public double getGanancias() {
        return ganancias;
    }

    // =========================
    //      MÉTODOS DE IMPRESIÓN
    // =========================

    /**
     * Representación completa del viaje (ideal para admin).
     * - Incluye: asientos, precios y ganancias.
     */
    public String toString() {
        return "Viaje{" +
                "idViaje=" + idViaje +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", cantidad(total)=" + (asientosClaseEconomica + asientosClasePremium) +
                ", asientosClasePremium=" + asientosClasePremium +
                ", asientosClaseEconomica=" + asientosClaseEconomica +
                ", precioEconomica=" + precioEconomica +
                ", precioPremium=" + precioPremium +
                ", ganancias=" + ganancias +
                '}' + "\n";
    }

    /**
     * Representación simplificada del viaje (ideal para clientes).
     * - Igual que toString(), pero sin mostrar ganancias.
     */
    public String toString2() {
        return "Viaje{" +
                "idViaje=" + idViaje +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", cantidad(total)=" + (asientosClaseEconomica + asientosClasePremium) +
                ", asientosClasePremium=" + asientosClasePremium +
                ", asientosClaseEconomica=" + asientosClaseEconomica +
                ", precioEconomica=" + precioEconomica +
                ", precioPremium=" + precioPremium +
                '}' + "\n";
    }

}
