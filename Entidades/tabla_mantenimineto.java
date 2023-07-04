/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author yoanny
 */
public class tabla_mantenimineto {
    
    private String Nombre;
    private String Peso;
    
    public tabla_mantenimineto(){};

    public tabla_mantenimineto(String nombre, String peso) {
        Nombre = nombre;
        Peso = peso;
        
                }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setPeso(String Peso) {
        this.Peso = Peso;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getPeso() {
        return Peso;
    }
    
    }
