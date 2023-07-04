package Controladores;

import Entidades.Beneficio;
import Entidades.Contrato;
import Entidades.Incidencia;
import Entidades.Rol_de_Guardia;
import Entidades.Vigilante;
import Modelo.Modelo;
import Otros.Alerta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Toolkit;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class A_RdG_CU_Controlador implements Initializable {

    private final String CEDULA = "^([V,E]{1}+[1-9]{1}[0-9]{6,7})$";
    private final String HORA = "^([0-9]{2}+[:]+[0-9]{2})$";
    private final String DESCRIPCION = "^([A-Za-z0-9.,-/#:]+[ ]?){5,}$";
    
    private Alerta mensaje = new Alerta();
    private Modelo modelo = new Modelo();
    
    private Contrato contrato;
    private Vigilante vigilante;
    
    private String buscar = "";
    private float hInicio = 0, hFinal = 0, mInicio = 0, mFinal = 0;
    private float hInicioCont = 0, hFinalCont = 0, mInicioCont = 0, mFinalCont = 0;
    private float hLlegada = 0, hSalida = 0, mLlegada = 0, mSalida = 0;
    
    private ObservableList<Beneficio> beneficiosRegistrados = FXCollections.observableArrayList();
    private ObservableList<Incidencia> incidenciasRegistradas = FXCollections.observableArrayList();
    
    @FXML private JFXTextField txtCodRdG;
    @FXML private JFXButton btnBuscar;
    
    @FXML private JFXTextField txtCodContrato;
    @FXML private JFXDatePicker dpFechaRdG;
    @FXML private JFXTextField txtHoraInicio, txtHoraCierre;
    @FXML private JFXTextField txtCodVigilante, txtVigilante;
    @FXML private JFXCheckBox chbAsistencia;
    @FXML private JFXTextField txtHoraLlegada, txtHoraSalida;
    @FXML private TextField txtBonoNocturno, txtBonoDomingo;
    @FXML private JFXTextField txtHorasTrabajadas, txtHorasExtra, txtHorasDescanso;
    
    @FXML private JFXComboBox<String> cbTipoIncidencia;
    @FXML private JFXTextArea txtDescripcion;
    @FXML private JFXButton btnAgregarIncidencia;
    
    @FXML private TableView<Incidencia> tablaIncidencia;
    @FXML private TableColumn<Incidencia, String> c_tipoIncidencia;
    @FXML private TableColumn<Incidencia, String> c_descripcion;
    @FXML private TableColumn<Incidencia, String> c_eliminarIncidencia;
    
    @FXML private JFXButton btnAgregarRdG, btnModificarRdG;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        verRdG_CU();
    }
    
    private void verRdG_CU() {
        cbTipoIncidencia.getItems().setAll(modelo.cbTipoIncidencia());
        txtCodRdG.requestFocus();
        
        Botones();
        
        validarCampos();
        Enter(txtCodRdG, txtCodContrato);
        Enter(txtHoraInicio, txtHoraCierre);
        Enter(txtHoraCierre, txtCodVigilante);
        Enter(txtCodVigilante, txtHoraLlegada);
        Enter(txtHoraLlegada, txtHoraSalida);
        Enter(txtHoraSalida, txtHorasDescanso);
        Enter(txtHorasDescanso, txtDescripcion);
    }
    
    private void Botones() {
        btnAgregarRdG.setOnAction(RegistrarRolDeGuardia);
        
        btnAgregarIncidencia.setOnAction(AgregarIncidencia);
        
//        btnVolver.setOnMouseClicked((e) -> {
//            IniciarSesion_Controlador._AdministradorGeneral.mostrarContrato_RD();
//        });
//        
//        btnAgregarContrato.setOnAction(RegistrarContrato);
//        btnBuscar.setOnAction(BuscarContrato);
//        btnModificarContrato.setOnAction(ModificarContrato);
        
        btnBuscar.setOnAction(BuscarRdG);
        
    }
    
    private void validarCampos() {
        txtCodRdG.setOnKeyTyped((event) -> {
            // Este campo debe tener máximo 5 caracteres
            if (txtCodRdG.getText().length() < 5) {
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
        
        txtCodContrato.setOnKeyReleased((event) -> {
            if (txtCodContrato.getText().length() == 5) {
                // Asigna los parámetros establecidos en el Contrato para las validaciones
                if (modelo.buscarContrato(txtCodContrato.getText()) != null) {
                    contrato = modelo.buscarContrato(txtCodContrato.getText());
                } else {
                    mensaje.Error("Datos no encontrados", "Por favor, asegúrese que el contrato " + txtCodContrato.getText() + " se encuentre registrado. Caso contrario, el Rol de Guardia no podrá ser asignado y, en consecuencia, se le negará el registro.");
                    
                    contrato = null;
                    txtCodContrato.clear();
                }
            }
        });
        
        // Valida las fechas ingresadas
        dpFechaRdG.setDayCellFactory(validacionFechaRdG);
        
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
        
        txtCodVigilante.setOnKeyTyped((KeyEvent event) -> {
            // Debe tener máximo 9 caracteres
            if (txtCodVigilante.getText().length() < 9) {
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
        
        txtCodVigilante.setOnKeyReleased((event) -> {
            // Convierte la primera letra en mayúscula
            if(txtCodVigilante.getText().length() == 1) {
                event.consume();
                txtCodVigilante.setText(txtCodVigilante.getText().toUpperCase());
                txtCodVigilante.end();
            }
            
            txtCodVigilante.setOnAction((e) -> {
                if (txtCodVigilante.getText().length() >= 8) {
                    // Asigna un Vigilante al Rol de Guardia
                    if (modelo.buscarVigilante(txtCodVigilante.getText()) != null) {
                        // Verifica que el Vigilante esté disponible
                        if (modelo.buscarVigilante(txtCodVigilante.getText()).isDisponible()) {
                            vigilante = modelo.buscarVigilante(txtCodVigilante.getText());
                        } else {
                            mensaje.Error("No disponible", "Lo sentimos. El vigilante con el Documento de Identidad " + txtCodVigilante.getText() + " no puede ser asignado a este Rol de Guardia, debido a que no está disponible.");
                            txtVigilante.setText("No disponible");
                            
                            vigilante = null;
                            txtCodVigilante.clear();
                        }
                    } else {
                        mensaje.Error("Datos no encontrados", "Por favor, asegúrese que el vigilante con el Documento de Identidad " + txtCodVigilante.getText() + " se encuentre registrado. Caso contrario, el Rol de Guardia no podrá ser asignado y, en consecuencia, se le negará el registro.");
                        
                        vigilante = null;
                        
                        txtVigilante.clear();
                        txtCodVigilante.clear();
                    }
                }

                // Asigna el nombre del Vigilante
                if (vigilante != null) {
                    txtVigilante.setText(vigilante.getNombres() + " " + vigilante.getApellidos());
                }
            });
        });
        
        
        /*** Asistencia del Vigilante ***/
        chbAsistencia.setOnAction((event) -> {
            asignarBonoDomingo();
            asistenciaVigilante();
            
            tablaColumnas();
        });
        
        txtHoraLlegada.setOnKeyTyped((event) -> {
            // Este campo debe tener máximo 5 caracteres
            if (txtHoraLlegada.getText().length() < 5) {
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
        
        txtHoraSalida.setOnKeyTyped((event) -> {
            // Este campo debe tener máximo 5 caracteres
            if (txtHoraSalida.getText().length() < 5) {
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
            
            txtHoraSalida.setOnAction((e) -> {
                asignarHorasYBeneficio();
            });
        });
        
        cbTipoIncidencia.setOnAction((event) -> {
            if (cbTipoIncidencia.getValue() != null) {
                txtDescripcion.setDisable(cbTipoIncidencia.getValue().equals("Sin incidencia"));
            }
        });
        
        txtDescripcion.setOnKeyReleased((event) -> {
            if(txtDescripcion.getText().length() == 1){
                event.consume();
                txtDescripcion.setText(txtDescripcion.getText().toUpperCase());
                txtDescripcion.end();
            }
        });
    }
    
    private void asistenciaVigilante() {
        txtHoraLlegada.setDisable(!chbAsistencia.isSelected());
        txtHoraSalida.setDisable(!chbAsistencia.isSelected());
        txtBonoNocturno.setDisable(!chbAsistencia.isSelected());
        txtBonoDomingo.setDisable(!chbAsistencia.isSelected());
        txtHorasTrabajadas.setDisable(!chbAsistencia.isSelected());
        txtHorasExtra.setDisable(!chbAsistencia.isSelected());
        txtHorasDescanso.setDisable(!chbAsistencia.isSelected());
        cbTipoIncidencia.setDisable(!chbAsistencia.isSelected());
        txtDescripcion.setDisable(true);
        btnAgregarIncidencia.setDisable(!chbAsistencia.isSelected());
    }
    
    private void convertirHorasYMinutos(String campoUno, String campoDos, int numeroConversion) {
        String horaI = "", horaF = "", minI = "", minF = "";

        // Separamos la hora y los minutos
        for (int i = 0; i < campoUno.length(); i++) {
            // Los primeros dos caracteres son las horas
            if (i < 2) {
                horaI += campoUno.charAt(i);
            } else {
                // El resto son los minutos, excepto el caracter especial ':'
                if (campoUno.charAt(i) != ':') {
                    minI += campoUno.charAt(i);
                }
            }
        }

        for (int i = 0; i < campoDos.length(); i++) {
            // Los primeros dos caracteres son las horas
            if (i < 2) {
                horaF += campoDos.charAt(i);
            } else {
                // El resto son los minutos, excepto el caracter especial ':'
                if (campoDos.charAt(i) != ':') {
                    minF += campoDos.charAt(i);
                }
            }
        }
        
        // Convertimos las variables String a Float dependiendo de las horas y minutos a convertir
        switch (numeroConversion) {
            // Para convertir las horas y minutos del RdG
            case 1:
                hInicio = Float.valueOf(horaI);
                hFinal = Float.valueOf(horaF);
                mInicio = Float.valueOf(minI);
                mFinal = Float.valueOf(minF);
                break;
                
            // Para convertir las horas y minutos del Contrato
            case 2:
                hInicioCont = Float.valueOf(horaI);
                hFinalCont = Float.valueOf(horaF);
                mInicioCont = Float.valueOf(minI);
                mFinalCont = Float.valueOf(minF);
                break;
                
            // Para convertir las horas y minutos del RdG del Vigilante
            case 3:
                hLlegada = Float.valueOf(horaI);
                hSalida = Float.valueOf(horaF);
                mLlegada = Float.valueOf(minI);
                mSalida = Float.valueOf(minF);
                break;
        }
    }
    
    private boolean validarHorasRdG() {
        boolean continuarRegistroRdG = false;
        
        if (Pattern.matches(HORA, txtHoraInicio.getText()) || Pattern.matches(HORA, txtHoraCierre.getText())) {
            convertirHorasYMinutos(txtHoraInicio.getText(), txtHoraCierre.getText(), 1);
            
            if (hInicio < hFinal && ((hFinal + (mFinal / 100)) - (hInicio + (mInicio / 100))) >= 1 && hInicio < 24 && hFinal < 24 && mInicio < 60 && mFinal < 60) {
                if (contrato != null) {
                    convertirHorasYMinutos(contrato.getHora_inicio(), contrato.getHora_final(), 2);
                    
                    // Compara que la Hora de Inicio y la Hora de Cierre del Rol de Guardia sea entre la Hora de Inicio y la Hora de Cierre establecidas en el Contrato
                    if (((hInicio > hInicioCont) || (hInicio == hInicioCont && mInicio >= mInicioCont)) && ((hFinal < hFinalCont) || (hFinal == hFinalCont && mFinal <= mFinalCont)) ) {
                        continuarRegistroRdG = true;
                    } else {
                        continuarRegistroRdG = false;
                        
                        mensaje.Error("Se ha encontrado un error", "Por favor, asegúrese que las horas de Inicio y Cierre del Rol de Guardia estén dentro del rango de las horas de Inicio y Cierre establecidas en el Contrato.");
                    }
                }
            } else {
                continuarRegistroRdG = false;
                
                mensaje.Error("Se ha encontrado un error", "Compruebe que:\n"
                        + "+ La hora de inicio y la de cierre estén escritas en horario militar.\n"
                        + "+ Tengan mínimo 60 minutos de diferencia entre sí (ej. 12:30 - 13:30).");         
            }
            
        } else {
            continuarRegistroRdG = false;
            
            mensaje.Error("Datos no válidos", "Por favor, asegúrese de introducir una hora de inicio y de cierre siguiendo las instrucciones.");   
        }
        
        return continuarRegistroRdG;
    }
    
    private boolean validarHorasVigilante() {
        boolean continuarRegistroV;
        
        if (Pattern.matches(HORA, txtHoraLlegada.getText()) && Pattern.matches(HORA, txtHoraSalida.getText())) {
            convertirHorasYMinutos(txtHoraLlegada.getText(), txtHoraSalida.getText(), 3);
            
            // Valida que haya una diferencia de 30 minutos entre la hora de Llegada y la hora de Salida
            if (((hLlegada < hSalida && ((hFinal + (mFinal / 100)) - (hInicio + (mInicio / 100))) >= 0.3) || (hLlegada == hSalida && (mSalida - mLlegada) >= 30)) && hLlegada < 24 && hSalida < 24 && mLlegada < 60 && mSalida < 60) {
                // Valida que la hora de Llegada sea mayor o igual a la hora de Inicio
                if (hLlegada > hInicioCont || (hLlegada == hInicioCont && mLlegada >= mInicioCont)) {
                    continuarRegistroV = true;
                } else {
                    continuarRegistroV = false;
                        
                    mensaje.Error("Se ha encontrado un error", "Por favor, asegúrese que la hora de llegada del Vigilante sea igual o después de la hora de Inicio establecida en el Rol de Guardia.");
                }
                
            } else {
                continuarRegistroV = false;
                
                mensaje.Error("Se ha encontrado un error", "Compruebe que:\n"
                        + "+ La hora de llegada y la de salida estén escritas en horario militar.\n"
                        + "+ Tengan mínimo 30 minutos de diferencia entre sí (ej. 12:10 - 12:40).");    
            }
        } else {
            continuarRegistroV = false;
            
            mensaje.Error("Datos no válidos", "Por favor, asegúrese de introducir una hora de llegada y de salida siguiendo las instrucciones.");
        }
        
        return continuarRegistroV;
    }
    
    private void asignarHorasYBeneficio() {
        if (validarHorasRdG()) {
            if (validarHorasVigilante()) {
                // Asigna las horas trabajadas
                if (hSalida > hLlegada && (hSalida + (mSalida / 100)) - (hLlegada + (mLlegada / 100)) >= 1) {
                    int horas_trabajadas = Math.round((hSalida + (mSalida / 100)) - (hLlegada + (mLlegada / 100)));

                    if (hSalida > hFinal) {
                        horas_trabajadas -= (hSalida - hFinal);
                    }

                    txtHorasTrabajadas.setText(Integer.toString(horas_trabajadas));
                } else {
                    txtHorasTrabajadas.setText("0");
                }

                // Asigna las Horas Extra
                if (hSalida > hFinal && (hSalida + (mSalida / 100)) - (hFinal + (mFinal / 100)) >= 1) {
                    int horas_extra = Math.round((hSalida + (mSalida / 100)) - (hFinal + (mFinal / 100)));

                    txtHorasExtra.setText(Integer.toString(horas_extra));
                } else {
                    txtHorasExtra.setText("0");
                }

                // Asigna las Horas de Descanso
                if ((hSalida + (mSalida / 100)) - (hLlegada + (mLlegada / 100)) >= 8) {
                    txtHorasDescanso.setText("1");
                } else {
                    txtHorasDescanso.setText("0");
                }

                // Comprueba que la hora de Cierre del RdG y que la hora de Salida del Vigilante sean después de las 7:00PM para asignarle el Bono Nocturno al Vigilante
                if (hFinal >= 19 && hSalida >= 19) {
                    txtBonoNocturno.setText("SÍ");
                    txtBonoNocturno.setStyle("-fx-background-color: white; -fx-text-fill: #00cc99; -fx-border-color: #00cc99; -fx-border-width: 0px 0px 0px 5px;");
                } else {
                    txtBonoNocturno.setText("NO");
                    txtBonoNocturno.setStyle("-fx-background-color: white; -fx-text-fill: #ff0000; -fx-border-color: #ff0000; -fx-border-width: 0px 0px 0px 5px;");
                }
            }
        }
    }
    
    private void asignarBonoDomingo() {
        if (dpFechaRdG.getValue() != null) {
            // Comprueba que el día del RdG sea domingo para asignarle el Bono Domingo al Vigilante
            if (dpFechaRdG.getValue().getDayOfWeek().getValue() == 7) {
                txtBonoDomingo.setText("SÍ");
                txtBonoDomingo.setStyle("-fx-background-color: white; -fx-text-fill: #00cc99; -fx-border-color: #00cc99; -fx-border-width: 0px 0px 0px 5px;");
            } else {
                txtBonoDomingo.setText("NO");
                txtBonoDomingo.setStyle("-fx-background-color: white; -fx-text-fill: #ff0000; -fx-border-color: #ff0000; -fx-border-width: 0px 0px 0px 5px;");
            }
        } else {
            mensaje.Error("Se ha encontrado un error", "Por favor, asegúrese de seleccionar una fecha para el Rol de Guardia.");

            chbAsistencia.setSelected(false);
            dpFechaRdG.requestFocus();
        }
    }
    
    private void tablaColumnas(){
        c_tipoIncidencia.setCellValueFactory(new PropertyValueFactory<>("tipoIncidencia"));
        c_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Agrega el botón de Eliminar
        Callback<TableColumn<Incidencia, String>, TableCell<Incidencia, String>> btnEliminar = (TableColumn<Incidencia, String> param) -> {
            // Crea la columna que contiene el botón
            final TableCell<Incidencia, String> espacio_btn = new TableCell<Incidencia, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    // Se crea solo para aquellas filas que contengan información
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        icon.setStyle("-fx-cursor: hand; -glyph-size: 20px; -fx-fill: #ff1744;");
                        
                        icon.setOnMouseClicked((e) -> {
                            if (tablaIncidencia.getSelectionModel().getSelectedItem().getTipoIncidencia().equals("Sin incidencia")) {
                                // Activa el botón de Agregar Incidencia si se elimina "Sin incidencia"
                                btnAgregarIncidencia.setDisable(false);
                            }
                            
                            tablaIncidencia.getItems().remove(tablaIncidencia.getSelectionModel().getSelectedItem());
                        });
                        
                        HBox espacio = new HBox(icon);
                        espacio.setStyle("-fx-alignment: center");
                        HBox.setMargin(icon, new Insets(2, 2, 0, 3));
                        setGraphic(espacio);
                        setText(null);
                    }
                }
            };

            return espacio_btn;
        };
        
        c_eliminarIncidencia.setCellFactory(btnEliminar);
    }
    
    private boolean validarIncidencia() {
        if (cbTipoIncidencia.getValue() != null) {
            if (cbTipoIncidencia.getValue().equals("Sin incidencia")) {
               return true;
            } else {
                if (Pattern.matches(DESCRIPCION, txtDescripcion.getText())) {
                    return true;
                } else {
                    mensaje.Error("Datos no válidos", "Por favor, asegúrese de escribir más de 5 palabras en el campo de Descripción. Recuerde que puede utilizar letras, números y caracteres especiales (.,-/#:).");
                    return false;
                }
            }
        } else {
            mensaje.Error("Datos no válidos", "Por favor, asegúrese de seleccionar un tipo de incidencia.");
            return false;
        }
    }
    
    private final EventHandler<ActionEvent> AgregarIncidencia = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            if (validarIncidencia()) {
                if (cbTipoIncidencia.getValue().equals("Sin incidencia")) {
                    incidenciasRegistradas.clear();
                    txtDescripcion.setText("No hay incidente que reportar.");
                    
                    // Desactiva el botón Agregar Incidencia dado que el Rol de Guardia no tuvo incidencia
                    btnAgregarIncidencia.setDisable(true);
                }
                
                Incidencia _Incidencia = new Incidencia(Integer.valueOf(txtCodRdG.getText()), modelo.buscarIdTipoIncidencia(cbTipoIncidencia.getValue()), cbTipoIncidencia.getValue(), txtDescripcion.getText());
                incidenciasRegistradas.add(_Incidencia);
                tablaIncidencia.setItems(incidenciasRegistradas);

                cbTipoIncidencia.setValue(null);
                txtDescripcion.setText("");
            }
        }
    };
    
    private boolean validarDatos() {
        if (!Pattern.matches(CEDULA, txtCodVigilante.getText()) || txtCodRdG.getText().length() < 5 || Integer.valueOf(txtCodRdG.getText()) < 50000
                || txtCodContrato.getText().length() < 5 || Integer.valueOf(txtCodContrato.getText()) < 30000
                || dpFechaRdG.getValue() == null) {
            mensaje.Error("Datos no válidos", "Por favor, asegúrese de seguir las instrucciones y que el campo no esté vacío.");
            return false;
        } else {
            if (contrato != null && vigilante != null) {
                if (dpFechaRdG.getValue().isBefore(contrato.getFecha_inicio()) || dpFechaRdG.getValue().isAfter(contrato.getFecha_final())) {
                    mensaje.Error("Se ha encontrado un error", "Compruebe que:\n"
                        + "+ La fecha del Rol de Guardia sea a partir del día " + contrato.getFecha_inicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " y antes del día " + contrato.getFecha_final().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".");
                    return false;
                } else {
                    if (!validarHorasRdG()) {
                        return false;
                    } else {
                        if (chbAsistencia.isSelected()) {
                            asignarBonoDomingo();
                            
                            if (!validarHorasVigilante()) {
                                return false;
                            } else {
                                asignarHorasYBeneficio();
                                
                                if (incidenciasRegistradas.isEmpty()) {
                                    mensaje.Error("No hay registro de incidencias", "Recuerde que debe registrar al menos una (1) incidencia.");
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        } else {
                            return true;
                        }
                    }
                }
            } else {
                mensaje.Error("Datos no válidos", "Por favor, asegúrese de seleccionar un Contrato y un Vigilante que se encuentren previamente registrados.");
                return false;
            }
        }
    }
    
    private void agregarBeneficio() {
        Beneficio _Beneficio, _tipoBeneficio;
        
        if (Integer.valueOf(txtHorasExtra.getText()) > 0) {
            _tipoBeneficio  = modelo.buscarTipoBeneficio("Horas extra");
            _Beneficio = new Beneficio(_tipoBeneficio.getId_tipo_beneficio(), Integer.valueOf(txtHorasExtra.getText()), (double) (_tipoBeneficio.getCosto() * Integer.valueOf(txtHorasExtra.getText())), Integer.valueOf(txtCodRdG.getText()));

            beneficiosRegistrados.add(_Beneficio);
        }

        if (Integer.valueOf(txtHorasDescanso.getText()) > 0) {
            _tipoBeneficio = modelo.buscarTipoBeneficio("Horas de descanso");
            _Beneficio = new Beneficio(_tipoBeneficio.getId_tipo_beneficio(), Integer.valueOf(txtHorasDescanso.getText()), (double) (_tipoBeneficio.getCosto() * Integer.valueOf(txtHorasDescanso.getText())), Integer.valueOf(txtCodRdG.getText()));

            beneficiosRegistrados.add(_Beneficio);
        }

        if (txtBonoNocturno.getText().equalsIgnoreCase("SÍ")) {
            _tipoBeneficio = modelo.buscarTipoBeneficio("Bono nocturno");
            _Beneficio = new Beneficio(_tipoBeneficio.getId_tipo_beneficio(), 1, _tipoBeneficio.getCosto(), Integer.valueOf(txtCodRdG.getText()));

            beneficiosRegistrados.add(_Beneficio);
        }

        if (txtBonoDomingo.getText().equalsIgnoreCase("SÍ")) {
            _tipoBeneficio = modelo.buscarTipoBeneficio("Bono domingo");
            _Beneficio = new Beneficio(_tipoBeneficio.getId_tipo_beneficio(), 1, _tipoBeneficio.getCosto(), Integer.valueOf(txtCodRdG.getText()));

            beneficiosRegistrados.add(_Beneficio);
        }
    }
    
    private final EventHandler<ActionEvent> RegistrarRolDeGuardia = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            if (validarDatos()) {
                if (chbAsistencia.isSelected()) {
                    int id_remuneracion;
                    
                    if (vigilante.isJefe_grupo()) {
                        System.out.println("Entré 2");
                        id_remuneracion = 2;
                    } else {
                        id_remuneracion = 1;
                        System.out.println("Entré " + id_remuneracion);
                    }
                    
                    // Registra el Rol de Guardia, siempre y cuando el código no se encuentre previamente registrado
                    if (modelo.registrarRdG(Integer.valueOf(txtCodRdG.getText()), Integer.valueOf(txtCodContrato.getText()), dpFechaRdG.getValue(), txtHoraInicio.getText(), 
                            txtHoraCierre.getText(), txtCodVigilante.getText(), chbAsistencia.isSelected(), txtHoraLlegada.getText(), txtHoraSalida.getText(), id_remuneracion)) {
                        mensaje.errorDatosRepetidos("Rol de Guardia", "El Rol de Guardia " + txtCodRdG.getText() + " ya se encuentra registrado en la Base de Datos. Verifique.");
                    } else {
                        System.out.println("Controlador: " + id_remuneracion);
                        
                        if (modelo.buscarIncidenciaYBeneficios_VigilanteAusente(Integer.valueOf(txtCodRdG.getText()), 0, 0)) {
                            modelo.eliminarIncidenciasYBeneficios_VigilanteAusente(Integer.valueOf(txtCodRdG.getText()), 0, 0);
                        }
                        
                        agregarBeneficio();
                    
                        modelo.registrarBeneficios(beneficiosRegistrados);
                        modelo.registrarIncidencias(incidenciasRegistradas);
                        
                        mensaje.registroExitoso("El Rol de Guardia " + txtCodRdG.getText());

//                        limpiarCampos();
                    }
                } else {
                    // Registra el Rol de Guardia (donde el Vigilante no asistió o el RdG no ha sido llevado a cabo), siempre y cuando el código no se encuentre previamente registrado
                    if (modelo.registrarRdG_VigilanteAusente(Integer.valueOf(txtCodRdG.getText()), Integer.valueOf(txtCodContrato.getText()), dpFechaRdG.getValue(), txtHoraInicio.getText(), 
                            txtHoraCierre.getText(), txtCodVigilante.getText(), chbAsistencia.isSelected(), "NA", "NA", 0)) {
                        mensaje.errorDatosRepetidos("Rol de Guardia", "El Rol de Guardia " + txtCodRdG.getText() + " ya se encuentra registrado en la Base de Datos. Verifique.");
                    } else {
                        mensaje.registroExitoso("El Rol de Guardia " + txtCodRdG.getText());

                        limpiarCampos();
                    }
                }
            }
        }
    };
    
    private final EventHandler<ActionEvent> BuscarRdG = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event){
            if (txtCodRdG.getText().length() < 5 || Integer.valueOf(txtCodRdG.getText()) < 50000) {
                mensaje.Error("Código no válido", "Por favor, asegúrese de seguir las instrucciones y que el campo no esté vacío.");
            } else {
                // Muestra los datos del RdG en el caso de que se encuentre registrado
                if (modelo.buscarRdG(txtCodRdG.getText()) != null) {
                    buscar = txtCodRdG.getText();
                    
                    Rol_de_Guardia datos_consultados = modelo.buscarRdG(buscar);
                    
                    txtCodRdG.setText(String.valueOf(datos_consultados.getIdRdg()));
                    txtCodContrato.setText(String.valueOf(datos_consultados.getId_contrato()));
                    
                    contrato = modelo.buscarContrato(txtCodContrato.getText());
                    
                    dpFechaRdG.setValue(datos_consultados.getFecha_rdg());
                    txtCodVigilante.setText(datos_consultados.getDni_vigilante());
                    
                    vigilante = modelo.buscarVigilante(txtCodVigilante.getText());
                    txtVigilante.setText(vigilante.getNombres() + " " + vigilante.getApellidos());
                    
                    txtHoraInicio.setText(datos_consultados.getHora_inicio());
                    txtHoraCierre.setText(datos_consultados.getHora_final());
                    
                    chbAsistencia.setSelected(datos_consultados.isAsistenciaV());
                    
                    if (datos_consultados.isAsistenciaV()) {
                        asistenciaVigilante();
                        
                        txtHoraLlegada.setText(datos_consultados.getHora_llegada());
                        txtHoraSalida.setText(datos_consultados.getHora_salida());
                        
                        asignarHorasYBeneficio();
                        asignarBonoDomingo();
                        
                        tablaColumnas();
                        incidenciasRegistradas.clear();
                        
                        for (Incidencia incidencia: datos_consultados.obtenerIncidencias()) {
                            Incidencia _Incidencia = new Incidencia(Integer.valueOf(txtCodRdG.getText()), incidencia.getIdTipo_incidencia(), incidencia.getTipoIncidencia(), incidencia.getDescripcion());
                            incidenciasRegistradas.add(_Incidencia);
                            
                            if (_Incidencia.getIdTipo_incidencia() == 1) {
                                btnAgregarIncidencia.setDisable(true);
                            }
                        }
                        
                        tablaIncidencia.setItems(incidenciasRegistradas);
                        tablaIncidencia.refresh();
                    } else {
                        asistenciaVigilante();
                    }
                    
                    btnModificarRdG.setDisable(false); // Activa el botón de Aplicar cambios
                    btnAgregarRdG.setDisable(true);    // Desactiva el botón de Agregar Rol de Guardia
                } else {
                    mensaje.Error("Datos no encontrados", "Por favor, asegúrese que el Rol de Guardia " + txtCodRdG.getText() + " se encuentre registrado.");
                }
            }
        }
    };
    
    private void limpiarCampos() {
        txtCodRdG.clear();
        txtCodContrato.clear();
        dpFechaRdG.getEditor().clear();
        txtHoraInicio.clear();
        txtHoraCierre.clear();
        txtCodVigilante.clear();
        
        chbAsistencia.setSelected(false);
        asistenciaVigilante();
        
        txtHoraLlegada.clear();
        txtHoraSalida.clear();
        
        txtBonoNocturno.setText("TBA");
        txtBonoNocturno.setStyle("-fx-background-color: white; -fx-text-fill: #16a9fa; -fx-border-color: #16a9fa; -fx-border-width: 0px 0px 0px 5px;");
        
        txtBonoDomingo.setText("TBA");
        txtBonoDomingo.setStyle("-fx-background-color: white; -fx-text-fill: #16a9fa; -fx-border-color: #16a9fa; -fx-border-width: 0px 0px 0px 5px;");
        
        txtHorasTrabajadas.clear();
        txtHorasExtra.clear();
        txtHorasDescanso.clear();
        cbTipoIncidencia.setValue(null);
        txtDescripcion.clear();
        
        contrato = null;
        vigilante = null;
        incidenciasRegistradas.clear();
    }
    
    final Callback<DatePicker, DateCell> validacionFechaRdG = (final DatePicker datePicker) -> new DateCell() {
        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            
            if (contrato != null) {
                if (item.isBefore(contrato.getFecha_inicio()) || item.isAfter(contrato.getFecha_final())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffb3d1;");
                }
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
