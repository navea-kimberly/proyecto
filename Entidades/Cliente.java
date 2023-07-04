package Entidades;

public class Cliente {
    
    private String dni, tipoCliente, nombre, direccion, n_contacto, correo;
    private int idTipoCliente;

    public Cliente() {
    }
    
    public Cliente(String dni, int idTipoCliente, String nombre, String direccion, String n_contacto, String correo) {
        this.dni = dni;
        this.idTipoCliente = idTipoCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.n_contacto = n_contacto;
        this.correo = correo;
    }
    
    public Cliente(String dni, String tipoCliente, String nombre, String direccion, String n_contacto, String correo) {
        this.dni = dni;
        this.tipoCliente = tipoCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.n_contacto = n_contacto;
        this.correo = correo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getN_contacto() {
        return n_contacto;
    }

    public void setN_contacto(String n_contacto) {
        this.n_contacto = n_contacto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getIdTipoCliente() {
        return idTipoCliente;
    }

    public void setIdTipoCliente(int idTipoCliente) {
        this.idTipoCliente = idTipoCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
}
