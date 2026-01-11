package clases;

/**
 * Representa un cliente del sistema.
 *
 * Explicación simple:
 * - Guarda datos personales (nombre, apellido, identificación, email).
 * - Mantiene información de asientos comprados por el cliente (económica y premium) y el precio total asociado.
 * - Contiene getters y setters para manipular los campos.
 */
public class Cliente {
    private String nombre;
    private String apellido;
    private String identificacion;
    private String email;
    private int id;
    private int asientosComprados;
    private int asientosClaseEconomica;
    private int asientosClasePremium;
    private double precio; // agregado


    /**
     * Constructor completo para crear un cliente con id y datos personales.
     */
    public Cliente(int id, String nombre, String apellido, String email, String identificacion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.identificacion = identificacion;
        this.asientosComprados = 0;
        this.asientosClaseEconomica = 0;
        this.asientosClasePremium = 0;
        this.precio = 0.0;
    }

    /**
     * Constructor vacío. Inicializa precio en 0.0.
     */
    public Cliente() {
        this.precio = 0.0;
    }

    // Getters y setters
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
     * Obtiene el total de asientos comprados por el cliente (eco + premium).
     */
    public int getAsientosComprados() {
        return asientosComprados;
    }

    /**
     * Establece el total de asientos comprados.
     * Si se pasa un valor negativo se muestra un mensaje y se ignora la validación mínima.
     */
    public void setAsientosComprados(int asientosComprados) {
        if (asientosComprados < 0) {
            System.out.println("Los asientos comprados no pueden ser negativos");
        }
        this.asientosComprados = asientosComprados;
    }

    /**
     * Obtiene la cantidad de asientos en clase económica.
     */
    public int getAsientosClaseEconomica() {
        return asientosClaseEconomica;
    }

    /**
     * Establece la cantidad de asientos en clase económica y recalcula el total.
     */
    public void setAsientosClaseEconomica(int asientosClaseEconomica) {
        if (asientosClaseEconomica < 0) return;
        this.asientosClaseEconomica = asientosClaseEconomica;
        this.asientosComprados = this.asientosClaseEconomica + this.asientosClasePremium;
    }

    /**
     * Obtiene la cantidad de asientos en clase premium.
     */
    public int getAsientosClasePremium() {
        return asientosClasePremium;
    }

    /**
     * Establece la cantidad de asientos en clase premium y recalcula el total.
     */
    public void setAsientosClasePremium(int asientosClasePremium) {
        if (asientosClasePremium < 0) return;
        this.asientosClasePremium = asientosClasePremium;
        this.asientosComprados = this.asientosClaseEconomica + this.asientosClasePremium;
    }

    /**
     * Precio total asociado al cliente (por los asientos comprados).
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio total asociado al cliente.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }



    /**
     * Representación en texto del cliente. Útil para depuración y lista de clientes.
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
