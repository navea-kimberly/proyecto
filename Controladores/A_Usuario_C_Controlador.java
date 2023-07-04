package Controladores;

import Modelo.Modelo;
import Otros.Alerta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class A_Usuario_C_Controlador implements Initializable {
    
    private final String CODIGO_VIGILANTE = "^([V]{1}+[1-9]{1}[0-9]{6,7})$";
    private final String CODIGO_USUARIO = "^([A,S]{1}+[1-9]{1}[0-9]{5})$";

    private Alerta mensaje = new Alerta();
    private Modelo modelo = new Modelo();
    
    @FXML private Label lblUsuariosR;
    
    @FXML private JFXTextField txtCodUsuario;
    @FXML private JFXTextField txtUsername;
    @FXML private JFXPasswordField txtPassword;
    
    @FXML private JFXButton btnAgregarUsuario;
    @FXML private ImageView btnVolver;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        verUsuario_CU();
    }    
    
    private void verUsuario_CU() {
        txtCodUsuario.requestFocus();
        Botones();
        Validaciones();
        
        conteoUsuarios();
        
        Enter(txtCodUsuario, txtUsername);
        Enter(txtUsername, txtPassword);
    }
    
    private void Botones() {
        btnAgregarUsuario.setOnAction(Verificacion);
        
        btnVolver.setOnMouseClicked((e) -> {
            IniciarSesion_Controlador._AdministradorGeneral.mostrarUsuario_RDU();
        });
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
                
            } else {
                Toolkit.getDefaultToolkit().beep();
                event.consume();
            }
        });
        
        txtUsername.setOnKeyReleased((event) -> {
            if(!txtUsername.getText().isEmpty()){
                event.consume();
                txtUsername.setText(txtUsername.getText().toUpperCase());
                txtUsername.end();
            }
        });
        
        txtUsername.setOnKeyTyped((KeyEvent event) -> {
            // Debe tener máximo 15 caracteres
            if (txtUsername.getText().length() < 15) {
                int a = event.getCharacter().charAt(0);
                
                // Este campo no admite espacios o caracteres especiales distintos de '_'
                if (!(Character.isLetter(a) || Character.isDigit(a)) && (a != '_')) {
                    Toolkit.getDefaultToolkit().beep();
                    event.consume();
                }
                
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
                
            } else {
                Toolkit.getDefaultToolkit().beep();
                event.consume();
            }
        });
    }
    
    private final EventHandler<ActionEvent> Verificacion = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event){
            if (expresiones_Regulares() || (txtUsername.getText().length() < 3) || (txtPassword.getText().length() < 4)) {
                mensaje.Error("Datos no válidos",
                        "Se han encontrado campos con datos no válidos. " +
                        "Por favor, asegúrese de seguir las instrucciones de cada campo y que ninguno de éstos se encuentre vacío.");
            } else {
                // Registra el usuario, siempre y cuando el Código de Usuario no se repita
                if (modelo.registrarUsuario(txtCodUsuario.getText(), txtUsername.getText(), txtPassword.getText(), modelo.buscarTipoUsuario(txtCodUsuario.getText().charAt(0)))) {
                    mensaje.errorDatosRepetidos("Código de Usuario", "El Código de Usuario " + txtCodUsuario.getText() + " ya se encuentra registrado en la Base de Datos. Verifique.");
                } else {
                    mensaje.registroExitoso("El usuario " + txtCodUsuario.getText());
                    
                    conteoUsuarios();
                    limpiarCampos();
                }
            }
            
        }
    };
    
    private boolean expresiones_Regulares(){
        return (!Pattern.matches(CODIGO_USUARIO, txtCodUsuario.getText())
                && !Pattern.matches(CODIGO_VIGILANTE, txtCodUsuario.getText()));
    }
    
    private void conteoUsuarios() {
        // Muestra el total de usuarios registrados
        lblUsuariosR.setText(Integer.toString(modelo.usuariosRegistrados()));
    }
    
    private void limpiarCampos() {
        txtCodUsuario.clear();
        txtUsername.clear();
        txtPassword.clear();
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
