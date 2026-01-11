package clases;

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

    public Cliente() {
        this.precio = 0.0;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAsientosComprados() {
        return asientosComprados;
    }

    public void setAsientosComprados(int asientosComprados) {
        if (asientosComprados < 0) {
            System.out.println("Los asientos comprados no pueden ser negativos");
        }
        this.asientosComprados = asientosComprados;
    }

    public int getAsientosClaseEconomica() {
        return asientosClaseEconomica;
    }

    public void setAsientosClaseEconomica(int asientosClaseEconomica) {
        if (asientosClaseEconomica < 0) return;
        this.asientosClaseEconomica = asientosClaseEconomica;
        this.asientosComprados = this.asientosClaseEconomica + this.asientosClasePremium;
    }

    public int getAsientosClasePremium() {
        return asientosClasePremium;
    }

    public void setAsientosClasePremium(int asientosClasePremium) {
        if (asientosClasePremium < 0) return;
        this.asientosClasePremium = asientosClasePremium;
        this.asientosComprados = this.asientosClaseEconomica + this.asientosClasePremium;
    }

    /**
     * Precio asociado al cliente (agregado).
     */
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }



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
