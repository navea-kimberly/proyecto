package Entidades;

public class Usuario {
    private int id_tipo;
    private String cod_usuario, nombre, clave, tipo;

    public Usuario() {
    }
    
    public Usuario (String cod_usuario, String nombre, String clave, int id_tipo) {
        this.cod_usuario = cod_usuario;
        this.nombre = nombre;
        this.clave = clave;
        this.id_tipo = id_tipo;
    }
    
    public Usuario (String cod_usuario, String nombre, String clave, String tipo) {
        this.cod_usuario = cod_usuario;
        this.nombre = nombre;
        this.clave = clave;
        this.tipo = tipo;
    }
    
    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getClave() {
        return clave;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }
    
    public int getId_tipo() {
        return id_tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getTipo() {
        return tipo;
    }
}
