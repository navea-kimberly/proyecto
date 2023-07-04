package Entidades;

import java.time.LocalDate;

public class Vigilante {
    private String dni, nombres, apellidos, direccion, telefono, correo;
    private boolean jefe_grupo, disponible;
    private LocalDate fecha_nacimiento, fecha_ingreso;

    private String nombre_completo;
    private char jefe_v, disponible_v;
    
    public Vigilante() {
    }

    public Vigilante(String dni, String nombres, String apellidos, LocalDate fecha_nacimiento, String direccion, String telefono, LocalDate fecha_ingreso, boolean jefe_grupo, boolean disponible) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fecha_nacimiento = fecha_nacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fecha_ingreso = fecha_ingreso;
        this.jefe_grupo = jefe_grupo;
        this.disponible = disponible;
    }

    public Vigilante(String dni, String nombre_completo, String direccion, String telefono, String correo, boolean jefe_grupo,
            boolean disponible, LocalDate fecha_nacimiento, LocalDate fecha_ingreso) {
        
        this.dni = dni;
        this.nombre_completo = nombre_completo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.jefe_grupo = jefe_grupo;
        this.disponible = disponible;
        this.fecha_ingreso = fecha_ingreso;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDni() {
        return dni;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getNombres() {
        return nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getApellidos() {
        return apellidos;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
    
    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setFecha_ingreso(LocalDate fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public LocalDate getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setJefe_grupo(boolean jefe_grupo) {
        this.jefe_grupo = jefe_grupo;
    }
    
    public boolean isJefe_grupo() {
        return jefe_grupo;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    public boolean isDisponible() {
        return disponible;
    }
    
    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setJefe_v(char jefe_v) {
        this.jefe_v = jefe_v;
    }

    public char getJefe_v() {
        return jefe_v;
    }

    public void setDisponible_v(char disponible_v) {
        this.disponible_v = disponible_v;
    }
    
    public char getDisponible_v() {
        return disponible_v;
    }
}
    