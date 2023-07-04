package Controladores;

import Entidades.Contrato;
import Modelo.Modelo;
import Otros.Alerta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.Toolkit;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class A_Contrato_RD_Controlador implements Initializable {

    private int celda = 0;
    
    private Alerta mensaje = new Alerta();
    private Modelo modelo = new Modelo();
    
    @FXML private JFXTextField txtBuscarContrato;
    @FXML private JFXButton btnBuscar;
    
    @FXML private TableView<Contrato> tablaContrato;
    @FXML private TableColumn<Contrato, Contrato> c_numero;
    @FXML private TableColumn<Contrato, Integer> c_idContrato;
    @FXML private TableColumn<Contrato, String> c_dniCliente;
    @FXML private TableColumn<Contrato, LocalDate> c_fInicio;
    @FXML private TableColumn<Contrato, LocalDate> c_fFinal;
    @FXML private TableColumn<Contrato, String> c_turno;
    @FXML private TableColumn<Contrato, String> c_hInicio;
    @FXML private TableColumn<Contrato, String> c_hFinal;
    
    @FXML private JFXButton btnCUContrato;
    @FXML private JFXButton btnEliminarContrato;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        verContrato_RD();
    }    
    
    private void verContrato_RD() {
        txtBuscarContrato.requestFocus();
        
        tablaColumnas();
        validarBuscar();
        
        Botones();
        
        actualizarDatosMostrados();
    }
    
    private void tablaColumnas() {
        c_numero.setCellFactory((TableColumn<Contrato, Contrato> param) -> new TableCell<Contrato, Contrato>() {
            @Override
            protected void updateItem(Contrato item, boolean empty) {
                super.updateItem(item, empty);
                
                if (this.getTableRow() != null) {
                    int index = this.getTableRow().getIndex();
                    
                    if (index < tablaContrato.getItems().size()) {
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
        
        c_idContrato.setCellValueFactory(new PropertyValueFactory<>("idContrato"));
        c_dniCliente.setCellValueFactory(new PropertyValueFactory<>("dni_cliente"));
        c_fInicio.setCellValueFactory(new PropertyValueFactory<>("fecha_inicio"));
        c_fFinal.setCellValueFactory(new PropertyValueFactory<>("fecha_final"));
        c_turno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        c_hInicio.setCellValueFactory(new PropertyValueFactory<>("hora_inicio"));
        c_hFinal.setCellValueFactory(new PropertyValueFactory<>("hora_final"));
    }
    
    private void Botones() {
        btnBuscar.setOnAction(ConsultarContrato);
        
        btnCUContrato.setOnAction((e) -> {
            IniciarSesion_Controlador._AdministradorGeneral.mostrarContrato_CU();
        });
        
        btnEliminarContrato.setOnAction(EliminarContrato);
    }
    
    private void validarBuscar() {
        txtBuscarContrato.setOnKeyTyped((KeyEvent event) -> {
            // Debe tener máximo 5 caracteres
            if (txtBuscarContrato.getText().length() < 5) {
                int a = event.getCharacter().charAt(0);
                
                // Este campo solo admite números
                if (!Character.isDigit(a)) {
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
        // Muestra todos los contratos almacenados en la BD
        tablaContrato.setItems(modelo.tablaContratos());
        tablaContrato.refresh();
    }
    
    private final EventHandler<ActionEvent> ConsultarContrato = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event){
            if (txtBuscarContrato.getText().length() < 5 || Integer.valueOf(txtBuscarContrato.getText()) < 30000) {
                mensaje.Error("Código no válido", "Por favor, asegúrese de seguir las instrucciones y que el campo no esté vacío.");
            } else {
                // Muestra el Contrato en el caso de que la búsqueda coincida
                if (modelo.consultarContrato(txtBuscarContrato.getText()).isEmpty()) {
                    mensaje.sinResultados("un contrato");
                } else {
                    tablaContrato.setItems(modelo.consultarContrato(txtBuscarContrato.getText()));
                    tablaContrato.refresh();
                }
            }
        }
    };
    
    private final EventHandler<ActionEvent> EliminarContrato = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e){
            mensaje.Informacion("contrato");
            
            tablaContrato.setOnMouseClicked((MouseEvent event) -> {
                if (tablaContrato.getSelectionModel().getSelectedItem() != null) {
                    if (mensaje.eliminarDatos("contrato", Integer.toString(tablaContrato.getSelectionModel().getSelectedItem().getIdContrato()))) {
                        modelo.eliminarContrato(Integer.toString(tablaContrato.getSelectionModel().getSelectedItem().getIdContrato()));
                        
                        actualizarDatosMostrados();
                    }
                }
            });
        }
    };
}
