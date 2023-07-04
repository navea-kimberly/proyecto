/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import Entidades.DatosBitacora;
import Entidades.tabla_mantenimineto;
import Otros.Mensajes;

/**
 * FXML Controller class
 *
 * @author Jesus Bata
 */
public class A_MantenimientoControlador implements Initializable {

    @FXML
    private TableView<tabla_mantenimineto> TableView_DatoBD;
    @FXML
    private TableColumn<tabla_mantenimineto, String> TablaColumn_Nombre;
    @FXML
    private TableColumn<tabla_mantenimineto, String> TableColumn_Peso;
    @FXML
    private JFXTextField Text_Field_URL_Exportar;
    @FXML
    private JFXTextField Text_Field_URL_Importar;
    @FXML
    private JFXButton Button_Seleccionar_Exportar;
    @FXML
    private JFXButton Button_Exportar;
    @FXML
    private JFXButton Button_Importar;
    @FXML
    private JFXButton Button_Seleccionar_Importar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     Eventos();
    }  
             
    private void Cargar_tabla(String Nombre,String Peso){
        try{
        tabla_mantenimineto Tabla = new tabla_mantenimineto(Nombre,Peso);
        ObservableList<tabla_mantenimineto> Datos_consultados = FXCollections.observableArrayList();
        Datos_consultados.add(Tabla);
        TableView_DatoBD.getItems().clear();
        TablaColumn_Nombre.setCellValueFactory(new PropertyValueFactory <>("nombre"));
        TableColumn_Peso.setCellValueFactory(new PropertyValueFactory <>("peso"));
        TableView_DatoBD.setItems(Datos_consultados);
        
        if(Tabla.getNombre() == null ){
             Mensajes.Mensaje("Problema al leer el archivo");
         }
        
        }catch(Exception e){
            
            System.out.println("Error" + e);
        } 
    }
    private void Eventos(){
        Button_Seleccionar_Importar.setOnAction(Seleccionar_Ruta_Importar);
        Button_Seleccionar_Exportar.setOnAction(Seleccionar_Ruta_Exportar);
        Button_Exportar.setOnAction(ExportarBD);
        Button_Importar.setOnAction(ImportarBD);
    } 
     
    EventHandler<ActionEvent> Seleccionar_Ruta_Exportar = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent me) {
            String ruta = null;
        
         JFileChooser ch = new JFileChooser();
        ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int se = ch.showSaveDialog(null);
        if(se == JFileChooser.APPROVE_OPTION){
             ruta = ch.getSelectedFile().getPath();
            Text_Field_URL_Exportar.setText(ruta);
        }
            }
     };    
    EventHandler<ActionEvent> Seleccionar_Ruta_Importar = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent me) {
            String ruta = null;                                   
        JFileChooser ch = new JFileChooser();
        FileNameExtensionFilter fil = new FileNameExtensionFilter("SQL","sql");
        ch.setFileFilter(fil);
        int se = ch.showSaveDialog(null);
        if(se == JFileChooser.APPROVE_OPTION){
            ruta = ch.getSelectedFile().getPath();  
            String nombre = ch.getSelectedFile().getAbsoluteFile().getName();
            String Conversion = String.valueOf(ch.getSelectedFile().length());
            Double Peso = (Double.parseDouble(Conversion)/1000);
            Cargar_tabla(nombre, Peso.toString()+"KB");
            Text_Field_URL_Importar.setText(ruta); 
        }    
                
                
                
            }
     };
    EventHandler<ActionEvent> ImportarBD = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent me) {
                String ruta = Text_Field_URL_Importar.getText();
                String backus = null;
                if(ruta.trim().length()!=0){
            try{
               // backus = "cmd /c C:\\xampp\\mysql\\bin\\mysql -u root -p root vgs < "+Validando_Ruta(ruta);
               backus = "cmd /c C:\\Archivos de programa\\MySQL\\MySQL Server 5.1\\bin\\mysql -u root -p 123456 vgs < "+Validando_Ruta(ruta);
               Runtime rt = Runtime.getRuntime();
                rt.exec(backus);
                JOptionPane.showMessageDialog(null, "Backup Importado: "+ruta);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
                    System.out.println(backus);
    }
            }
     };
    EventHandler<ActionEvent> ExportarBD = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent me) {
                String ruta = Text_Field_URL_Exportar.getText();
                String name = "\\Respaldo.sql";
        String backus = "";
        if(ruta.trim().length()!=0){
            try{
               // backus = "C:\\xampp\\mysql\\bin\\mysqldump --opt -u root -p root vgs -r "+Validando_Ruta(ruta) +"\\Respaldo.sql";
              backus = "C:\\Archivos de programa\\MySQL\\MySQL Server 5.1\\bin\\mysqldump --opt -u root -p 123456 vgs -r "+Validando_Ruta(ruta) +"\\Respaldo.sql";
               
               Runtime rt = Runtime.getRuntime();
                rt.exec(backus);
                System.out.println(backus);
                JOptionPane.showMessageDialog(null, "Respaldo creado: "+ruta);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
            }
     };

    private String Validando_Ruta(String ruta){
        
                String hilo = ruta;
                char[] t = hilo.toCharArray();
                Integer[] b = {2,3,4};
                String Uniendo= "";
                for(int i=0;i<ruta.length();i++){
                    if(t[i]==' '){    
                        Uniendo +="\" \"";
                    }else{                
                        Uniendo += ""+t[i]+"";       
                    }
                }

        return Uniendo;
    }
}