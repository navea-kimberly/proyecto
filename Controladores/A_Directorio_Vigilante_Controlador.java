package Controladores;

import Entidades.Vigilante;
import Otros.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import Otros.Validaciones;
import Modelo.Modelo;
import Otros.Alerta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class A_Directorio_Vigilante_Controlador implements Initializable {

    @FXML private TableView<Vigilante> TableView_Directorio_Vigilantes;
    @FXML private TableColumn<Vigilante, Vigilante> TableColumn_Conteo;
    @FXML private TableColumn<Vigilante, String> TableColumn_DNI;
    @FXML private TableColumn<Vigilante, String> TableColumn_Nombres;
    @FXML private TableColumn<Vigilante, String> TableColumn_Apellidos;
    @FXML private TableColumn<Vigilante, String> TableColumn_Nmr_Contacto;
    @FXML private TableColumn<Vigilante, String> TableColumn_Fecha_de_ingreso;
    @FXML private TableColumn<Vigilante, String> TableColumn_Fecha_de_nacimiento;
    
    @FXML private JFXTextField TextField_Identificador;
    @FXML private JFXTextField TextField_Nombre;

    private Validaciones validar = new Validaciones();
    private Mensajes aletar = new Mensajes();
    
    Modelo modelo = new Modelo();
    Alerta mensaje = new Alerta();
    
    @FXML private JFXButton btnBorrar1;
    @FXML private JFXButton btn_Resgistrar_Vigilante;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estado_inicio();
    }    

    private void estado_inicio() {    
        inicializarColumnas ();   
        Botones();
        Cargar_Tabla();
    }
    
    private void Cargar_Tabla(){
        ObservableList<Vigilante> Datos_Temp =  FXCollections.observableArrayList();
        ObservableList<Vigilante> Datos_Tabla =  FXCollections.observableArrayList();
        try{
            Datos_Tabla =  FXCollections.observableArrayList(modelo.tablaVigilantes());
        }catch(Exception e){
            
            aletar.Mensaje("Error, Los datos no fueron encontrado");
            
        } 
        Datos_Temp.addAll(Datos_Tabla);
        
        TableView_Directorio_Vigilantes.getItems().clear();
        TableView_Directorio_Vigilantes.setItems(Datos_Tabla);
        
        if(Datos_Tabla.isEmpty()){
            aletar.Mensaje("No se encontro ningun resultado");
        }

        TextField_Identificador.setOnKeyTyped((KeyEvent event) -> {
            char a = event.getCharacter().toUpperCase().charAt(0);
            if ((a=='V'|| a=='E'|| a=='J'|| a=='G') && TextField_Identificador.getText().toCharArray().length<1){
            }else{
                if(TextField_Identificador.getText().toCharArray().length==0){
                    validar.QuitarNumerosYSignos(event);
                }
                if(TextField_Identificador.getText().toCharArray().length<=10){
                    validar.QuitarLetrasYTodosLosSignosCodigo(event);
                }else{
                    event.consume();
                }
            }
            char[] caracteresp, caracterese;
            String texto1 = TextField_Identificador.getText();
            ObservableList<Vigilante> Nuevos_Datos = FXCollections.observableArrayList() ;
            char as= event.getCharacter().charAt(0);
            int valor = Character.getNumericValue(as);
            if (valor==-1){
            }else{
                texto1 += String.valueOf(a);
            } 
            String predeterminada, entrada;
            for(int p= 0 ; p<Datos_Temp.size(); p ++){
                entrada = texto1.toUpperCase();
                predeterminada = Datos_Temp.get(p).getDni().toUpperCase();
                
                caracteresp = predeterminada.toCharArray();
                caracterese = entrada.toCharArray();
                
                int tamaño;
                if(caracterese.length < caracteresp.length)
                    tamaño = caracteresp.length;
                else
                    tamaño = caracterese.length;
                
                int ultima_posicion = 0;
                try
                {
                    for(int i = 0; i <= tamaño; i++)
                    {
                        ultima_posicion = i;
                        if(caracteresp[i] != caracterese[i]) 
                        {
                            break;
                        }else{
                            if(i == caracteresp.length){
                                Nuevos_Datos.add(Datos_Temp.get(p));
                            }
                        }
                    }
                   
                } catch (ArrayIndexOutOfBoundsException e){
                    if(caracterese.length <= caracteresp.length){
                        Nuevos_Datos.add(Datos_Temp.get(p));
                    }    
                }
            }
            
            TableView_Directorio_Vigilantes.getItems().clear();
            
            if (Nuevos_Datos.isEmpty()) {
                mostrarVigilantesRegistrados();
                mensaje.sinResultados("un vigilante");
            } else {
                TableView_Directorio_Vigilantes.setItems(Nuevos_Datos);
            }
        });
        
        TextField_Nombre.setOnKeyTyped((KeyEvent event) -> {
            char a = event.getCharacter().toUpperCase().charAt(0);
            char[] caracteresp, caracterese;
            String texto1 = TextField_Nombre.getText();
            ObservableList<Vigilante> Nuevos_Datos = FXCollections.observableArrayList() ;
            char as= event.getCharacter().charAt(0);
            int valor = Character.getNumericValue(as);
            if (valor==-1){
            }else{
                texto1 += String.valueOf(a);
            }
            String predeterminada, entrada;
            for(int p= 0 ; p<Datos_Temp.size(); p ++){
                entrada = texto1.toUpperCase();
                predeterminada = Datos_Temp.get(p).getNombres().toUpperCase();
                
                caracteresp = predeterminada.toCharArray();
                caracterese = entrada.toCharArray();

                int tamaño;
                if(caracterese.length < caracteresp.length) 
                    tamaño = caracteresp.length;
                else
                    tamaño = caracterese.length;
                
                int ultima_posicion = 0; 
                try
                {
                    for(int i = 0; i <= tamaño; i++)
                    {
                        ultima_posicion = i;
                        if(caracteresp[i] != caracterese[i]) 
                        {
                            break;
                        }else{
                            if(i == caracteresp.length){
                                Nuevos_Datos.add(Datos_Temp.get(p));}
                        }
                    }
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    if(caracterese.length <= caracteresp.length){
                        Nuevos_Datos.add(Datos_Temp.get(p));    
                    }
                }
            }
            
            TableView_Directorio_Vigilantes.getItems().clear();
            
            if (Nuevos_Datos.isEmpty()) {
                mostrarVigilantesRegistrados();
                mensaje.sinResultados("un vigilante");
            } else {
                TableView_Directorio_Vigilantes.setItems(Nuevos_Datos);
            }
        });     
    }
    
    private void Botones(){
        btn_Resgistrar_Vigilante.setOnAction((e) -> {
            IniciarSesion_Controlador._AdministradorGeneral.mostrarVigilante_CU();
        });
       
        TextField_Identificador.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
                public void handle(KeyEvent event){
                    char a = event.getCharacter().toUpperCase().charAt(0);
                    
                    if ((a=='V'|| a=='E'|| a=='J'|| a=='G') && TextField_Identificador.getText().toCharArray().length<1){
                    }else{
                        if(TextField_Identificador.getText().toCharArray().length==0){
                        validar.QuitarNumerosYSignos(event);
                            }
                        if(TextField_Identificador.getText().toCharArray().length<=10){
                        validar.QuitarLetrasYTodosLosSignosCodigo(event);
                         }else{
                           event.consume();
                        }
                    }
                }  
        });
    }

    private void inicializarColumnas() {
        TableColumn_Conteo.setCellValueFactory(new PropertyValueFactory <>("#")); 
        TableColumn_DNI.setCellValueFactory(new PropertyValueFactory <>("dni"));
        TableColumn_Nombres.setCellValueFactory(new PropertyValueFactory <>("nombres"));
        TableColumn_Apellidos.setCellValueFactory(new PropertyValueFactory <>("apellidos"));    
        TableColumn_Fecha_de_ingreso.setCellValueFactory(new PropertyValueFactory <>("fecha_ingreso"));  
        TableColumn_Nmr_Contacto.setCellValueFactory(new PropertyValueFactory <>("telefono"));  
        TableColumn_Fecha_de_nacimiento.setCellValueFactory(new PropertyValueFactory <>("fecha_nacimiento"));  
        
        TableColumn_Conteo.setCellFactory(new Callback<TableColumn<Vigilante, Vigilante>, TableCell<Vigilante, Vigilante>>() {
            @Override public TableCell<Vigilante, Vigilante> call(TableColumn<Vigilante, Vigilante> param) {
                return new TableCell<Vigilante, Vigilante>() {
                    @Override protected void updateItem(Vigilante item, boolean empty) {
                        super.updateItem(item, empty);
                        if (this.getTableRow() != null) {
                            int index = this.getTableRow().getIndex();
                            if( index < TableView_Directorio_Vigilantes.getItems().size()) {
                                int rowNum = index + 1;
                                setText( String.valueOf(rowNum));
                            } else {
                                setText("");
                            }
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
    }
    
    private void mostrarVigilantesRegistrados() {
        // Muestra todos los clientes almacenados en la BD
        TableView_Directorio_Vigilantes.setItems(FXCollections.observableArrayList(modelo.tablaVigilantes()));
        TableView_Directorio_Vigilantes.refresh();
    }
}
