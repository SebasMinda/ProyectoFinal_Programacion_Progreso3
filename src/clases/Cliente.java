package clases;

/**
 * Representa un cliente del sistema.
 *
 * Explicación simple (pero completa):
 * - Guarda datos personales: nombre, apellido, identificación y correo.
 * - Guarda un id interno (normalmente viene de la base de datos).
 * - Guarda información de compra: asientos en Económica, asientos en Premium, total de asientos y precio total.
 * - Los setters de asientos recalculan el total para mantener consistencia.
 */
public class Cliente {

    // Datos personales del cliente
    private String nombre;
    private String apellido;
    private String identificacion;
    private String email;

    // ID interno del cliente (por ejemplo, idcliente en la BD)
    private int id;

    // Total de asientos comprados (eco + premium)
    private int asientosComprados;

    // Cantidad de asientos comprados en clase Económica
    private int asientosClaseEconomica;

    // Cantidad de asientos comprados en clase Premium
    private int asientosClasePremium;

    // Precio total pagado por los asientos comprados (suma de ambas clases)
    private double precio;

    /**
     * Constructor completo para crear un cliente con id y datos personales.
     *
     * Detalle:
     * - Inicializa la parte de compra en 0 (sin asientos, sin precio).
     */
    public Cliente(int id, String nombre, String apellido, String email, String identificacion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.identificacion = identificacion;

        // Inicializamos compra en 0 (cliente recién creado)
        this.asientosComprados = 0;
        this.asientosClaseEconomica = 0;
        this.asientosClasePremium = 0;
        this.precio = 0.0;
    }

    /**
     * Constructor vacío.
     *
     * Detalle:
     * - Útil cuando se crea el objeto y luego se llenan los campos con setters.
     * - Inicializa precio en 0.0 por defecto.
     */
    public Cliente() {
        this.precio = 0.0;
    }

    // =========================
    //      GETTERS / SETTERS
    // =========================

    /**
     * Obtiene el nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del cliente.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido del cliente.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene la identificación del cliente.
     * - Puede ser cédula, pasaporte o algún identificador usado en el sistema.
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * Establece la identificación del cliente.
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del cliente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el id interno del cliente.
     * - Normalmente se asigna cuando se inserta el cliente en la base de datos.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el id interno del cliente.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el total de asientos comprados.
     * - Este valor debería coincidir con (económica + premium).
     */
    public int getAsientosComprados() {
        return asientosComprados;
    }

    /**
     * Establece el total de asientos comprados.
     *
     * Nota:
     * - Si se envía un valor negativo, se avisa por consola, pero igual se asigna (como está tu lógica actual).
     * - Se mantiene así para no cambiar el comportamiento de tu programa.
     */
    public void setAsientosComprados(int asientosComprados) {
        if (asientosComprados < 0) {
            // Mensaje simple de validación (no detiene el programa)
            System.out.println("Los asientos comprados no pueden ser negativos");
        }
        this.asientosComprados = asientosComprados;
    }

    /**
     * Obtiene la cantidad de asientos en clase Económica.
     */
    public int getAsientosClaseEconomica() {
        return asientosClaseEconomica;
    }

    /**
     * Establece la cantidad de asientos en clase Económica.
     *
     * Detalles:
     * - Si el valor es negativo, se ignora (return) para evitar datos incorrectos.
     * - Recalcula el total de asientos comprados para que siempre sea eco + premium.
     */
    public void setAsientosClaseEconomica(int asientosClaseEconomica) {
        if (asientosClaseEconomica < 0) return;

        this.asientosClaseEconomica = asientosClaseEconomica;

        // Mantener consistencia: total = eco + premium
        this.asientosComprados = this.asientosClaseEconomica + this.asientosClasePremium;
    }

    /**
     * Obtiene la cantidad de asientos en clase Premium.
     */
    public int getAsientosClasePremium() {
        return asientosClasePremium;
    }

    /**
     * Establece la cantidad de asientos en clase Premium.
     *
     * Detalles:
     * - Si el valor es negativo, se ignora para evitar datos inválidos.
     * - Recalcula el total de asientos comprados para mantener consistencia.
     */
    public void setAsientosClasePremium(int asientosClasePremium) {
        if (asientosClasePremium < 0) return;

        this.asientosClasePremium = asientosClasePremium;

        // Mantener consistencia: total = eco + premium
        this.asientosComprados = this.asientosClaseEconomica + this.asientosClasePremium;
    }

    /**
     * Obtiene el precio total asociado al cliente por su compra.
     * - Normalmente es la suma (precioEco * cantEco) + (precioPrem * cantPrem).
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio total asociado al cliente.
     * - Se usa después de calcular el total a pagar en el flujo de reserva.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // =========================
    //           EXTRA
    // =========================

    /**
     * Representación en texto del cliente.
     * - Útil para depuración y para mostrar la lista de clientes en consola.
     */
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", email='" + email + '\'' +
                ", asientosComprados=" + asientosComprados +
                ", asientosClaseEconomica=" + asientosClaseEconomica +
                ", asientosClasePremium=" + asientosClasePremium +
                ", precio=" + precio +
                '}';
    }
}
