package modelo;

public class Proceso {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String tipo; // PROMEDIO_MENSUAL, MAX_MIN, ALERTAS, etc.
    private Double costo;

    public Proceso() {}

    public Proceso(String nombre, String descripcion, String tipo, Double costo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.costo = costo;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Double getCosto() { return costo; }
    public void setCosto(Double costo) { this.costo = costo; }
}
