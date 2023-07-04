
package Controladores;
import Entidades.Cliente;
import Modelo.Modelo;
import Otros.Alerta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class A_Cliente_CU_Controlador implements Initializable {

    private final String RIF = "^([0-4]{1}[0-9]{7}+[-]+[0-9]{1})$";
    private final String CEDULA = "^([1-9]{1}[0-9]{6,7})$";
    private final String NOMBRE = "^([A-Z]{1}[a-z]+[ ]?){2,}$";
    private final String DIRECCION = "^([A-Za-z0-9.,-/#]+[ ]?){10,}$";
    private final String TELEFONO = "^([0-9]{7})$";
    private final String CORREO = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
    
    private Modelo modelo = new Modelo();
    private Alerta mensaje = new Alerta();
    
    private String docIdentidad = "", telefono = "", buscar = "";
    
    @FXML private JFXButton btnBuscar;
    @FXML private JFXComboBox<Character> cbRazonSocial;
    @FXML private JFXTextField txtDni;
    
    @FXML private JFXTextField txtNombre, txtNContacto, txtCorreo;
    @FXML private JFXTextArea txtDireccion;
    @FXML private JFXComboBox<String> cbTipoCliente, cbNumeroTelefono;

    @FXML private JFXButton btnAgregarCliente, btnModificarCliente;
    @FXML private ImageView btnVolver;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       abrirRegistrarCliente();
    }
    
    public void abrirRegistrarCliente(){
        cbRazonSocial.getItems().setAll('V', 'E', 'J', 'G');
        cbTipoCliente.getItems().setAll("Persona Natural", "Persona Jurídica");
        cbNumeroTelefono.getItems().setAll("0426", "0416", "0424", "0414", "0412");
        
        cbRazonSocial.setValue('V');
        cbTipoCliente.setValue("Persona Natural");
        cbNumeroTelefono.setValue("0424");
        
        txtDni.requestFocus();
        
        Botones();
        Validaciones();
        
        Enter(txtNombre, txtDni);
        Enter(txtDni, txtNombre);
        Enter(txtNombre, txtDireccion);
        Enter(txtNContacto, txtCorreo);
    }
    
    private void Botones() {
        btnVolver.setOnMouseClicked((e) -> {
            IniciarSesion_Controlador._AdministradorGeneral.mostrarCliente_RD();
        });
        
        btnAgregarCliente.setOnAction(RegistrarCliente);
        btnBuscar.setOnAction(BuscarCliente);
        btnModificarCliente.setOnAction(ModificarCliente);
    }
    
    private void Validaciones() {
        txtDni.setOnKeyTyped((event) -> {
            // Este campo debe tener máximo 10 caracteres
            if (txtDni.getText().length() < 10) {
                int b = event.getCharacter().charAt(0);

                // Este campo solo admite números y guiones cortos
                if (!Character.isDigit(b) && (b != '-')) {
                    Toolkit.getDefaultToolkit().beep();
                    event.consume();
                }
            } else {
                Toolkit.getDefaultToolkit().beep();
                event.consume();
            }
        });
        
        cbRazonSocial.setOnAction((e) -> {
            if (cbRazonSocial.getValue().equals('V') || cbRazonSocial.getValue().equals('E')) {
                cbTipoCliente.setValue("Persona Natural");
            } else {
                cbTipoCliente.setValue("Persona Jurídica");
            }
        });
        
        txtNombre.setOnKeyTyped((event) -> {
            // Este campo debe tener máximo 30 caracteres
            if (txtNombre.getText().length() < 30) {
                int a = event.getCharacter().charAt(0);
                
                // Este campo solo admite letras y espacios
                if (!(Character.isLetter(a) || Character.isSpaceChar(a))){
                   Toolkit.getDefaultToolkit().beep();
                   event.consume();
                }
            } else {
                Toolkit.getDefaultToolkit().beep();
                event.consume();
            }
        });
        
        txtNombre.setOnKeyReleased((event) -> {
            if(txtNombre.getText().length() == 1){
                event.consume();
                txtNombre.setText(txtNombre.getText().toUpperCase());
                txtNombre.end();
            }
        });
        
        txtNContacto.setOnKeyTyped((event) -> {
            // Este campo debe tener máximo 7 caracteres
            if (txtNContacto.getText().length() < 7) {
                int b = event.getCharacter().charAt(0);

                // Este campo solo admite números
                if (!Character.isDigit(b)) {
                    Toolkit.getDefaultToolkit().beep();
                    event.consume();
                }
            } else {
                Toolkit.getDefaultToolkit().beep();
                event.consume();
            }
        });
        
        txtDireccion.setOnKeyReleased((event) -> {
            if(txtDireccion.getText().length() == 1){
                event.consume();
                txtDireccion.setText(txtDireccion.getText().toUpperCase());
                txtDireccion.end();
            }
        });
        
        txtCorreo.setOnKeyTyped((event) -> {
            // Este campo debe tener máximo 35 caracteres
            if (txtCorreo.getText().length() < 35) {
                int a = event.getCharacter().charAt(0);
                
                // Este campo solo admite letras, números, puntos y el caracter especial '@'
                if (!(Character.isLetter(a) || Character.isDigit(a)) && (a != '@') && (a != '.')){
                   Toolkit.getDefaultToolkit().beep();
                   event.consume();
                }
            } else {
                Toolkit.getDefaultToolkit().beep();
                event.consume();
            }
        });
    }
    
    private final EventHandler<ActionEvent> RegistrarCliente = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            if (expresiones_Regulares() || txtNContacto.getText().length() < 7) {
                mensaje.Error("Datos no válidos",
                        "Se han encontrado campos con datos no válidos. " +
                        "Por favor, asegúrese de seguir las instrucciones de cada campo y que ninguno de éstos se encuentre vacío.");
            } else {
                if (permitirRegistro()) {
                    docIdentidad = Character.toString(cbRazonSocial.getValue()) + txtDni.getText();
                    telefono = cbNumeroTelefono.getValue() + txtNContacto.getText();
                    
                    // Registra el cliente, siempre y cuando el Documento de Identidad no se encuentre previamente registrado
                    if (modelo.registrarCliente(docIdentidad, modelo.buscarIdTipoCliente(cbTipoCliente.getValue()), txtNombre.getText(), txtDireccion.getText(), telefono, txtCorreo.getText())){
                        mensaje.errorDatosRepetidos("Cliente", "El cliente con el Documento de Identidad " + docIdentidad + " ya se encuentra registrado en la Base de Datos. Verifique.");
                    } else {
                        mensaje.registroExitoso("El cliente ");

                        limpiarCampos();
                    }
                } else {
                    mensaje.Error("Datos no válidos",
                        "Se han encontrado campos con datos no válidos. " +
                        "Por favor, asegúrese de seguir las instrucciones de cada campo y que ninguno de éstos se encuentre vacío.");
                }
            }
        }
    };
    
    private final EventHandler<ActionEvent> BuscarCliente = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            if (permitirRegistro()) {
                // Muestra los datos del Cliente en el caso de que se encuentre registrado
                if (modelo.buscarCliente(Character.toString(cbRazonSocial.getValue()) + txtDni.getText()) != null) {
                    buscar = Character.toString(cbRazonSocial.getValue()) + txtDni.getText();
                    
                    Cliente datos_consultados = (modelo.buscarCliente(buscar));
                    
                    docIdentidad = "";
                    telefono = "";
                    
                    for (Character letra: datos_consultados.getDni().toCharArray()) {
                        if (Character.isDigit(letra)) {
                            docIdentidad += letra;
                        } else {
                            cbRazonSocial.setValue(letra);
                        }
                    }
                    
                    txtDni.setText(docIdentidad);
                    cbTipoCliente.setValue(datos_consultados.getTipoCliente());
                    txtNombre.setText(datos_consultados.getNombre());
                    txtDireccion.setText(datos_consultados.getDireccion());
                    txtCorreo.setText(datos_consultados.getCorreo());
                    
                    String codtelefono = "";
                    
                    for (int i = 0; i < datos_consultados.getN_contacto().length(); i++) {
                        if (i < 4) {
                            codtelefono += datos_consultados.getN_contacto().charAt(i);
                        } else {
                            telefono += datos_consultados.getN_contacto().charAt(i);
                        }
                    }
                    
                    cbNumeroTelefono.setValue(codtelefono);
                    txtNContacto.setText(telefono);
                    
                    btnModificarCliente.setDisable(false);    // Activa el botón de Aplicar cambios
                    btnAgregarCliente.setDisable(true);    // Desactiva el botón de Agregar Cliente
                } else {
                    mensaje.Error("Datos no encontrados", "Por favor, asegúrese que el vigilante con el Documento de Identidad " + (Character.toString(cbRazonSocial.getValue()) + txtDni.getText()) + " se encuentre registrado.");
                }
            } else {
                mensaje.Error("Documento de Identidad no válido", "Por favor, asegúrese de seguir las instrucciones y que el campo no esté vacío.");
            }
        }
    };
    
    private final EventHandler<ActionEvent> ModificarCliente = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            if (expresiones_Regulares() || txtNContacto.getText().length() < 7) {
                mensaje.Error("Datos no válidos",
                        "Se han encontrado campos con datos no válidos. " +
                        "Por favor, asegúrese de seguir las instrucciones de cada campo y que ninguno de éstos se encuentre vacío.");
            } else {
                if (permitirRegistro()) {
                    
                    docIdentidad = Character.toString(cbRazonSocial.getValue()) + txtDni.getText();
                    telefono = cbNumeroTelefono.getValue() + txtNContacto.getText();
                
                    // Actualiza los datos del Cliente, siempre y cuando el Documento de Identidad no se encuentre previamente registrado
                    if (modelo.actualizarCliente(buscar, docIdentidad, modelo.buscarIdTipoCliente(cbTipoCliente.getValue()), txtNombre.getText(), txtDireccion.getText(), telefono, txtCorreo.getText())) {
                        mensaje.errorDatosRepetidos("Cliente", "El cliente con el Documento de Identidad " + docIdentidad + " ya se encuentra registrado en la Base de Datos. Verifique.");
                    } else {
                        mensaje.actualizacionExitosa("cliente");

                        btnModificarCliente.setDisable(true); // Desactiva el botón de Aplicar cambios
                        btnAgregarCliente.setDisable(false);  // Activa el botón de Agregar cliente
                        limpiarCampos();
                    }
                } else {
                    mensaje.Error("Datos no válidos",
                        "Se han encontrado campos con datos no válidos. " +
                        "Por favor, asegúrese de seguir las instrucciones de cada campo y que ninguno de éstos se encuentre vacío.");
                }
            }
        }
    };
    
    private boolean expresiones_Regulares(){
        return (!Pattern.matches(NOMBRE, txtNombre.getText())
                || !Pattern.matches(DIRECCION, txtDireccion.getText())
                || !Pattern.matches(TELEFONO, txtNContacto.getText())
                || !Pattern.matches(CORREO, txtCorreo.getText()));
    }
    
    private boolean validarTelefono() {
        switch(txtNContacto.getText()) {
            case "0123456":
            case "1234567":
            case "1111111":
            case "2222222":
            case "3333333":
            case "4444444":
            case "5555555":
            case "6666666":
            case "7777777":
            case "8888888":
            case "9999999": return true;
            default: return false;
        }
    }
    
    private boolean permitirRegistro() {
        if (cbRazonSocial.getValue().equals('V') || cbRazonSocial.getValue().equals('E')) {
            return Pattern.matches(CEDULA, txtDni.getText());
        } else {
            return Pattern.matches(RIF, txtDni.getText());
        }
    }
    
    private void limpiarCampos() {
        cbRazonSocial.setValue('V');
        txtDni.clear();
        
        cbTipoCliente.setValue("Persona Natural");
        txtNombre.clear();
        txtDireccion.clear();
        
        cbNumeroTelefono.setValue("0424");
        txtNContacto.clear();      
        
        txtCorreo.clear();
    }
    
    private void Enter(Node primer_control, Node segundo_control) {
        TextField temp = (TextField) primer_control;
        temp.setOnAction((ActionEvent event) -> {
            if (temp.getText().length() == 0)
                event.consume();
            else segundo_control.requestFocus();
        });
    };
    
}