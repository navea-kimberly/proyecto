package Controladores;

import Entidades.Usuario;
import Modelo.Modelo;
import Otros.Alerta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class A_Usuario_RDU_Controlador implements Initializable {

    private final String CODIGO_VIGILANTE = "^([V]{1}+[1-9]{1}[0-9]{6,7})$";
    private final String CODIGO_USUARIO = "^([A,S]{1}+[1-9]{1}[0-9]{5})$";
    
    private Alerta mensaje = new Alerta();
    private Modelo modelo = new Modelo();
    
    @FXML private Label lblUsuariosR;
    
    @FXML private JFXTextField txtBuscarUsuario;
    @FXML private JFXButton btnBuscar;
    
    @FXML private TableView<Usuario> tablaUsuario;
    @FXML private TableColumn<Usuario, String> c_codUsuario;
    @FXML private TableColumn<Usuario, String> c_nombreUsuario;
    @FXML private TableColumn<Usuario, String> c_tipoUsuario;
    
    @FXML private JFXButton btnAgregarUsuario;
    @FXML private JFXButton btnEliminarUsuario;
    
    @FXML private JFXTextField txtCodUsuario;
    @FXML private JFXButton btnModificarUsuario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        verUsuario_RD();
    }    
    
    private void verUsuario_RD() {
        txtBuscarUsuario.requestFocus();
        
        tablaColumnas();
        validarBuscar();
        validarModificar();
        
        Botones();
        
        actualizarDatosMostrados();
    }
    
    private void tablaColumnas() {
        c_codUsuario.setCellValueFactory(new PropertyValueFactory<>("cod_usuario"));
        c_nombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        c_tipoUsuario.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }
    
    private void Botones() {
        btnBuscar.setOnAction(ConsultarUsuario);
        
        btnAgregarUsuario.setOnAction((e) -> {
            IniciarSesion_Controlador._AdministradorGeneral.mostrarUsuario_C();
        });
        
        btnModificarUsuario.setOnAction(ModificarUsuario);
        btnEliminarUsuario.setOnAction(EliminarUsuario);
    }
    
    private void validarBuscar() {
        txtBuscarUsuario.setOnKeyReleased((event) -> {
            if(txtBuscarUsuario.getText().length() == 1){
                event.consume();
                txtBuscarUsuario.setText(txtBuscarUsuario.getText().toUpperCase());
                txtBuscarUsuario.end();
            }
        });
        
        txtBuscarUsuario.setOnKeyTyped((KeyEvent event) -> {
            // Debe tener máximo 9 caracteres
            if (txtBuscarUsuario.getText().length() < 9) {
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
    
    private void validarModificar() {
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
    }
    
    private void actualizarDatosMostrados() {
        txtBuscarUsuario.clear();
        txtCodUsuario.clear();
        
        // Muestra el total de usuarios registrados
        lblUsuariosR.setText(Integer.toString(modelo.usuariosRegistrados()));
        
        // Muestra todos los usuarios almacenados en la BD
        tablaUsuario.setItems(modelo.tablaUsuarios());
        tablaUsuario.refresh();
    }
    
    private final EventHandler<ActionEvent> ConsultarUsuario = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event){
            if (!Pattern.matches(CODIGO_VIGILANTE, txtBuscarUsuario.getText()) && !Pattern.matches(CODIGO_USUARIO, txtBuscarUsuario.getText())) {
                mensaje.Error("Código no válido", "Por favor, asegúrese de seguir las instrucciones y que el campo no esté vacío.");
            } else {
                // Muestra el Usuario en el caso de que la búsqueda coincida
                if (modelo.consultarUsuario(txtBuscarUsuario.getText()).isEmpty()) {
                    mensaje.sinResultados("un usuario");
                } else {
                    tablaUsuario.setItems(modelo.consultarUsuario(txtBuscarUsuario.getText()));
                    tablaUsuario.refresh();
                    
                    txtCodUsuario.setText(txtBuscarUsuario.getText());
                }
            }
        }
    };
    
    private final EventHandler<ActionEvent> ModificarUsuario = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event){
            if (Pattern.matches(CODIGO_USUARIO, txtCodUsuario.getText()) || Pattern.matches(CODIGO_VIGILANTE, txtCodUsuario.getText())) {
                // Actualiza los datos del usuario, siempre y cuando el Código de Usuario no se encuentre previamente registrado
                if (modelo.actualizarUsuario(txtBuscarUsuario.getText(), txtCodUsuario.getText(), modelo.buscarTipoUsuario(txtCodUsuario.getText().charAt(0)))) {
                    mensaje.errorDatosRepetidos("Código de Usuario", "El Código de Usuario " + txtCodUsuario.getText() + " ya se encuentra registrado en la Base de Datos. Verifique.");
                } else {
                    mensaje.actualizacionExitosa("usuario");
                    
                    actualizarDatosMostrados();
                }
                
            } else
                mensaje.Error("Código no válido", "Por favor, asegúrese de seguir las instrucciones del Código de Usuario.");
            
        }
    };
    
    private final EventHandler<ActionEvent> EliminarUsuario = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e){
            mensaje.Informacion("usuario");
            
            tablaUsuario.setOnMouseClicked((MouseEvent event) -> {
                if (tablaUsuario.getSelectionModel().getSelectedItem() != null) {
                    if (mensaje.eliminarDatos("usuario", tablaUsuario.getSelectionModel().getSelectedItem().getCod_usuario())) {
                        modelo.eliminarUsuario(tablaUsuario.getSelectionModel().getSelectedItem().getCod_usuario());
                        
                        actualizarDatosMostrados();
                    }
                }
            });
        }
    };
}
