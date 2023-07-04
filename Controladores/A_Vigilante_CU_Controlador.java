package Controladores;

import Entidades.Vigilante;
import Modelo.Modelo;
import Otros.Alerta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.Toolkit;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Callback;


public class A_Vigilante_CU_Controlador implements Initializable {

    private final String CEDULA = "^([1-9]{1}[0-9]{6,7})$";
    private final String NOMBRES = "^([A-Z]{1}[a-z]+[ ]?){1,2}$";
    private final String APELLIDOS = "^([A-Z]{1}[a-z]+[ ]?){2}$";
    private final String DIRECCION = "^([A-Za-z0-9.,-/#]+[ ]?){10,}$";
    private final String TELEFONO = "^([0-9]{7})$";
    
    private Modelo modelo = new Modelo();
    private Alerta mensaje = new Alerta();
    
    private String docIdentidad = "", telefono = "", buscar = "";
    
    @FXML private JFXButton btnBuscar;
    @FXML private JFXComboBox<Character> CedulaCombobox;
    @FXML private JFXTextField txtDni;
    
    @FXML private JFXTextField txtNombres, txtApellidos;
    @FXML private JFXDatePicker dpFechaNacimiento, dpFechaIngreso;
    @FXML private JFXTextArea txtDireccion;
    @FXML private JFXComboBox TelefonoCombobox;
    @FXML private JFXTextField txtNContacto;
    @FXML private JFXCheckBox chbJefeGrupo, chbDisponible;
    
