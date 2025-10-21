package modelo;

import java.time.LocalDateTime;

public class SolicitudProceso {
    private Integer id;
    private Integer usuarioId;
    private Integer procesoId;
    private LocalDateTime fechaSolicitud;
    private String estado; // pendiente, completado
    private String parametros; // JSON con parámetros del proceso

    public SolicitudProceso() {}

    public SolicitudProceso(Integer usuarioId, Integer procesoId, String parametros) {
        this.usuarioId = usuarioId;
        this.procesoId = procesoId;
        this.parametros = parametros;
        this.estado = "pendiente";
        this.fechaSolicitud = LocalDateTime.now();
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public Integer getProcesoId() { return procesoId; }
    public void setProcesoId(Integer procesoId) { this.procesoId = procesoId; }

    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getParametros() { return parametros; }
    public void setParametros(String parametros) { this.parametros = parametros; }
}
