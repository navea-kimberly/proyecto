package Controladores;

import Entidades.Rol_de_Guardia;
import Modelo.Modelo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class A_Rotacion_Personal_Controlador implements Initializable {
    
    private Modelo modelo = new Modelo();
    
    //private int turno = 0;
    private String turno ="";
    
    @FXML private TableView<Rol_de_Guardia> tblRotacionPersonal;
    @FXML private TableColumn<Rol_de_Guardia, String> clmVigilante;
    @FXML private TableColumn<Rol_de_Guardia, Integer> clmCodigoRol;
    @FXML private TableColumn<Rol_de_Guardia, LocalDate> clmFechaRol;
    @FXML private TableColumn<Rol_de_Guardia, String> clmHoraInicio;
    @FXML private TableColumn<Rol_de_Guardia, String> clmHoraCierre;
    
    @FXML private JFXComboBox<String> cmbFiltrosDeBusqueda;
    @FXML private JFXDatePicker dtpDesde, dtpHasta;
    @FXML private JFXButton btnBuscar;
    
    @FXML private JFXButton btnRegistrarRdG, btnEliminarRdG;
    @FXML private JFXCheckBox chbSinFiltros;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarRotacionPersonal();
    }
    
    private void mostrarRotacionPersonal() {
        cmbFiltrosDeBusqueda.getItems().setAll("Turno diurno", "Turno nocturno", "Ambos turnos");
        
        columnasTabla();
        Botones();
        MostrarSinFiltrosPorDefault();
        
        dtpDesde.setDayCellFactory(validacionFechaDesde);
        dtpHasta.setDayCellFactory(validacionFechaHasta);
    }
    
    public void columnasTabla(){
        clmVigilante.setCellValueFactory(new PropertyValueFactory<>("dni_vigilante"));
        clmCodigoRol.setCellValueFactory(new PropertyValueFactory<>("idRdg"));
        clmFechaRol.setCellValueFactory(new PropertyValueFactory<>("fecha_rdg"));
        clmHoraInicio.setCellValueFactory(new PropertyValueFactory<>("hora_inicio"));
        clmHoraCierre.setCellValueFactory(new PropertyValueFactory<>("hora_final"));
    }
    
    private void Botones(){  
        chbSinFiltros.setSelected(true);
        chbSinFiltros.setOnAction(consultaSinFiltros);
        
        btnRegistrarRdG.setOnAction((e) -> {
            IniciarSesion_Controlador._AdministradorGeneral.mostrarRdG_CU();
        });
        
        btnBuscar.setOnAction(consultaConFiltros);
    }
    
    private void MostrarSinFiltrosPorDefault(){
        tblRotacionPersonal.setItems(modelo.tablaMostrarSinFiltros_RotacionP());
        tblRotacionPersonal.refresh();
        cmbFiltrosDeBusqueda.setDisable(true);
        cmbFiltrosDeBusqueda.setValue(null);
                
        dtpDesde.setDisable(true);
        dtpDesde.getEditor().clear();
                
        dtpHasta.setDisable(true);
        dtpHasta.getEditor().clear();
                
        btnBuscar.setDisable(true);
    }

    private final EventHandler<ActionEvent> consultaSinFiltros = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            // Desactiva o activa los filtros de búsqueda
            cmbFiltrosDeBusqueda.setDisable(chbSinFiltros.isSelected());
            dtpDesde.setDisable(chbSinFiltros.isSelected());
            dtpHasta.setDisable(chbSinFiltros.isSelected());
            btnBuscar.setDisable(chbSinFiltros.isSelected());

            if (chbSinFiltros.isSelected()) {

                // Muestra los Roles de Guardia
                tblRotacionPersonal.setItems(modelo.tablaMostrarSinFiltros_RotacionP());
                tblRotacionPersonal.refresh();

                // Limpia los campos del filtro de búsqueda
                cmbFiltrosDeBusqueda.setValue(null);
                dtpDesde.getEditor().clear();
                dtpHasta.getEditor().clear();
            }
        }
    };
    
    private final EventHandler<ActionEvent> consultaConFiltros = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) { 
            if(cmbFiltrosDeBusqueda.getValue() != null && dtpDesde.getValue() == null && dtpHasta.getValue() == null) {
                switch(cmbFiltrosDeBusqueda.getValue()) {
                    case "Turno Diurno": turno = "diurno"; break;
                    case "Turno Nocturno": turno = "nocturno"; break;
                    case "Ambos Turnos": turno = "ambos"; break;
                }
                
                tblRotacionPersonal.setItems(modelo.tablaMostrarPorTurno(turno));
                tblRotacionPersonal.refresh();
            } else {
                if(cmbFiltrosDeBusqueda.getValue() != null && dtpDesde.getValue() != null && dtpHasta.getValue() != null) {
                    switch(cmbFiltrosDeBusqueda.getValue()){
                    case "Turno diurno": turno = "diurno"; break;
                    case "Turno nocturno": turno = "nocturno"; break;
                    case "Ambos turnos": turno = "ambos"; break;
                }
                
                tblRotacionPersonal.setItems(modelo.tablaMostrarPorTurnoYFechas(turno, dtpDesde.getValue(), dtpHasta.getValue()));
                tblRotacionPersonal.refresh();
                
                System.out.println(modelo.tablaMostrarPorTurnoYFechas(turno, dtpDesde.getValue(), dtpHasta.getValue()).isEmpty());

                } else {
                    System.out.println("No valido");
                }
            }
        }       
    };
    
    // Validación de Fechas
    final Callback<DatePicker, DateCell> validacionFechaDesde = (final DatePicker datePicker) -> new DateCell(){
        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            
            if(item.isBefore(LocalDate.now().minusYears(2))){
                setDisable(true);
                setStyle("-fx-background-color: #ffb3d1;");
            }
        }
    };
    
    final Callback<DatePicker, DateCell> validacionFechaHasta = (final DatePicker datePicker) -> new DateCell(){
        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            
            if(item.isBefore(dtpDesde.getValue()) || (item.isAfter(LocalDate.now().plusYears(1)))){
                setDisable(true);
                setStyle("-fx-background-color: #ffb3d1;");
            }
        }
    }; 
}
