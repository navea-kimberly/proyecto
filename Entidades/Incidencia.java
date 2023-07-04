package Entidades;

public class Incidencia {
    private int idRdg, idTipo_incidencia;
    private String descripcion, tipoIncidencia;

    public Incidencia() {
    }
    
    public Incidencia(int idRdg, int idTipo_incidencia, String descripcion) {
        this.idRdg = idRdg;
        this.idTipo_incidencia = idTipo_incidencia;
        this.descripcion = descripcion;
    }
    
    public Incidencia(int idRdg, int idTipo_incidencia, String tipoIncidencia, String descripcion) {
        this.idRdg = idRdg;
        this.idTipo_incidencia = idTipo_incidencia;
        this.tipoIncidencia = tipoIncidencia;
        this.descripcion = descripcion;
    }
    
    public void setIdRdg(int idRdg) {
        this.idRdg = idRdg;
    }
    
    public int getIdRdg() {
        return idRdg;
    }

    public void setIdTipo_incidencia(int idTipo_incidencia) {
        this.idTipo_incidencia = idTipo_incidencia;
    }

    public int getIdTipo_incidencia() {
        return idTipo_incidencia;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setTipoIncidencia(String tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }
    
    public String getTipoIncidencia() {
        return tipoIncidencia;
    }

    

}
