package clases;

public class Cliente {
    private String nombre;
    private String apellido;
    private String identificacion;
    private String email;
    private int id;
    private int asientosComprados; // renombrado a camelCase y ahora usado

    public Cliente(String nombre, String apellido, String email, String identificacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.identificacion = identificacion;
        this.asientosComprados = 0; // por defecto 0
    }

    public Cliente(int id, String nombre, String apellido, String email, String identificacion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.identificacion = identificacion;
        this.asientosComprados = 0; // por defecto 0
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

    /**
     * Incrementa la cantidad de asientos comprados por este cliente.
     */
    public void comprarAsientos(int cantidad) {
        if (cantidad <= 0) return;
        this.asientosComprados += cantidad;
    }

    /**
     * Resta asientos comprados (por ejemplo, anulación). No permite negativos.
     */
    public void anularAsientos(int cantidad) {
        if (cantidad <= 0) return;
        this.asientosComprados = Math.max(0, this.asientosComprados - cantidad);
    }

        public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", email='" + email + '\'' +
                ", asientosComprados=" + asientosComprados +
                '}';
    }
}
