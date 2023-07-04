package Otros;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

public class Alerta {
    public void Bienvenida(String usuario) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("¡Ha ingresado con éxito!");
        a.setHeaderText(null);
        a.setGraphic(new ImageView("/Imagenes/welcome.png"));
        a.setContentText("Bienvenido/a, " + usuario + '.');
        
        ButtonType _Continuar = new ButtonType("Continuar", ButtonBar.ButtonData.OK_DONE);
        a.getButtonTypes().clear();
        a.getButtonTypes().setAll(_Continuar);
        a.showAndWait();
    }
    
    public void Error(String titulo, String contenido) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(contenido);
        
        ButtonType _Entendido = new ButtonType("Entendido", ButtonBar.ButtonData.OK_DONE);
        a.getButtonTypes().clear();
        a.getButtonTypes().setAll(_Entendido);
        a.showAndWait();
    }
    
    public void errorDatosRepetidos(String dato, String contenido) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Fallo al registrar datos");
        a.setHeaderText("¡ERROR! " + dato + " repetido");
        a.setContentText(contenido);
        
        ButtonType _Entendido = new ButtonType("Entendido", ButtonBar.ButtonData.OK_DONE);
        a.getButtonTypes().clear();
        a.getButtonTypes().setAll(_Entendido);
        a.showAndWait();
    }
    
    public void sinResultados(String tipo) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Sin resultados");
        a.setHeaderText(null);
        a.setContentText("No se ha encontrado " + tipo + " que coincida con la búsqueda.");
        
        ButtonType _Continuar = new ButtonType("Continuar", ButtonBar.ButtonData.OK_DONE);
        a.getButtonTypes().clear();
        a.getButtonTypes().setAll(_Continuar);
        a.showAndWait();
    }
    
    public void registroExitoso(String tipo) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Registro exitoso");
        a.setHeaderText(null);
        a.setContentText(tipo + " se ha registrado con éxito.");
        
        ButtonType _Continuar = new ButtonType("Continuar", ButtonBar.ButtonData.OK_DONE);
        a.getButtonTypes().clear();
        a.getButtonTypes().setAll(_Continuar);
        a.showAndWait();
    }
    
    public void actualizacionExitosa(String tipo) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Datos modificados");
        a.setHeaderText(null);
        a.setContentText("Los datos del " + tipo + " han sido modificados con éxito.");
        
        ButtonType _Continuar = new ButtonType("Continuar", ButtonBar.ButtonData.OK_DONE);
        a.getButtonTypes().clear();
        a.getButtonTypes().setAll(_Continuar);
        a.showAndWait();
    }
    
    public void Informacion(String tipo) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Información importante");
        a.setHeaderText("¡ATENCIÓN!");
        a.setContentText("Si desea eliminar los datos de un " + tipo + ", solo debe seleccionar dentro de la tabla la fila a la que pertenezca.");
        
        ButtonType _Continuar = new ButtonType("Continuar", ButtonBar.ButtonData.OK_DONE);
        a.getButtonTypes().clear();
        a.getButtonTypes().setAll(_Continuar);
        a.showAndWait();
    }
    
    public boolean eliminarDatos(String tipo, String dato) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Eliminar " + tipo);
        a.setHeaderText(null);
        a.setContentText("¿Está seguro de que desea eliminar al " + tipo + " (" + dato + ") de la Base de Datos?");
        
        ButtonType _Eliminar = new ButtonType("Eliminar", ButtonBar.ButtonData.OK_DONE);
        ButtonType _Cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        a.getButtonTypes().clear();
        a.getButtonTypes().setAll(_Eliminar, _Cancelar);
        
        a.showAndWait();
        return (a.getResult().getButtonData().toString().equals("OK_DONE"));
    }
    
    public boolean cerrarSesion() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Confirmar cierre");
        a.setHeaderText(null);
        a.setContentText("¿Está seguro de que desea cerrar sesión?");
        
        ButtonType _CerrarSesion = new ButtonType("Cerrar Sesión", ButtonBar.ButtonData.OK_DONE);
        ButtonType _Cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        a.getButtonTypes().clear();
        a.getButtonTypes().setAll(_CerrarSesion, _Cancelar);
        
        a.showAndWait();
        return (a.getResult().getButtonData().toString().equals("OK_DONE"));
    }
    
    public boolean Cerrar() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Confirmar cierre");
        a.setHeaderText(null);
        a.setContentText("¿Está seguro de que desea salir del programa?");
        
        ButtonType _CerrarPrograma = new ButtonType("Cerrar programa", ButtonBar.ButtonData.OK_DONE);
        ButtonType _Cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        a.getButtonTypes().clear();
        a.getButtonTypes().setAll(_CerrarPrograma, _Cancelar);
        
        a.showAndWait();
        return (a.getResult().getButtonData().toString().equals("OK_DONE"));
    }
}
