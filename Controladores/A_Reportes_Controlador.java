/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ConexionMySQL.Conexion;
import Otros.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Javier
 */
public class A_Reportes_Controlador implements Initializable {

    @FXML
    private JFXButton btn_puntualidad;
    @FXML
    private JFXButton btn_cobro;
    @FXML
    private JFXButton btn_pago;
    @FXML
    private JFXDatePicker Date_Picker_Inicio;
    @FXML
    private JFXDatePicker Date_Picker_Final;
    @FXML
    private JFXButton btn_bitacora;

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    Eventos();
    Parametros();
    }    
    
    void Parametros(){
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
               @Override
               public void updateItem(LocalDate item, boolean empty)
               {
                   super.updateItem(item, empty);
                   try{
                   if(item.isBefore(Date_Picker_Inicio.getValue()) || item.isAfter(LocalDate.now().plusYears(1)))
                   {
                       setStyle("-fx-background-color: #ffc0cb;");
                       Platform.runLater(() -> setDisable(true));
                    }
                   }catch(Exception e){    
                   }
               }
        };
        Date_Picker_Final.setDayCellFactory(dayCellFactory);
    }
    
    void Eventos(){

        btn_puntualidad.setOnAction((e) -> {
            try {
                if(Validar()){
                }else{
                    reporte_puntulidad();}
            } catch (SQLException ex) {
                Logger.getLogger(A_Reportes_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btn_cobro.setOnAction((e) -> {
            try {
                if(Validar()){
                }else{
                    reporte_cobro();}
            } catch (SQLException ex) {
                Logger.getLogger(A_Reportes_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btn_pago.setOnAction((e) -> {
            try {
                if(Validar()){
                }else{
                    reporte_pago();}
            } catch (SQLException ex) {
                Logger.getLogger(A_Reportes_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btn_bitacora.setOnAction((e) -> {
            try {
                if(Validar()){
                }else{
                    reporte_bitacora();}
            } catch (SQLException ex) {
                Logger.getLogger(A_Reportes_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
    }

        void reporte_puntulidad()throws SQLException{
           try{
                try {
                    
                    Map parametro = new HashMap();
                    
                    LocalDate Fecha_Inicio   = Date_Picker_Inicio.getValue();    
                    LocalDate Fecha_Fin   = Date_Picker_Final.getValue();  
                 ///   System.out.println(Fecha_Fin);
                     //Fecha_Inicio.format(DateTimeFormatter.ISO_LOCAL_DATE);
                   parametro.put("fecha_inicio",Fecha_Inicio);
                   parametro.put("fecha_final",Fecha_Fin);
                    Conexion conexion = new Conexion();
                    Connection canal = conexion.Conectar();

                    String path= "src\\Reportes\\Reporte_Puntualidad.jasper";
                     JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
                    JasperPrint jprint = JasperFillManager.fillReport(reporte,parametro,canal);
                    JasperViewer view = new JasperViewer(jprint,false);
                    view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    view.setVisible(true);
           
                } catch (JRException ex) {
                    System.out.println("ERROR" + ex);
                    Logger.getLogger(A_BitacoraControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }catch(Exception ex){
                System.out.println("sadass"+ ex);
            }
       }
        
        void reporte_bitacora()throws SQLException{
           try{
                try {
                    
                    Map parametro = new HashMap();
                    
                    LocalDate Fecha_Inicio   = Date_Picker_Inicio.getValue();    
                    LocalDate Fecha_Fin   = Date_Picker_Final.getValue();  
                 ///   System.out.println(Fecha_Fin);
                     //Fecha_Inicio.format(DateTimeFormatter.ISO_LOCAL_DATE);
                   parametro.put("fecha_inicio",Fecha_Inicio);
                   parametro.put("fecha_final",Fecha_Fin);
                    Conexion conexion = new Conexion();
                    Connection canal = conexion.Conectar();

                    String path= "src\\Reportes\\Reporte_Bitacora.jasper";
                     JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
                    JasperPrint jprint = JasperFillManager.fillReport(reporte,parametro,canal);
                    JasperViewer view = new JasperViewer(jprint,false);
                    view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    view.setVisible(true);
           
                } catch (JRException ex) {
                    System.out.println("ERROR" + ex);
                    Logger.getLogger(A_BitacoraControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }catch(Exception ex){
                System.out.println("sadass"+ ex);
            }
       }
        void reporte_pago()throws SQLException{
           try{
                try {
                    
                    Map parametro = new HashMap();
                    
                    LocalDate Fecha_Inicio   = Date_Picker_Inicio.getValue();    
                    LocalDate Fecha_Fin   = Date_Picker_Final.getValue();  
                    System.out.println(Fecha_Fin);
                    Fecha_Inicio.format(DateTimeFormatter.ISO_LOCAL_DATE);
                   parametro.put("fecha_inicio",Fecha_Inicio);
                   parametro.put("fecha_final",Fecha_Fin);
                    Conexion conexion = new Conexion();
                    Connection canal = conexion.Conectar();

                    String path= "src\\Reportes\\Reporte_pagos.jasper";
                     JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
                    JasperPrint jprint = JasperFillManager.fillReport(reporte,parametro,canal);
                    JasperViewer view = new JasperViewer(jprint,false);
                    view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    view.setVisible(true);
                    
                } catch (JRException ex) {
                    System.out.println("ERROR" + ex);
                    Logger.getLogger(A_BitacoraControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }catch(Exception ex){
                System.out.println("sadass"+ ex);
            }
       }
        void reporte_cobro()throws SQLException{
           try{
                try {
                    
                    Map parametro = new HashMap();
                    
                    LocalDate Fecha_Inicio   = Date_Picker_Inicio.getValue();    
                    LocalDate Fecha_Fin   = Date_Picker_Final.getValue();  
                 ///   System.out.println(Fecha_Fin);
                     //Fecha_Inicio.format(DateTimeFormatter.ISO_LOCAL_DATE);
                   parametro.put("fecha_inicio",Fecha_Inicio);
                   parametro.put("fecha_final",Fecha_Fin);
                    Conexion conexion = new Conexion();
                    Connection canal = conexion.Conectar();

                    String path= "src\\Reportes\\Reporte_cobro.jasper";
                     JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
                    JasperPrint jprint = JasperFillManager.fillReport(reporte,parametro,canal);
                    JasperViewer view = new JasperViewer(jprint,false);
                    view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    view.setVisible(true);
                } catch (JRException ex) {
                    System.out.println("ERROR" + ex);
                    Logger.getLogger(A_BitacoraControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }catch(Exception ex){
                System.out.println("sadass"+ ex);
            }
       }
    
     private boolean Validar(){
        boolean Respuesta = false;
        String Mensaje = "Error en los siguientes campos: ";
       
            if(Date_Picker_Inicio.getValue()==null){ Mensaje += "\nFecha de inicio no seleccionada"; Respuesta = true;}
            if(Date_Picker_Final.getValue()==null){ Mensaje += "\nFecha de final no seleccionada";  Respuesta = true;}
            
            if(Respuesta==true){Mensajes.Mensaje(Mensaje);}
        return Respuesta;
     }
     
}
