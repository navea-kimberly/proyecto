package Main;

import Controladores.IniciarSesion_Controlador;
import Modelo.Modelo;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Proyecto extends Application {
    
    private IniciarSesion_Controlador _eventIniciarSesion;
    private Parent _root;
    private Scene sc;
    Modelo modelo = new Modelo();
    @Override
    public void start(Stage stage) {
        FXMLLoader _verIniciarSesion = new FXMLLoader(getClass().getResource("/Vistas/IniciarSesion.fxml"));

        try {
            
            _root = (Parent) _verIniciarSesion.load();
            _eventIniciarSesion = _verIniciarSesion.<IniciarSesion_Controlador>getController();
            
            sc = new Scene(_root);
              
            stage.getIcons().add(new Image("/Imagenes/miniatura.png"));
            stage.setTitle("Iniciar Sesión");
            stage.setScene(sc);
            stage.centerOnScreen();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            _eventIniciarSesion.abrirIniciarSesion();
            
        } catch (IOException ex) {
            Logger.getLogger(IniciarSesion_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
