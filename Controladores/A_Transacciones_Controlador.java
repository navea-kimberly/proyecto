
package Controladores;

import Entidades.Transacciones;
import Modelo.Modelo;
import Otros.Alerta;
import Otros.Mensajes;
import Otros.Validaciones;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;


public class A_Transacciones_Controlador implements Initializable {

    @FXML
    private JFXButton btnRegistrar, btnGuardar, btnBuscar;
   
    @FXML
    private JFXTextField txtRdg, txtContrato, dniCliente, dniVigilante,
            nombreVigilante, txtMonto, nombreCliente, txtReferencia;
           
    @FXML
    private JFXComboBox cbEstado, cbMetodo, cbtipo;
    
    @FXML
    private JFXDatePicker diaRealizado, dateInicio, fechaPagado ;
    @FXML
    private Pane Pane_Pago;
 


 
    
    
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        IniciarRegistro();
        eventos();
      }
    
    private void eventos(){
        Validaciones validar = new  Validaciones();
        txtRdg.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
                public void handle(KeyEvent event){
                    
                    if(txtRdg.getText().length()>10)event.consume();
                   validar.QuitarLetrasYTodosLosSignosCodigo(event);
                }});
        txtReferencia.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
                public void handle(KeyEvent event){
                    
                    if(txtRdg.getText().length()>10)event.consume();
                   validar.QuitarLetrasYTodosLosSignosCodigo(event);
                }});
        txtMonto.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
                public void handle(KeyEvent event){
                    
                    if(txtRdg.getText().length()>10)event.consume();
                   validar.QuitarLetrasYTodosLosSignosCodigo(event);
                }});

        btnBuscar.setOnAction(Seleccionar_busqueda);
        btnRegistrar.setOnAction(Registrar_Datos);
     
        
    }
    
    public void IniciarRegistro(){
        ObservableList<String> strings = FXCollections.observableArrayList();  
        Modelo modelo = new Modelo ();
        Transacciones datos = modelo.Tipos_combobox_Transaccion();
        cbEstado.getItems().setAll(observableList(datos.getDatos_estado_transaccion()));
        cbMetodo.getItems().setAll(datos.getDatos_metodo_transaccion());
        cbtipo.getItems().setAll(datos.getDatos_tipo_transaccion());
        
    }
    
    EventHandler<ActionEvent> Seleccionar_busqueda  = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent me) {
                
                try{
                    Mensajes aletar = new Mensajes();
                    Transacciones Datos = new Transacciones();
                    Modelo modelo = new Modelo();
                    System.out.println("rico");
                    Datos= modelo.Datos_Transacciones(Integer.parseInt(txtRdg.getText()));
                        if(Datos==null){
                            }
                            
                                txtContrato.setText( Datos.getContrato());
                                dateInicio.setValue((Datos.getFecha__inicio_contrato()));
                                dniCliente.setText(Datos.getDniCliente());
                                nombreCliente.setText(Datos.getNombreCliente());
                                dniVigilante.setText(Datos.getDniVigilante());
                                nombreVigilante.setText(Datos.getNombreVigilante());//  
                                diaRealizado.setValue(Datos.getFecha_rdg());
                    
                        }catch(Exception e){
                            Mensajes.Mensaje("Error no se encontro el rol de guardia");
                            }
                        }
                    };
    
    EventHandler<ActionEvent> Registrar_Datos = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent me) {
            try{
                
                    if(Validar()){}
                        else{
                                    int tipo_pago =cbtipo.getSelectionModel().getSelectedIndex()+1;  
                                    LocalDate dia_realizado= diaRealizado.getValue();
                                    String codigo_rdg = txtRdg.getText();
                                    Double monto = Double.valueOf(txtMonto.getText());
                                    int estado_pago = cbEstado.getSelectionModel().getSelectedIndex()+1;
                                    int metodo_pago = cbMetodo.getSelectionModel().getSelectedIndex()+1;
                                    LocalDate fecha_del_pago = fechaPagado.getValue();
                                    String referencia = txtReferencia.getText();
                                    String contrato = txtContrato.getText();

                                        Mensajes aletar = new Mensajes(); 
                                        Transacciones Datos = new Transacciones(tipo_pago,dia_realizado,codigo_rdg,monto,estado_pago,metodo_pago,fecha_del_pago,referencia,contrato);
                                        Modelo modelo = new Modelo();
                                        String Resultado = modelo.Resgistro_Transaccion_pago(Datos);
                                        Mensajes.Mensaje(Resultado);
                                            }
                               
                                    
                                    
                                    
                                
                            
                                
                            }catch(Exception e){    
                    Mensajes.Mensaje("Ocurrio un error, por favor verifique los datos");
                                }
                            }      
                        };
    
       private boolean Validar(){
        boolean Respuesta = false;
        String Mensaje = "Error en los siguientes campos: ";

                if(diaRealizado.getValue()==null){ Mensaje += "\nFecha del dia realiazdo no seleccionado";  Respuesta = true;}
                if(txtMonto.getText().isEmpty()){Mensaje += "\nNo ingreso un monto"; Respuesta = true;}
                if(txtRdg.getText().isEmpty()){Mensaje +="\nNo ingreso el rol de guarda a pagar"; Respuesta = true;}
                if(cbEstado.getSelectionModel().isEmpty()){Mensaje +="\nNo selecciono el estado del pago"; Respuesta = true;}
                if(cbMetodo.getSelectionModel().isEmpty()){Mensaje +="\nNo selecciono metodo de pago"; Respuesta = true;}

                    if(Respuesta==true){
                   Mensajes.Mensaje(Mensaje);
                    }
                    
            return Respuesta;
       }
}
