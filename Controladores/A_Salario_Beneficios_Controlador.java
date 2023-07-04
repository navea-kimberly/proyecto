package Controladores;

import Entidades.Beneficio;
import Entidades.Rol_de_Guardia;
import Modelo.Modelo;
import Otros.Alerta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.Toolkit;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
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

public class A_Salario_Beneficios_Controlador implements Initializable {

    @FXML private JFXTextField txtIdentificadorVigilante;
    @FXML private JFXButton btnBuscarSalarioBeneficios;
    
    @FXML private TableView<Rol_de_Guardia> tblRolDeGuardiaSB;
    @FXML private TableColumn<Rol_de_Guardia, Integer> clmCodigoRG;
    @FXML private TableColumn<Rol_de_Guardia, LocalDate> clmFechaRG;
    @FXML private TableColumn<Rol_de_Guardia, Integer> clmAsistenciaRG;
    @FXML private TableColumn<Rol_de_Guardia, Double> clmRemuneracionRG;
    
    @FXML private TableView<Beneficio> tblBeneficios;
    @FXML private TableColumn<Beneficio, Integer> clmRdgSB;
    @FXML private TableColumn<Beneficio, Integer> clmCodigoSB;
    @FXML private TableColumn<Beneficio, String> clmNombreSB;
    @FXML private TableColumn<Beneficio, Double> clmCostoSB;
    @FXML private TableColumn<Beneficio, Integer> clmCantidadSB;
    @FXML private TableColumn<Beneficio, Double> clmMontoSB;
    
    @FXML private Label lblTotalPagar;
    
    private Alerta mensaje = new Alerta();
    private Modelo modelo = new Modelo();
    
    private final String CODIGO_VIGILANTE = "^([V,E]{1}+[1-9]{1}[0-9]{6,7})$";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarSalarioBeneficios();
    }    
    
    private void mostrarSalarioBeneficios() {
        validarBuscar();
        
        btnBuscarSalarioBeneficios.setOnAction(buscarRdG);
        
        TablaColumnasRolGuardia();
        TablaColumnasBeneficios();
    }
    
    private void validarBuscar() {
        txtIdentificadorVigilante.setOnKeyTyped((event) -> {
            // Debe tener máximo 9 caracteres
            if (txtIdentificadorVigilante.getText().length() < 9) {
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
        
        txtIdentificadorVigilante.setOnKeyReleased((event)-> {
            if(txtIdentificadorVigilante.getText().length() == 1){
                event.consume();
                txtIdentificadorVigilante.setText(txtIdentificadorVigilante.getText().toUpperCase());
                txtIdentificadorVigilante.end();
            }
        });
    }
    
    private void TablaColumnasRolGuardia() {
        clmCodigoRG.setCellValueFactory(new PropertyValueFactory<>("idRdg"));
        clmFechaRG.setCellValueFactory(new PropertyValueFactory("fecha_rdg"));
        clmAsistenciaRG.setCellValueFactory(new PropertyValueFactory("asistencia"));
        clmRemuneracionRG.setCellValueFactory(new PropertyValueFactory("monto_rem"));
    }
    
    private void TablaColumnasBeneficios() {
        clmRdgSB.setCellValueFactory(new PropertyValueFactory("id_Rdg"));
        clmCodigoSB.setCellValueFactory(new PropertyValueFactory("id_tipo_beneficio"));
        clmNombreSB.setCellValueFactory(new PropertyValueFactory("tipoBeneficio"));
        clmCostoSB.setCellValueFactory(new PropertyValueFactory("costo"));
        clmCantidadSB.setCellValueFactory(new PropertyValueFactory("cantidad"));
        clmMontoSB.setCellValueFactory(new PropertyValueFactory("monto"));
    }
    
    private double asignarMontoTotal() {
        double monto_total = 0;
        
        for (Rol_de_Guardia RdG : modelo.tablaMostrarRolEnBeneficios(txtIdentificadorVigilante.getText())) {
            monto_total += RdG.getMonto_rem();
            
            for (Beneficio beneficio : modelo.tablaMostrarBeneficios(txtIdentificadorVigilante.getText())) {
                if (RdG.getIdRdg() == beneficio.getId_Rdg()) {
                    monto_total += beneficio.getMonto();
                }
            }
        }
        
        return monto_total;
    }
    
    private final EventHandler<ActionEvent> buscarRdG = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            if(!Pattern.matches(CODIGO_VIGILANTE, txtIdentificadorVigilante.getText())){
                mensaje.Error("Identificador de Vigilante no válido", "Por favor, asegúrese de seguir las instrucciones y que el campo no esté vacío.");
            }else{
                if(modelo.tablaMostrarRolEnBeneficios(txtIdentificadorVigilante.getText()).isEmpty()){
                    mensaje.sinResultados("un vigilante");
                    
                    tblRolDeGuardiaSB.getItems().clear();
                    tblRolDeGuardiaSB.refresh();
                    
                    tblBeneficios.getItems().clear();
                    tblBeneficios.refresh();
                } else {
                    tblRolDeGuardiaSB.setItems(modelo.tablaMostrarRolEnBeneficios(txtIdentificadorVigilante.getText()));
                    tblBeneficios.setItems(modelo.tablaMostrarBeneficios(txtIdentificadorVigilante.getText()));
                    
                    tblRolDeGuardiaSB.refresh();
                    tblBeneficios.refresh();
                    
                    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
                    simbolo.setDecimalSeparator('.');
                    simbolo.setGroupingSeparator(',');
                    
                    DecimalFormat formato = new DecimalFormat("###,###,###,###,###,###.##", simbolo);
                    lblTotalPagar.setText(formato.format(asignarMontoTotal()));
                }
            }
        }       
    };
    
}
