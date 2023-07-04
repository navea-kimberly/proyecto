
package Otros;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class Mensajes extends Exception {
    
        static String estilo = "-fx-background-color: #B2E9F4; " 
           + "-fx-text-fill: #000000; ";
    
        public static void Mensaje(String texto) {
        Alert x = new Alert(Alert.AlertType.ERROR);
       x.getDialogPane().setStyle(estilo);
        x.setTitle("ATENCION");
        x.setContentText(texto);
        x.setGraphic(null);
        x.setHeaderText(null);
        ButtonType _Ok = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);  
        x.getButtonTypes().setAll(_Ok);
        x.showAndWait();        
    }
        public static void Valido (){
        Alert x = new Alert(Alert.AlertType.ERROR);
        x.getDialogPane().setStyle(estilo);
        x.setTitle("Registro valido");
        x.setContentText("Su registro se ha completado exitosamente");
        x.setGraphic(null);
        x.setHeaderText(null);
        ButtonType _Ok = new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);  
        x.getButtonTypes().setAll(_Ok);
        x.showAndWait();    
        }
    
  
    
}
