package Controladores;

import Entidades.Rol_de_Guardia;
import Modelo.Modelo;
import Otros.Alerta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class A_Control_de_Ausencias_Controlador implements Initializable {

    @FXML private JFXTextField txtCodigoVigilante;
    @FXML private JFXComboBox<String> cmbMes;
    @FXML private JFXButton btnBuscarAusencias, btnSeleccioneMes;
    
    @FXML private Label lblTrabajosAsignados, lblAusenciasTotal;
    
    @FXML private TableView<Rol_de_Guardia> tblControlAusencias;
    @FXML private TableColumn<Rol_de_Guardia, Rol_de_Guardia> clmNumeroAusencia;
    @FXML private TableColumn<Rol_de_Guardia, Integer> clmIDRolGuardia;
    @FXML private TableColumn<Rol_de_Guardia, Integer> clmIDContrato;
    @FXML private TableColumn<Rol_de_Guardia, LocalDate> clmFechaGuardia;
    @FXML private TableColumn<Rol_de_Guardia, Double> clmRemuneracion;

    private Alerta mensaje = new Alerta();
    private Modelo modelo = new Modelo();
    
    private final String CODIGO_VIGILANTE = "^([V,E]{1}+[1-9]{1}[0-9]{6,7})$";
    
    private int celda = 0;
    private int mes = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarControlDeAusencias();
    }    
    
    private void mostrarControlDeAusencias() {
        cmbMes.getItems().setAll("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
        validarCodVigilante();
        
        TablaColumnas();
        
        btnBuscarAusencias.setOnAction(buscarAusencias);
        btnSeleccioneMes.setOnAction(buscarAusenciasPorMes);
    }
    
    private void validarCodVigilante() {
        txtCodigoVigilante.setOnKeyTyped((event) -> {
            // Debe tener máximo 9 caracteres
            if (txtCodigoVigilante.getText().length() < 9) {
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
        
        txtCodigoVigilante.setOnKeyReleased((event)-> {
            if(txtCodigoVigilante.getText().length() == 1){
                event.consume();
                txtCodigoVigilante.setText(txtCodigoVigilante.getText().toUpperCase());
                txtCodigoVigilante.end();
            }
        });
    }
    
    private void TablaColumnas(){    
        clmIDRolGuardia.setCellValueFactory(new PropertyValueFactory<>("idRdg"));
        clmIDContrato.setCellValueFactory(new PropertyValueFactory<>("id_contrato"));
        clmFechaGuardia.setCellValueFactory(new PropertyValueFactory<>("fecha_rdg"));
        clmRemuneracion.setCellValueFactory(new PropertyValueFactory<>("monto_rem"));

        clmNumeroAusencia.setCellFactory((TableColumn<Rol_de_Guardia, Rol_de_Guardia> param) -> new TableCell<Rol_de_Guardia, Rol_de_Guardia>() {
            @Override
            protected void updateItem(Rol_de_Guardia item, boolean empty) {
                super.updateItem(item, empty);
                
                if (this.getTableRow() != null) {
                    int index = this.getTableRow().getIndex();
                    
                    if (index < tblControlAusencias.getItems().size()) {
                        celda = index + 1;
                        setText(String.valueOf(celda));
                        
                    } else {
                        setText("");
                    }
                } else {
                    setText("");
                }
            }
        });
    }
    
    private void Mensaje(String subtitulo, String contenido) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Sin resultados");
        a.setHeaderText(subtitulo);
        a.setContentText(contenido);

        ButtonType _Continuar = new ButtonType("Continuar", ButtonBar.ButtonData.OK_DONE);
        a.getButtonTypes().clear();
        a.getButtonTypes().setAll(_Continuar);
        a.showAndWait();
    }
    
    private void reiniciarCampos(String trabajosAsignados) {
        lblAusenciasTotal.setText("0");
        lblTrabajosAsignados.setText(trabajosAsignados); 

        tblControlAusencias.getItems().clear();
        tblControlAusencias.refresh();
    }
    
    private final EventHandler<ActionEvent> buscarAusencias = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            if (!Pattern.matches(CODIGO_VIGILANTE, txtCodigoVigilante.getText())) {
                mensaje.Error("Identificador de Vigilante no válido", "Por favor, asegúrese de seguir las instrucciones y que el campo no esté vacío.");
            } else {
                if (modelo.ContadorTrabajosAsignados(txtCodigoVigilante.getText()) == 0) {
                    Mensaje("No se han encontrado trabajos asignados", "No se le han asignado Roles de Guardia al vigilante.");
                    reiniciarCampos("0");
                } else {
                    if(modelo.tablaMostrarAusencias(txtCodigoVigilante.getText()).isEmpty()) {
                        Mensaje("¡ENHORABUENA!", "El vigilante ha estado presente en los Roles de Guardia asignados.");
                        reiniciarCampos(Integer.toString(modelo.ContadorTrabajosAsignados(txtCodigoVigilante.getText())));
                    } else {
                        lblAusenciasTotal.setText(Integer.toString(modelo.ContadorAusenciasIdentificador(txtCodigoVigilante.getText())));
                        lblTrabajosAsignados.setText(Integer.toString(modelo.ContadorTrabajosAsignados(txtCodigoVigilante.getText())));

                        tblControlAusencias.setItems(modelo.tablaMostrarAusencias(txtCodigoVigilante.getText()));
                        tblControlAusencias.refresh();
                    }
                }
            }
        }        
    };

    private final EventHandler<ActionEvent> buscarAusenciasPorMes = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            if (cmbMes.getValue() == null || !Pattern.matches(CODIGO_VIGILANTE, txtCodigoVigilante.getText())) {
                mensaje.Error("Ha ocurrido un error", "Por favor, asegúrese de seleccionar un mes y que el campo de Código de Vigilante no esté vacío.");
            } else {
                switch(cmbMes.getValue()) {
                    case "Enero": mes = 1; break;
                    case "Febrero": mes = 2; break;
                    case "Marzo": mes = 3; break;
                    case "Abril": mes = 4; break;
                    case "Mayo": mes = 5; break;
                    case "Junio": mes = 6; break;
                    case "Julio": mes = 7; break;
                    case "Agosto": mes = 8; break;
                    case "Septiembre": mes = 9; break;
                    case "Octubre": mes = 10; break;
                    case "Noviembre": mes = 11; break;
                    case "Diciembre": mes = 12; break;
                }
                
                if (modelo.ContadorTrabajosAsignadosConMes(txtCodigoVigilante.getText(), mes) == 0) {
                    Mensaje("No se han encontrado trabajos asignados", "No se le han asignado Roles de Guardia al vigilante durante el mes seleccionado.");
                    reiniciarCampos("0");
                } else {
                    if(modelo.tablaMostrarAusenciasMes(txtCodigoVigilante.getText(), mes).isEmpty()) {
                        Mensaje("¡ENHORABUENA!", "El vigilante ha estado presente en los Roles de Guardia asignados durante el mes seleccionado.");
                        reiniciarCampos(Integer.toString(modelo.ContadorTrabajosAsignadosConMes(txtCodigoVigilante.getText(), mes)));
                    } else {
                        lblAusenciasTotal.setText(Integer.toString(modelo.ContadorAusenciasConMes(txtCodigoVigilante.getText(), mes)));
                        lblTrabajosAsignados.setText(Integer.toString(modelo.ContadorTrabajosAsignadosConMes(txtCodigoVigilante.getText(), mes)));

                        tblControlAusencias.setItems(modelo.tablaMostrarAusenciasMes(txtCodigoVigilante.getText(), mes));
                        tblControlAusencias.refresh();
                    }
                }
            }
        }
    };
}
