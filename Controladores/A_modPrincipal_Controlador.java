package Controladores;

import Otros.Alerta;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class A_modPrincipal_Controlador implements Initializable {

    private Stage stage;
    private double x, y;
    
    @FXML private Label barraSuperior, lblMinimizarVentana, lblCerrarVentana;
    
    @FXML private HBox btnInicio;
    @FXML private HBox btnClientes, btnVigilantes, btnRolesDeGuardia, btnContratos, btnTransacciones;
    @FXML private HBox btnRotacionDelPersonal, btnControlDeAusencias, btnSalario_Beneficios;
    @FXML
    private HBox btnUsuarios;
    @FXML private Label btnCerrarSesion;
   
    @FXML private AnchorPane AnchorPanePrincipal;
    @FXML
    private HBox btnBitacora;
    @FXML
    private HBox btnMantenimiento;
    @FXML
    private HBox btnReporte;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void modPrincipal() {
        ventanaPersonalizada();
        
        Menu();
        mostrarCliente_RD();
        
    }
    //mostrarTransaccion_R
    private void Menu() {
        btnClientes.setOnMouseClicked((e) -> {
            mostrarCliente_RD();
        });
        
        btnVigilantes.setOnMouseClicked((e) -> {
            mostrarVigilante_R();
        });
        
        btnRolesDeGuardia.setOnMouseClicked((e) -> {
            mostrarRdG_CU();
        });
        
        btnContratos.setOnMouseClicked((e) -> {
            mostrarContrato_RD();
        });
        
        btnRotacionDelPersonal.setOnMouseClicked((e) -> {
            mostrarRotacionDelPersonal();
        });
        
        btnControlDeAusencias.setOnMouseClicked((e) -> {
            mostrarControlAusencias();
        });
        
        btnSalario_Beneficios.setOnMouseClicked((e) -> {
            mostrarSalarioBeneficios();
        });
        
        btnUsuarios.setOnMouseClicked((e) -> {
            mostrarUsuario_RDU();
        });
        
        btnTransacciones.setOnMouseClicked((e) -> {
            mostrarTransaccion_R();
        });
        btnBitacora.setOnMouseClicked((e) -> {
            mostrarBitacora();
        });
        btnMantenimiento.setOnMouseClicked((e) -> {
            mostrarMantenimiento();
        });
        btnReporte.setOnMouseClicked((e) -> {
            mostrarReportes();
        });
        btnCerrarSesion.setOnMouseClicked((e) -> {
            Alerta mensaje = new Alerta();
            
            if (mensaje.cerrarSesion()) {
                FXMLLoader _IniciarSesion = new FXMLLoader(getClass().getResource("/Vistas/IniciarSesion.fxml"));

                try {
                    Parent _root = (Parent) _IniciarSesion.load();
                    IniciarSesion_Controlador _eventIniciarSesion = _IniciarSesion.<IniciarSesion_Controlador>getController();

                    Scene sc = new Scene(_root);

                    stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.setTitle("Iniciar Sesión");
                    stage.setScene(sc);
                    stage.centerOnScreen();
                    stage.show();
                    _eventIniciarSesion.abrirIniciarSesion();

                } catch (IOException ex) {
                    Logger.getLogger(IniciarSesion_Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void mostrarCliente_CU() {
        try {
            AnchorPane _Cliente_CU = FXMLLoader.load(getClass().getResource("/Vistas/A_Cliente_CU.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_Cliente_CU);
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarVigilante_CU() {
        try {
            AnchorPane _Vigilante_CU = FXMLLoader.load(getClass().getResource("/Vistas/A_Vigilante_CU.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_Vigilante_CU);
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarRdG_CU() {
        try {
            AnchorPane _RdG_CU = FXMLLoader.load(getClass().getResource("/Vistas/A_RdG_CU.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_RdG_CU);
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarContrato_CU() {
        try {
            AnchorPane _Contrato_CU = FXMLLoader.load(getClass().getResource("/Vistas/A_Contrato_CU.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_Contrato_CU);
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarContrato_RD() {
        try {
            AnchorPane _Contrato_RD = FXMLLoader.load(getClass().getResource("/Vistas/A_Contrato_RD.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_Contrato_RD);
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarRotacionDelPersonal() {
        try {
            AnchorPane _RotacionPersonal = FXMLLoader.load(getClass().getResource("/Vistas/A_Rotacion_Personal.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_RotacionPersonal);
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarControlAusencias() {
        try {
            AnchorPane _ControlAusencias = FXMLLoader.load(getClass().getResource("/Vistas/A_Control_de_Ausencias.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_ControlAusencias);
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarSalarioBeneficios() {
        try {
            AnchorPane _SalarioBeneficios = FXMLLoader.load(getClass().getResource("/Vistas/A_Salario_Beneficios.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_SalarioBeneficios);
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarUsuario_C() {
        try {
            AnchorPane _Usuario_C = FXMLLoader.load(getClass().getResource("/Vistas/A_Usuario_C.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_Usuario_C);
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarUsuario_RDU() {
        try {
            AnchorPane _Usuario_RDU = FXMLLoader.load(getClass().getResource("/Vistas/A_Usuario_RDU.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_Usuario_RDU);
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void mostrarTransaccion_R() {
        try {
            AnchorPane _Usuario_RDU = FXMLLoader.load(getClass().getResource("/Vistas/A_Transacciones.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_Usuario_RDU);   
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarVigilante_R() {
        try {
            AnchorPane _Usuario_RDU = FXMLLoader.load(getClass().getResource("/Vistas/A_Directorio_Vigilante.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_Usuario_RDU);   
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarCliente_RD() {
        try {
            AnchorPane _Usuario_RDU = FXMLLoader.load(getClass().getResource("/Vistas/A_Directorio_Clientes.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_Usuario_RDU);   
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void mostrarBitacora() {
        try {
            AnchorPane _Contrato_CU = FXMLLoader.load(getClass().getResource("/Vistas/A_Bitacora.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_Contrato_CU);
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void mostrarMantenimiento() {
        try {
            AnchorPane _Contrato_CU = FXMLLoader.load(getClass().getResource("/Vistas/A_Mantenimiento.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_Contrato_CU);
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarReportes() {
        try {
            AnchorPane _Contrato_CU = FXMLLoader.load(getClass().getResource("/Vistas/A_Reportes.fxml"));
            AnchorPanePrincipal.getChildren().clear();
            AnchorPanePrincipal.getChildren().add(_Contrato_CU);
        } catch (IOException ex) {
            Logger.getLogger(A_modPrincipal_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Alerta a = new Alerta();
            if(a.Cerrar()){
                Platform.exit();
            }
        });
        
        lblMinimizarVentana.setOnMouseClicked((e) -> {
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setIconified(true);
        });
    }
    
}
