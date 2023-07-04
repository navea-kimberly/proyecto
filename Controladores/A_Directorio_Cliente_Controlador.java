package Controladores;

import Entidades.Cliente;
import Modelo.Modelo;
import Otros.Alerta;
import Otros.Mensajes;
import Otros.Validaciones;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class A_Directorio_Cliente_Controlador implements Initializable {

    @FXML private JFXTextField TextField_Identificador;
    @FXML private JFXTextField TextField_Nombre;
    
    Modelo modelo = new Modelo();
    Alerta mensaje = new Alerta();
    
    @FXML private TableView<Cliente> TableView_Directorio_Clientes;
    @FXML private TableColumn<Cliente, Cliente> TableColumn_Conteo;
    @FXML private TableColumn<Cliente, String> TableColumn_DNI;
    @FXML private TableColumn<Cliente, String> TableColumn_Nombres;
    @FXML private TableColumn<Cliente, String> TableColumn_Contacto;
    @FXML private TableColumn<Cliente, String> TableColumn_Tipo_Cliente;
    @FXML private TableColumn<Cliente, String> TableColumn_Direccion;
    @FXML private TableColumn<Cliente, String> TableColumn_Correo;
    
    @FXML private JFXButton btnCUCliente, btnEliminarCliente;
    
    private Validaciones validar = new Validaciones();
    private Mensajes aletar = new Mensajes();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estado_inicio();
    }    
       
    private void estado_inicio(){    
        inicializarColumnas ();   
        Botones();
        Cargar_Tabla();
    }
    
    private void Cargar_Tabla(){
        ObservableList<Cliente> Datos_Temp =  FXCollections.observableArrayList();
        ObservableList<Cliente> Datos_Tabla =  FXCollections.observableArrayList();
        try{
            Datos_Tabla =  FXCollections.observableArrayList(modelo.tablaClientes());
        }catch(Exception e){
            
            aletar.Mensaje("Error, Los datos no fueron encontrado");
            
        } 
        Datos_Temp.addAll(Datos_Tabla);
        TableView_Directorio_Clientes.getItems().clear();
        TableView_Directorio_Clientes.setItems(Datos_Tabla);
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
            ObservableList<Cliente> Nuevos_Datos = FXCollections.observableArrayList() ;
            char as= event.getCharacter().charAt(0);
            System.out.println("As: " + as);
            int valor = Character.getNumericValue(as);
            System.out.println("Valor: " + valor);
            if (valor==-1){
                System.out.println("Por aquí");
            }else{
                System.out.println("Por acá");
                texto1 += String.valueOf(a);
            } 
            System.out.println("Texto1: " + texto1);
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
            
            TableView_Directorio_Clientes.getItems().clear();
            
            if (Nuevos_Datos.isEmpty()) {
                mostrarClientesRegistrados();
                mensaje.sinResultados("un cliente");
            } else {
                TableView_Directorio_Clientes.setItems(Nuevos_Datos);
            }
        });
        
        TextField_Nombre.setOnKeyTyped((KeyEvent event) -> {
            char a = event.getCharacter().toUpperCase().charAt(0);
            char[] caracteresp, caracterese;
            String texto1 = TextField_Nombre.getText();
            ObservableList<Cliente> Nuevos_Datos = FXCollections.observableArrayList() ;
            char as= event.getCharacter().charAt(0);
            int valor = Character.getNumericValue(as);
            if (valor==-1){
            }else{
                texto1 += String.valueOf(a);
            }
            String predeterminada, entrada;
            for(int p= 0 ; p<Datos_Temp.size(); p ++){
                entrada = texto1.toUpperCase();
                predeterminada = Datos_Temp.get(p).getNombre().toUpperCase();
                
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
            
            TableView_Directorio_Clientes.getItems().clear();
            
            if (Nuevos_Datos.isEmpty()) {
                mostrarClientesRegistrados();
                mensaje.sinResultados("un cliente");
            } else {
                TableView_Directorio_Clientes.setItems(Nuevos_Datos);
            }
        });    
    }
    
    private void Botones(){
        btnCUCliente.setOnAction((e) -> {
            IniciarSesion_Controlador._AdministradorGeneral.mostrarCliente_CU();
        });
        
        btnEliminarCliente.setOnAction(EliminarCliente);
    }
    
    private void inicializarColumnas() {
        TableColumn_Conteo.setCellValueFactory(new PropertyValueFactory <>("#")); 
        TableColumn_DNI.setCellValueFactory(new PropertyValueFactory <>("dni"));
        TableColumn_Nombres.setCellValueFactory(new PropertyValueFactory <>("nombre"));
        TableColumn_Tipo_Cliente.setCellValueFactory(new PropertyValueFactory <>("tipoCliente"));
        TableColumn_Contacto.setCellValueFactory(new PropertyValueFactory <>("n_contacto"));
        TableColumn_Direccion.setCellValueFactory(new PropertyValueFactory <>("direccion"));
        TableColumn_Correo.setCellValueFactory(new PropertyValueFactory <>("correo"));
        
        TableColumn_Conteo.setCellFactory((TableColumn<Cliente, Cliente> param) -> new TableCell<Cliente, Cliente>() {
            @Override protected void updateItem(Cliente item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null) {
                    int index = this.getTableRow().getIndex();
                    if( index < TableView_Directorio_Clientes.getItems().size()) {
                        int rowNum = index + 1;
                        setText( String.valueOf(rowNum));
                    } else {
                        setText("");
                    }
                } else {
                    setText("");
                }
            }
        });
        
        TableColumn_Direccion.setCellFactory((TableColumn<Cliente, String> param) -> new TableCell<Cliente, String>() {
            private Text text;

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                text = new Text(item);
                text.setWrappingWidth(TableColumn_Direccion.getWidth());
                this.setWrapText(true);

                setGraphic(text);
            }
        });
    }
    
    private final EventHandler<ActionEvent> EliminarCliente = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e){
            mensaje.Informacion("cliente");
            
            TableView_Directorio_Clientes.setOnMouseClicked((MouseEvent event) -> {
                if (TableView_Directorio_Clientes.getSelectionModel().getSelectedItem() != null) {
                    if (mensaje.eliminarDatos("cliente", TableView_Directorio_Clientes.getSelectionModel().getSelectedItem().getDni())) {
                        modelo.eliminarCliente(TableView_Directorio_Clientes.getSelectionModel().getSelectedItem().getDni());
                        
                        mostrarClientesRegistrados();
                    }
                }
            });
        }
    };
    
    private void mostrarClientesRegistrados() {
        // Muestra todos los clientes almacenados en la BD
        TableView_Directorio_Clientes.setItems(FXCollections.observableArrayList(modelo.tablaClientes()));
        TableView_Directorio_Clientes.refresh();
    }
}
