/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import Otros.Mensajes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.ObservableList;

public class DatosBitacora {

    private ObservableList<Integer> Tipos_de_seleccion;
    private String Usuario;
    private String Actividad;
    private String Descripcion;
    private String Fecha_del_registro;
    private ArrayList<String> acciones_bitacora_Array;

    public DatosBitacora(){}
    
    public DatosBitacora(String Usuario, String Actividad,String Descripcion,String Fecha_del_registro){
        
        this.Usuario = Usuario;
        this.Actividad = Actividad;
        this.Descripcion = Descripcion;
        this.Fecha_del_registro = Fecha_del_registro;
        
    }

    public DatosBitacora(ArrayList<String> acciones_bitacora_Array) {
        this.acciones_bitacora_Array = acciones_bitacora_Array;
    }

    public ArrayList<DatosBitacora> Consultar_Funcion(String consulta, Connection conexion_base_datos){
        
 String Consulta_sql= consulta;
        ArrayList<DatosBitacora> datosbitacora = new   ArrayList<DatosBitacora > ();

        Statement statement;
        try {
            statement =  conexion_base_datos.createStatement();  //Contiene nuestro conector
            ResultSet resultado =  statement.executeQuery(Consulta_sql);    //Contiene nuestra variable statement y ejecuta el select from desde la base de datos
            
      
            while(resultado.next()){
                try{
                Usuario = resultado.getString(1);
                Actividad = resultado.getString(2);
                Descripcion = resultado.getString(3);
                Fecha_del_registro = resultado.getString(4);
                
                DatosBitacora datos = new DatosBitacora(Usuario,Actividad,Descripcion,Fecha_del_registro);
                
                datosbitacora.add(datos);
            }catch(SQLException e){
                Mensajes.Mensaje("Error de consulta 1 ");
                }
               
                }
            } catch (SQLException ex) {
           Mensajes.Mensaje("Error de consulta 2 ");
        }

       // datosbitacora.get(0).get
    return datosbitacora;
    }

    public ArrayList<String> getAcciones_bitacora_Array() {
        return acciones_bitacora_Array;
    }

    public void setAcciones_bitacora_Array(ArrayList<String> acciones_bitacora_Array) {
        this.acciones_bitacora_Array = acciones_bitacora_Array;
    }
    
    public void setTipos_de_seleccion(ObservableList<Integer> Tipos_de_seleccion) {
        this.Tipos_de_seleccion = Tipos_de_seleccion;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public void setActividad(String Actividad) {
        this.Actividad = Actividad;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public void setFecha_del_registro(String Fecha_del_registro) {
        this.Fecha_del_registro = Fecha_del_registro;
    }

    public String getFecha_del_registro() {
        return Fecha_del_registro;
    }
    

    public ObservableList<Integer> getTipos_de_seleccion() {
        return Tipos_de_seleccion;
    }

    public String getUsuario() {
        return Usuario;
    }

    public String getActividad() {
        return Actividad;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
