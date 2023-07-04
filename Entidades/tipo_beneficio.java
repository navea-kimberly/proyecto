package Entidades;

public class tipo_beneficio {
    int idTipo_beneficio;
    String Nombre_beneficio;
    Double Costo_beneficio;

    public tipo_beneficio(int idTipo_beneficio, String Nombre_beneficio, Double Costo_beneficio) {
        this.idTipo_beneficio = idTipo_beneficio;
        this.Nombre_beneficio = Nombre_beneficio;
        this.Costo_beneficio = Costo_beneficio;
    }

    public tipo_beneficio() {
      
    }

    public int getIdTipo_beneficio() {
        return idTipo_beneficio;
    }

    public void setIdTipo_beneficio(int idTipo_beneficio) {
        this.idTipo_beneficio = idTipo_beneficio;
    }

    public String getNombre_beneficio() {
        return Nombre_beneficio;
    }

    public void setNombre_beneficio(String Nombre_beneficio) {
        this.Nombre_beneficio = Nombre_beneficio;
    }

    public Double getCosto_beneficio() {
        return Costo_beneficio;
    }

    public void setCosto_beneficio(Double Costo_beneficio) {
        this.Costo_beneficio = Costo_beneficio;
    }
    
    
}
