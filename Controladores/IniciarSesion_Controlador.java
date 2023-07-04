package Controladores;

import Modelo.Modelo;
import Otros.Alerta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class IniciarSesion_Controlador implements Initializable {

//    INSERT INTO `usuario`(`cod_usuario`, `nombre`, `clave`, `id_tipo`) VALUES ("A190001", "ADMIN", "025609", 1)
//    INSERT INTO `usuario`(`cod_usuario`, `nombre`, `clave`, `id_tipo`) VALUES ("V28019108", "KIM" ,"1234", 3)    
    
    private double x, y;
    private Stage stage;
    
    private final String CODIGO_VIGILANTE = "^([V]{1}+[1-9]{1}[0-9]{6,7})$";
    private final String CODIGO_USUARIO = "^([A,S]{1}+[1-9]{1}[0-9]{5})$";
    
    public static A_modPrincipal_Controlador _AdministradorGeneral;
    public static String usuario;
  
    private Alerta mensaje = new Alerta();
    private Modelo modelo = new Modelo();
    
    @FXML private Label barraSuperior, lblMinimizarVentana, lblCerrarVentana;
    
    @FXML private JFXTextField txtCodUsuario, txtUsername;
    @FXML private JFXPasswordField txtPassword;
    @FXML private JFXButton btnIniciarSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void abrirIniciarSesion() {
        ventanaPersonalizada();
        btnIniciarSesion.setDisable(true); // El botón de Iniciar Sesión está desactivado por defecto
        
        Validaciones();
        Enter(txtCodUsuario, txtUsername);
        Enter(txtUsername, txtPassword);
        
        btnIniciarSesion.setOnAction(IniciarSesion);
        sinComplicaciones();
    }
    
    private void sinComplicaciones() {
        txtCodUsuario.setText("A190001");
        txtUsername.setText("ADMIN");
        txtPassword.setText("025609");
    }
    
    private void Validaciones() {
        txtCodUsuario.setOnKeyReleased((event) -> {
            if(txtCodUsuario.getText().length() == 1){
                event.consume();
                txtCodUsuario.setText(txtCodUsuario.getText().toUpperCase());
                txtCodUsuario.end();
            }
        });
        
        txtCodUsuario.setOnKeyTyped((KeyEvent event) -> {
            // Debe tener máximo 9 caracteres
            if (txtCodUsuario.getText().length() < 9) {
                int a = event.getCharacter().charAt(0);
                
                // Este campo solo admite letras y números
                if (!(Character.isLetter(a) || Character.isDigit(a))) {
                    Toolkit.getDefaultToolkit().beep();
                    event.consume();
                }
                
                // Desactiva el botón Iniciar Sesión si hay campos vacíos:
                btnIniciarSesion.setDisable(txtCodUsuario.getText().isEmpty() ||  txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty());
            } else {
                Toolkit.getDefaultToolkit().beep();
                event.consume();
            }
        });
        
        txtUsername.setOnKeyTyped((KeyEvent event) -> {
            // Debe tener máximo 20 caracteres
            if (txtUsername.getText().length() < 20) {
                int a = event.getCharacter().charAt(0);
                
                // Este campo no admite espacios o caracteres especiales distintos de '_'
                if (!(Character.isLetter(a) || Character.isDigit(a)) && (a != '_')) {
                    Toolkit.getDefaultToolkit().beep();
                    event.consume();
                }
                
                // Desactiva el botón Iniciar Sesión si hay campos vacíos:
                btnIniciarSesion.setDisable(txtCodUsuario.getText().isEmpty() ||  txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty());
            } else {
                Toolkit.getDefaultToolkit().beep();
                event.consume();
            }
        });
        
        txtPassword.setOnKeyTyped((KeyEvent event) -> {
            // Debe tener máximo 10 caracteres
            if (txtPassword.getText().length() < 10) {
                int a = event.getCharacter().charAt(0);
                
                // Este campo solo admite letras y números
                if (!(Character.isLetter(a) || Character.isDigit(a))) {
                    Toolkit.getDefaultToolkit().beep();
                    event.consume();
                }
                
                // Desactiva el botón Iniciar Sesión si hay campos vacíos:
                btnIniciarSesion.setDisable(txtCodUsuario.getText().isEmpty() ||  txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty());
            } else {
                Toolkit.getDefaultToolkit().beep();
                event.consume();
            }
        });
        
        camposVacios();
    }
    
    private void camposVacios(){
        RequiredFieldValidator campoVacio_uno = new RequiredFieldValidator();
        campoVacio_uno.setStyle("-fx-fill: red;");
        campoVacio_uno.setMessage("* Campo obligatorio");
        txtCodUsuario.getValidators().add(campoVacio_uno);
        
        txtCodUsuario.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                txtCodUsuario.validate();
                
                if (txtCodUsuario.getText().isEmpty()) {
                    txtCodUsuario.requestFocus();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        
        RequiredFieldValidator campoVacio_dos = new RequiredFieldValidator();
        campoVacio_dos.setStyle("-fx-fill: red;");
        campoVacio_dos.setMessage("* Campo obligatorio");
        txtUsername.getValidators().add(campoVacio_dos);
        
        txtUsername.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                txtUsername.validate();
                
                if (txtUsername.getText().isEmpty()) {
                    txtUsername.requestFocus();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        
        RequiredFieldValidator campoVacio_tres = new RequiredFieldValidator();
        campoVacio_tres.setStyle("-fx-fill: red;");
        campoVacio_tres.setMessage("* Campo obligatorio");
        txtPassword.getValidators().add(campoVacio_tres);
        
        txtPassword.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                txtPassword.validate();

                if (txtPassword.getText().isEmpty()) {
                   txtPassword.requestFocus();
                   Toolkit.getDefaultToolkit().beep();
                }
            }
        });
    }
    
    private void ventanaPersonalizada(){
        barraSuperior.setOnMousePressed((MouseEvent event) -> {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            x = stage.getX() - event.getScreenX();
            y = stage.getY() - event.getScreenY();
        });
        
        barraSuperior.setOnMouseDragged((MouseEvent mouseEvent) -> {
            stage.setX(mouseEvent.getScreenX() + x);
            stage.setY(mouseEvent.getScreenY() + y);
        });
        
        lblCerrarVentana.setOnMouseClicked((e) -> {
            if(mensaje.Cerrar()){
                Platform.exit();
            }
        });
        
        lblMinimizarVentana.setOnMouseClicked((e) -> {
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setIconified(true);
        });
    }
    
    private void Enter(JFXTextField primer_control, Node segundo_control) {
        primer_control.setOnAction((ActionEvent e) -> {
            segundo_control.requestFocus();
        });
    };
    
    private final EventHandler<ActionEvent> IniciarSesion = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            // Comprueba si los datos son correctos según el tipo de usuario
            if (Pattern.matches(CODIGO_VIGILANTE, txtCodUsuario.getText())) {
                
                // Comprueba si el Vigilante se encuentra previamente registrado
                if (modelo.buscarVigilante(txtCodUsuario.getText(), txtUsername.getText(), txtPassword.getText())) {
                    mensaje.Bienvenida("usuario " + txtCodUsuario.getText());
                    usuario = txtCodUsuario.getText();
                } else
                    mensaje.Error("Acceso denegado", "Los datos ingresados no le pertenecen a un vigilante registrado. Por favor, verifique.");
                
            } else {
                
                if (Pattern.matches(CODIGO_USUARIO, txtCodUsuario.getText())) {
                    
                    // Comprueba que los datos ingresados por el Usuario coinciden
                    if (modelo.buscarUsuario(txtCodUsuario.getText(), txtUsername.getText(), txtPassword.getText()) != 0) {
                        mensaje.Bienvenida(txtUsername.getText());
                        usuario = txtCodUsuario.getText();
                        
                        if (modelo.buscarUsuario(txtCodUsuario.getText(), txtUsername.getText(), txtPassword.getText()) == 1) {
                            // El usuario es Administrador
                            try {
                                FXMLLoader _Administrador = new FXMLLoader(getClass().getResource("/Vistas/A_modPrincipal.fxml"));

                                Parent _root = (Parent) _Administrador.load();
                                _AdministradorGeneral = _Administrador.<A_modPrincipal_Controlador>getController();

                                Stage siguiente = (Stage) ((Node) event.getSource()).getScene().getWindow();

                                siguiente.setTitle("Usuario: Administrador");
                                siguiente.setScene(new Scene(_root));
                                siguiente.centerOnScreen();
                                siguiente.show();
                                _AdministradorGeneral.modPrincipal();
                                        
                            } catch (IOException e) {
                                System.out.println("Error " + e);
                            }
                            
                        } else {
                            // El usuario es Supervisor
                            System.out.println("Usuario: Supervisor");
                        }
                        
                    } else
                        mensaje.Error("Acceso denegado", "Los datos ingresados no le pertenecen a un usuario registrado. Por favor, verifique.");
                    
                } else
                    mensaje.Error("Código no válido", "Por favor, asegúrese de seguir las instrucciones del Código de Usuario.");
            
            }
        }
    };
    
}
