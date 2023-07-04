package Controladores;

import Entidades.Contrato;
import Modelo.Modelo;
import Otros.Alerta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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

public class A_Contrato_CU_Controlador implements Initializable {

    private final String CEDULA = "^([V,E]{1}+[1-9]{1}[0-9]{6,7})$";
    private final String RIF = "^([J,G]{1}+[0-4]{1}[0-9]{7}+[-]+[0-9]{1})$";
    private final String HORA = "^([0-9]{2}+[:]+[0-9]{2})$";

    private Alerta mensaje = new Alerta();
    private Modelo modelo = new Modelo();
    
    private String buscar = "";
    private float horaI = 0, horaF = 0, minI = 0, minF = 0;
    
    @FXML private JFXTextField txtCodContrato;
    @FXML private JFXButton btnBuscar;
    
    @FXML private JFXTextField txtCodCliente, txtCliente;
    @FXML private JFXDatePicker dpFechaInicio, dpFechaCierre;
    @FXML private JFXComboBox<String> cbTurno;
    @FXML private JFXTextField txtHoraInicio, txtHoraCierre;
    @FXML private JFXCheckBox chbTerminosCondiciones;
    
    @FXML private JFXButton btnAgregarContrato, btnModificarContrato;
    @FXML private ImageView btnVolver;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        verContrato_CU();
    }    
    
    private void verContrato_CU() {
        cbTurno.getItems().setAll("Diurno", "Nocturno", "Ambos");
        txtCodContrato.requestFocus();
        
        Botones();
        
        validarCampos();
        Enter(txtCodContrato, txtCodCliente);
        Enter(txtCodCliente, dpFechaInicio);
        Enter(txtHoraInicio, txtHoraCierre);
    }
    
    private void Botones() {
        btnVolver.setOnMouseClicked((e) -> {
            IniciarSesion_Controlador._AdministradorGeneral.mostrarContrato_RD();
        });
        
        btnAgregarContrato.setOnAction(RegistrarContrato);
        btnBuscar.setOnAction(BuscarContrato);
        btnModificarContrato.setOnAction(ModificarContrato);
    }
    
    private void validarCampos() {
        txtCodContrato.setOnKeyTyped((event) -> {
            // Este campo debe tener máximo 5 caracteres
            if (txtCodContrato.getText().length() < 5) {
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
        
        txtCodCliente.setOnKeyTyped((event) -> {
            // Este campo debe tener máximo 11 caracteres
            if (txtCodCliente.getText().length() < 11) {
                int b = event.getCharacter().charAt(0);

                // Este campo admite números, letras y guiones cortos
                if (!(Character.isLetter(b) || Character.isDigit(b)) && (b != '-')) {
                    Toolkit.getDefaultToolkit().beep();
                    event.consume();
                }
            } else {
                Toolkit.getDefaultToolkit().beep();
                event.consume();
            }
        });
        
        txtCodCliente.setOnKeyReleased((event) -> {
            // Convierte la primera letra en mayúscula
            if(txtCodCliente.getText().length() == 1) {
                event.consume();
                txtCodCliente.setText(txtCodCliente.getText().toUpperCase());
                txtCodCliente.end();
            }
            
            // Busca el nombre del Cliente
            if(modelo.buscarNombreCliente(txtCodCliente.getText()).isEmpty()) {
                txtCliente.setText("No disponible");
            } else {
                txtCliente.setText(modelo.buscarNombreCliente(txtCodCliente.getText()));
            }
        });
        
        // Activa el campo de Fecha de Cierre cuando es seleccionada una Fecha de Inicio
        dpFechaInicio.setOnAction((event) -> {
            dpFechaCierre.setDisable(dpFechaInicio.getValue() == null);
        });
        
        // Valida las fechas ingresadas
        dpFechaInicio.setDayCellFactory(validacionFechaInicio);
        dpFechaCierre.setDayCellFactory(validacionFechaCierre);
        
        txtHoraInicio.setOnKeyTyped((event) -> {
            // Este campo debe tener máximo 5 caracteres
            if (txtHoraInicio.getText().length() < 5) {
                int b = event.getCharacter().charAt(0);
                
                // Este campo solo admite números y el caracter especial ':'
                if (!Character.isDigit(b) && (b != ':')) {
                    Toolkit.getDefaultToolkit().beep();
                    event.consume();
                }
            } else {
                Toolkit.getDefaultToolkit().beep();
                event.consume();
            }
        });
        
        txtHoraCierre.setOnKeyTyped((event) -> {
            // Este campo debe tener máximo 5 caracteres
            if (txtHoraCierre.getText().length() < 5) {
                int b = event.getCharacter().charAt(0);
                
                // Este campo solo admite números y el caracter especial ':'
                if (!Character.isDigit(b) && (b != ':')) {
                    Toolkit.getDefaultToolkit().beep();
                    event.consume();
                }
            } else {
                Toolkit.getDefaultToolkit().beep();
                event.consume();
            }
        });
    }
    
    private boolean expresiones_Regulares() {
        return (!Pattern.matches(HORA, txtHoraInicio.getText())
                || !Pattern.matches(HORA, txtHoraCierre.getText())
                || !(Pattern.matches(CEDULA, txtCodCliente.getText()) || Pattern.matches(RIF, txtCodCliente.getText())));
    }
    
    private boolean validarHoras() {
        String hInicio = "", hFinal = "", mInicio = "", mFinal = "";
        for (int i = 0; i < txtHoraInicio.getText().length(); i++) {
            if (i < 2) {
                hInicio += txtHoraInicio.getText().charAt(i);
            } else {
                if (txtHoraInicio.getText().charAt(i) != ':') {
                    mInicio += txtHoraInicio.getText().charAt(i);
                }
            }
        }
        
        for (int i = 0; i < txtHoraCierre.getText().length(); i++) {
            if (i < 2) {
                hFinal += txtHoraCierre.getText().charAt(i);
            } else {
                if (txtHoraCierre.getText().charAt(i) != ':') {
                    mFinal += txtHoraCierre.getText().charAt(i);
                }
            }
        }
        
        horaI = Float.valueOf(hInicio);
        horaF = Float.valueOf(hFinal);
        minI = Float.valueOf(mInicio);
        minF = Float.valueOf(mFinal);
        
        return (horaF < horaI || ((horaF + (minF / 100)) - (horaI + (minI / 100))) < 1 || horaI >= 24 || horaF >= 24 || minI >= 60 || minF >= 60);
    }
    
    private boolean validarTurno() {
        // Es diurno cuando el turno termina antes de las 7PM o a las 7PM en punto
        if ((horaF < 19 || (horaF == 19 && minF == 0)) && cbTurno.getValue().equals("Diurno")) {
            return true;
        } else {
            // Es nocturno cuando el turno empieza a partir de las 7PM
            if (horaI >= 19 && cbTurno.getValue().equals("Nocturno")) {
                return true;
            } else {
                // Es ambos turnos cuando empieza antes de las 7PM y finaliza después de las 7:00PM
                return horaI < 19 && (horaF > 19 || (horaF == 19 && minF > 0)) && cbTurno.getValue().equals("Ambos");
            }
        }
    }
    
    private boolean validarDatos() {
        if (expresiones_Regulares() || txtCodContrato.getText().length() < 5 || Integer.valueOf(txtCodContrato.getText()) < 30000
                || dpFechaInicio.getValue() == null || dpFechaCierre.getValue() == null || cbTurno.getValue() == null) {
            mensaje.Error("Datos no válidos", "Por favor, asegúrese de seguir las instrucciones y que el campo no esté vacío.");
            return false;
        } else {
            if (dpFechaInicio.getValue().isBefore(LocalDate.now().minusYears(1)) || dpFechaCierre.getValue().isBefore(dpFechaInicio.getValue()) || dpFechaCierre.getValue().isAfter(LocalDate.now().plusYears(1))) {
                mensaje.Error("Se ha encontrado un error", "Compruebe que:\n"
                        + "+ La fecha de inicio sea a partir del día " + LocalDate.now().minusYears(1) + ".\n"
                        + "+ La fecha de cierre sea después de la fecha de inicio, dentro del rango de un (1) año a partir del día " + LocalDate.now() + ".");
                return false;
            } else {
                if (validarHoras()) {
                    mensaje.Error("Se ha encontrado un error", "Compruebe que:\n"
                        + "+ La hora de inicio y la de cierre estén escritas en horario militar.\n"
                        + "+ Tengan mínimo 60 minutos de diferencia entre sí (ej. 12:30 - 13:30).");
                    return false;
                } else {
                    if (!validarTurno()) {
                        mensaje.Error("Se ha encontrado un error", "Las horas ingresadas no corresponden al turno establecido. Por favor, verifique.");
                        return false;
                    } else {
                        if (!chbTerminosCondiciones.isSelected()) {
                            mensaje.Error("Se ha encontrado un error", "Los Términos y Condiciones deben ser aceptados por el Cliente.");
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
    }
    
    private final EventHandler<ActionEvent> RegistrarContrato = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            if (validarDatos()) {
                // Registra el contrato, siempre y cuando el Código de Contrato no se repita
                if (modelo.registrarContrato(Integer.valueOf(txtCodContrato.getText()), txtCodCliente.getText(), dpFechaInicio.getValue(), dpFechaCierre.getValue(), modelo.buscarTurno(cbTurno.getValue()), txtHoraInicio.getText(), txtHoraCierre.getText())) {
                    mensaje.errorDatosRepetidos("Contrato", "El contrato " + txtCodContrato.getText() + " ya se encuentra registrado en la Base de Datos. Verifique.");
                } else {
                    mensaje.registroExitoso("El contrato " + txtCodContrato.getText());
                    
                    limpiarCampos();
                }
            }
        }
    };
    
    private final EventHandler<ActionEvent> BuscarContrato = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            if (txtCodContrato.getText().length() < 5 || Integer.valueOf(txtCodContrato.getText()) < 30000) {
                mensaje.Error("Código no válido", "Por favor, asegúrese de seguir las instrucciones y que el campo no esté vacío.");
            } else {
                // Muestra los datos del Contrato en el caso de que se encuentre registrado
                if (modelo.buscarContrato(txtCodContrato.getText()) != null) {
                    buscar = txtCodContrato.getText();
                    
                    dpFechaCierre.setDisable(false);
                    
                    Contrato datos_consultados = modelo.buscarContrato(buscar);
                    
                    txtCodContrato.setText(String.valueOf(datos_consultados.getIdContrato()));
                    txtCodCliente.setText(datos_consultados.getDni_cliente());
                    
                    if(modelo.buscarNombreCliente(txtCodCliente.getText()).isEmpty()) {
                        txtCliente.setText("No disponible");
                    } else {
                        txtCliente.setText(modelo.buscarNombreCliente(txtCodCliente.getText()));
                    }
                    
                    dpFechaInicio.setValue(datos_consultados.getFecha_inicio());
                    dpFechaCierre.setValue(datos_consultados.getFecha_final());
                    cbTurno.setValue(datos_consultados.getTurno());
                    txtHoraInicio.setText(datos_consultados.getHora_inicio());
                    txtHoraCierre.setText(datos_consultados.getHora_final());
                    chbTerminosCondiciones.setSelected(true);
                    
                    btnModificarContrato.setDisable(false); // Activa el botón de Aplicar cambios
                    btnAgregarContrato.setDisable(true);    // Desactiva el botón de Agregar contrato
                } else {
                    mensaje.Error("Datos no encontrados", "Por favor, asegúrese que el contrato " + txtCodContrato.getText() + " se encuentre registrado.");
                }
            }
        }
    };
    
    private final EventHandler<ActionEvent> ModificarContrato = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            if (validarDatos()) {
                // Actualiza los datos del contrato, siempre y cuando el Código de Contrato no se encuentre previamente registrado
                if (modelo.actualizarContrato(Integer.valueOf(buscar), Integer.valueOf(txtCodContrato.getText()), txtCodCliente.getText(), dpFechaInicio.getValue(), dpFechaCierre.getValue(), modelo.buscarTurno(cbTurno.getValue()), txtHoraInicio.getText(), txtHoraCierre.getText())) {
                    mensaje.errorDatosRepetidos("Contrato", "El contrato " + txtCodContrato.getText() + " ya se encuentra registrado en la Base de Datos. Verifique.");
                } else {
                    mensaje.actualizacionExitosa("contrato");
                    
                    btnAgregarContrato.setDisable(false);    // Activa el botón de Agregar contrato
                    btnModificarContrato.setDisable(true);  // Desactiva el botón de Aplicar cambios
                    limpiarCampos();
                }
            }            
        }
    };
    
    private void limpiarCampos() {
        txtCodContrato.clear();
        txtCodCliente.clear();
        txtCliente.clear();
        dpFechaInicio.getEditor().clear();
        dpFechaCierre.getEditor().clear();
        cbTurno.setValue(null);
        txtHoraInicio.clear();
        txtHoraCierre.clear();
        chbTerminosCondiciones.setSelected(false);
        
        dpFechaCierre.setDisable(true);
    }
    
    final Callback<DatePicker, DateCell> validacionFechaInicio = (final DatePicker datePicker) -> new DateCell() {
        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            
            if (item.isBefore(LocalDate.now().minusYears(1))) {
                setDisable(true);
                setStyle("-fx-background-color: #ffb3d1;");
            }
        }
    };
    
    final Callback<DatePicker, DateCell> validacionFechaCierre = (final DatePicker datePicker) -> new DateCell() {
        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            
            if (item.isBefore(dpFechaInicio.getValue()) || item.isAfter(LocalDate.now().plusYears(1))) {
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