    @FXML private JFXButton btnRegistrarVigilante, btnModificarVigilante;
    @FXML private ImageView btnVolver;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IniciarRegistroVig();
    }
    
    public void IniciarRegistroVig() {
        CedulaCombobox.getItems().setAll('V', 'E');
        TelefonoCombobox.getItems().setAll("0426", "0416", "0424", "0414", "0412");
        
        CedulaCombobox.setValue('V');
        TelefonoCombobox.setValue("0424");
       
        txtDni.requestFocus();
        
        Botones();
        Validaciones();
        
        Enter(txtDni, txtNombres);
        Enter(txtNombres, txtApellidos);
        Enter(txtApellidos, dpFechaNacimiento);
    }
    
    private void Botones() {
        btnVolver.setOnMouseClicked((e) -> {
            IniciarSesion_Controlador._AdministradorGeneral.mostrarVigilante_R();
        });
        
        btnRegistrarVigilante.setOnAction(RegistrarVigilante);
        btnBuscar.setOnAction(BuscarVigilante);
        btnModificarVigilante.setOnAction(ModificarVigilante);
    }
    
    private void Validaciones() {
        txtDni.setOnKeyTyped((event) -> {
            // Este campo debe tener máximo 8 caracteres
            if (txtDni.getText().length() < 8) {
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
        
        txtNombres.setOnKeyTyped((event) -> {
            // Este campo debe tener máximo 20 caracteres
            if (txtNombres.getText().length() < 20) {
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
        
        txtNombres.setOnKeyReleased((event) -> {
            if(txtNombres.getText().length() == 1){
                event.consume();
                txtNombres.setText(txtNombres.getText().toUpperCase());
                txtNombres.end();
            }
        });
        
        txtApellidos.setOnKeyTyped((event) -> {
            // Este campo debe tener máximo 20 caracteres
            if (txtApellidos.getText().length() < 20) {
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
        
        txtApellidos.setOnKeyReleased((event) -> {
            if(txtApellidos.getText().length() == 1){
                event.consume();
                txtApellidos.setText(txtApellidos.getText().toUpperCase());
                txtApellidos.end();
            }
        });
        
        // Activa el campo de Fecha de Cierre cuando es seleccionada una Fecha de Inicio
        dpFechaNacimiento.setOnAction((event) -> {
            dpFechaIngreso.setDisable(dpFechaNacimiento.getValue() == null);
        });
        
        // Valida las fechas ingresadas
        dpFechaNacimiento.setDayCellFactory(validacionFechaNacimiento);
        dpFechaIngreso.setDayCellFactory(validacionFechaIngreso);
        
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
    }
    
    private boolean expresiones_Regulares(){
        return (!Pattern.matches(CEDULA, txtDni.getText())
                || !Pattern.matches(NOMBRES, txtNombres.getText())
                || !Pattern.matches(APELLIDOS, txtApellidos.getText())
                || !Pattern.matches(DIRECCION, txtDireccion.getText())
                || !Pattern.matches(TELEFONO, txtNContacto.getText()));
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
    
    private boolean validarDatos() {
        if (expresiones_Regulares() || dpFechaNacimiento.getValue() == null || dpFechaIngreso.getValue() == null) {
            mensaje.Error("Datos no válidos", "Por favor, asegúrese de seguir las instrucciones y que el campo no esté vacío.");
            return false;
        } else {
            if (dpFechaNacimiento.getValue().isBefore(LocalDate.of(1940, 1, 1)) || dpFechaNacimiento.getValue().isAfter(LocalDate.of(2003, LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()))
                || dpFechaIngreso.getValue().isBefore(dpFechaNacimiento.getValue()) || dpFechaIngreso.getValue().isBefore(LocalDate.of(1990, 2, 14)) || dpFechaIngreso.getValue().isAfter(LocalDate.now())) {
                mensaje.Error("Se ha encontrado un error", "Fecha de Nacimiento o Fecha de Ingreso no válida.");
                return false;
            } else {
                if (validarTelefono() || Integer.valueOf(txtNContacto.getText()) < 100009) {
                    mensaje.Error("Se ha encontrado un error", "El número de contacto ingresado no existe.");
                    return false;
                } else {
                    return true;
                }
            }
        }
    }
    
    private final EventHandler<ActionEvent> RegistrarVigilante = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            if (validarDatos()) {
                docIdentidad = Character.toString(CedulaCombobox.getValue()) + txtDni.getText();
                telefono = TelefonoCombobox.getValue() + txtNContacto.getText();
                
                // Registra el vigilante, siempre y cuando el Documento de Identidad no se encuentre previamente registrado
                if (modelo.registrarVigilante(docIdentidad, txtNombres.getText(), txtApellidos.getText(), dpFechaNacimiento.getValue(), txtDireccion.getText(), telefono, dpFechaIngreso.getValue(), chbJefeGrupo.isSelected(), chbDisponible.isSelected())) {
                    mensaje.errorDatosRepetidos("Vigilante", "El vigilante con el Documento de Identidad " + docIdentidad + " ya se encuentra registrado en la Base de Datos. Verifique.");
                } else {
                    mensaje.registroExitoso("El vigilante con el Documento de Identidad " + docIdentidad);
                    
                    limpiarCampos();
                }
            }
        }
    };
    
    private final EventHandler<ActionEvent> BuscarVigilante = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            if (!Pattern.matches(CEDULA, txtDni.getText())) {
                mensaje.Error("Documento de Identidad no válido", "Por favor, asegúrese de seguir las instrucciones y que el campo no esté vacío.");
            } else {
                // Muestra los datos del Vigilante en el caso de que se encuentre registrado
                if (modelo.buscarVigilante(Character.toString(CedulaCombobox.getValue()) + txtDni.getText()) != null) {
                    buscar = Character.toString(CedulaCombobox.getValue()) + txtDni.getText();
                    
                    dpFechaIngreso.setDisable(false);
                    
                    Vigilante datos_consultados = (modelo.buscarVigilante(buscar));
                    
                    docIdentidad = "";
                    telefono = "";
                    
                    for (Character letra: datos_consultados.getDni().toCharArray()) {
                        if (Character.isDigit(letra)) {
                            docIdentidad += letra;
                        } else {
                            CedulaCombobox.setValue(letra);
                        }
                    }
                    
                    txtDni.setText(docIdentidad);
                    txtNombres.setText(datos_consultados.getNombres());
                    txtApellidos.setText(datos_consultados.getApellidos());
                    dpFechaNacimiento.setValue(datos_consultados.getFecha_nacimiento());
                    dpFechaIngreso.setValue(datos_consultados.getFecha_ingreso());
                    txtDireccion.setText(datos_consultados.getDireccion());
                    
                    String codtelefono = "";
                    
                    for (int i = 0; i < datos_consultados.getTelefono().length(); i++) {
                        if (i < 4) {
                            codtelefono += datos_consultados.getTelefono().charAt(i);
                        } else {
                            telefono += datos_consultados.getTelefono().charAt(i);
                        }
                    }
                    
                    TelefonoCombobox.setValue(codtelefono);
                    txtNContacto.setText(telefono);
                    
                    chbJefeGrupo.setSelected(datos_consultados.isJefe_grupo());
                    chbDisponible.setSelected(datos_consultados.isDisponible());
                    
                    btnModificarVigilante.setDisable(false); // Activa el botón de Aplicar cambios
                    btnRegistrarVigilante.setDisable(true);    // Desactiva el botón de Agregar vigilante
                } else {
                    mensaje.Error("Datos no encontrados", "Por favor, asegúrese que el vigilante con el Documento de Identidad " + (Character.toString(CedulaCombobox.getValue()) + txtDni.getText()) + " se encuentre registrado.");
                }
            }
        }
    };
    
    private final EventHandler<ActionEvent> ModificarVigilante = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            if (validarDatos()) {
                docIdentidad = Character.toString(CedulaCombobox.getValue()) + txtDni.getText();
                telefono = TelefonoCombobox.getValue() + txtNContacto.getText();
                
                // Actualiza los datos del vigilante, siempre y cuando el Documento de Identidad no se encuentre previamente registrado
                if (modelo.actualizarVigilante(buscar, docIdentidad, txtNombres.getText(), txtApellidos.getText(), dpFechaNacimiento.getValue(), txtDireccion.getText(), telefono, dpFechaIngreso.getValue(), chbJefeGrupo.isSelected(), chbDisponible.isSelected())) {
                    mensaje.errorDatosRepetidos("Vigilante", "El vigilante con el Documento de Identidad " + docIdentidad + " ya se encuentra registrado en la Base de Datos. Verifique.");
                } else {
                    mensaje.actualizacionExitosa("vigilante");
                 
                    btnModificarVigilante.setDisable(true); // Desactiva el botón de Aplicar cambios
                    btnRegistrarVigilante.setDisable(false);    // Activa el botón de Agregar vigilante
                    limpiarCampos();
                }
            }            
        }
    };
    
    private void limpiarCampos() {
        CedulaCombobox.setValue('V');
        txtDni.clear();
        
        txtNombres.clear();
        txtApellidos.clear();
        
        dpFechaNacimiento.getEditor().clear();
        dpFechaIngreso.getEditor().clear();
        
        txtDireccion.clear();
        
        TelefonoCombobox.setValue("0424");
        txtNContacto.clear();
        
        chbJefeGrupo.setSelected(false);
        chbDisponible.setSelected(false);
        
        dpFechaIngreso.setDisable(true);
    }

    final Callback<DatePicker, DateCell> validacionFechaNacimiento = (final DatePicker datePicker) -> new DateCell() {
        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            
            if (item.isBefore(LocalDate.of(1940, 1, 1)) || item.isAfter(LocalDate.of(2003, LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()))) {
                setDisable(true);
                setStyle("-fx-background-color: #ffb3d1;");
            }
        }
    };
    
    final Callback<DatePicker, DateCell> validacionFechaIngreso = (final DatePicker datePicker) -> new DateCell() {
        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            
            if (item.isBefore(dpFechaNacimiento.getValue()) || item.isBefore(LocalDate.of(1990, 2, 14)) || item.isAfter(LocalDate.now())) {
                setDisable(true);
                setStyle("-fx-background-color: #ffb3d1;");
            }
        }
    };
    
    private void Enter(Node primer_control, Node segundo_control) {
        TextField temp = (TextField) primer_control;
        temp.setOnAction((ActionEvent event) -> {
            if (temp.getText().length() == 0)
                event.consume();
            else segundo_control.requestFocus();
        });
    };
}