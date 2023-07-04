package Controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.controlsfx.control.CheckComboBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JFileChooser;
import Entidades.DatosBitacora;
import Modelo.Modelo;
import Entidades.ExcelExport;
import Otros.Mensajes;
import Otros.Validaciones;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import ConexionMySQL.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

public class A_BitacoraControlador implements Initializable {

    @FXML
    private JFXDatePicker Date_Picker_Inicio;
    @FXML
    private JFXDatePicker Date_Picker_Final;
    @FXML
    private JFXButton Button_Consultar;
    @FXML
    private TableView<DatosBitacora> TableView_Bitacora;
    @FXML
    private CheckBox Check_Box_Codigo;
    @FXML
    private JFXTextField Text_Field_Codigo;
    @FXML
    private CheckComboBox<String> CheckComboBox_Busqueda;
    Validaciones validar = new Validaciones();
    @FXML
    private TableColumn<DatosBitacora, DatosBitacora> Table_Column_id;
    @FXML
    private TableColumn<DatosBitacora, String> Table_Column_usuario;
    @FXML
    private TableColumn<DatosBitacora, String> Table_Column_Actividad;
    @FXML
    private TableColumn<DatosBitacora, String> Table_Column_Descripcion;
    @FXML
    private TableColumn<DatosBitacora, String> Table_Column_Fecha;
    @FXML
    private JFXButton Button_Exportar;
    @FXML
    private JFXButton Button_Exportar_PDF;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Eventos(); // se carga los eventos de los botones etc
        Estado_Inicio(); // se inicialisa propiedades de la vista ( carga combo box y limitaciones) 
        CheckComboBox_Busqueda.getCheckModel().checkAll();
       // Date_Picker_Inicio.setValue(LocalDate.of(2021, Month.MARCH, 4));
       // Date_Picker_Final.setValue(LocalDate.of(2021, Month.MAY, 27));
    }    
    
    private void Eventos(){
        
        
//        try{
//     Button_Exportar_PDF.setOnAction(Exportar_PDF);
//        }catch(Exception e){
//            
//            
//            System.out.println("Error" + e);
//        }
        Button_Consultar.setOnAction(Consultar_busqueda);
//        Button_Exportar.setOnAction(Exportar);
        Check_Box_Codigo.setOnAction(Seleccionar_busqueda);
        Date_Picker_Final.setOnAction(Fecha_Final_Validar);
        Text_Field_Codigo.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
                public void handle(KeyEvent event){
                    char a = event.getCharacter().toUpperCase().charAt(0);
                    if ((a=='V'|| a=='E'|| a=='J'|| a=='G') && Text_Field_Codigo.getText().toCharArray().length<1){

                    }else{
                            if(Text_Field_Codigo.getText().toCharArray().length==0){
                        validar.QuitarNumerosYSignos(event);
                            }
                        if(Text_Field_Codigo.getText().toCharArray().length<=10){
                        validar.QuitarLetrasYTodosLosSignosCodigo(event);
                         }else{
                           event.consume();
                        }
                    }
                }  
            });  
        
        
        
        
        
        Check_Box_Codigo.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
                public void handle(KeyEvent event){
                    if(Check_Box_Codigo.isSelected()==true)Check_Box_Codigo.setDisable(true); 
                        
                    
                }  
            });  
        
        
        
        
        
        
        
        }
    
    private void Estado_Inicio(){   
        Modelo modelo = new Modelo();
        DatosBitacora datos = new DatosBitacora();
        datos = modelo.Tipos_combobox_Bitcora();
        CheckComboBox_Busqueda.getItems().addAll(datos.getAcciones_bitacora_Array());
        inicializarcolumnas ();       
    }
   
    private ObservableList<String> Datos_CheckCombo(){    
            final ObservableList<String> strings = FXCollections.observableArrayList();  
            strings.add("Inicios de seccion");
            strings.add("Resgistro");
            strings.add("Consultas");
            strings.add("Actualizacion");
            strings.add("Eliminacion de datos");
            strings.add("Descargar de reportes");
            strings.add("Respaldo de base de datos");
        return strings;
    }
    
     void Cargar_Tabla(ArrayList<DatosBitacora> Datos_consultados){
        TableView_Bitacora.getItems().clear();
        TableView_Bitacora.getItems().addAll(Datos_consultados);
        if(Datos_consultados.isEmpty()){Mensajes.Mensaje("No se encontro ningun resultado");}
        }
     
    private void inicializarcolumnas () {
        
        Table_Column_usuario.setCellValueFactory(new PropertyValueFactory <>("Usuario"));
        Table_Column_Actividad.setCellValueFactory(new PropertyValueFactory <>("Actividad"));
        Table_Column_Descripcion.setCellValueFactory(new PropertyValueFactory <>("Descripcion"));    
        Table_Column_Fecha.setCellValueFactory(new PropertyValueFactory <>("Fecha_del_registro"));  
        Table_Column_id.setCellValueFactory(new PropertyValueFactory <>("#"));
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
               @Override
               public void updateItem(LocalDate item, boolean empty)
               {
                   super.updateItem(item, empty);
                   try{
                   if(item.isBefore(Date_Picker_Inicio.getValue()) || item.isAfter(LocalDate.now().plusYears(1)))
                   {
                       setStyle("-fx-background-color: #ffc0cb;");
                       Platform.runLater(() -> setDisable(true));
                    }
                   }catch(Exception e){    
                   }
               }
        };
        Date_Picker_Final.setDayCellFactory(dayCellFactory);
        Table_Column_id.setCellFactory(new Callback<TableColumn<DatosBitacora, DatosBitacora>, TableCell<DatosBitacora, DatosBitacora>>() {
                @Override public TableCell<DatosBitacora, DatosBitacora> call(TableColumn<DatosBitacora, DatosBitacora> param) {
                    return new TableCell<DatosBitacora, DatosBitacora>() {
                        @Override protected void updateItem(DatosBitacora item, boolean empty) {
                            super.updateItem(item, empty);
                            if (this.getTableRow() != null) {
                                int index = this.getTableRow().getIndex();
                                if( index < TableView_Bitacora.getItems().size()) {
                                    int rowNum = index + 1;
                                    setText( String.valueOf(rowNum));
                                } else {
                                    setText("");
                                }
                            } else {
                                setText("");
                            }
                        }
                    };
                }
            }); 
 } 
    
    private boolean Validar(){
        boolean Respuesta = false;
        String Mensaje = "Error en los siguientes campos: ";
        ObservableList<Integer> Tipos_de_busqueda = FXCollections.observableList(CheckComboBox_Busqueda.getCheckModel().getCheckedIndices());
        
            if(Date_Picker_Inicio.getValue()==null){ Mensaje += "\nFecha de inicio no seleccionada"; Respuesta = true;}
            if(Date_Picker_Final.getValue()==null){ Mensaje += "\nFecha de final no seleccionada";  Respuesta = true;}
            if(Tipos_de_busqueda.isEmpty()){ Mensaje += "\nNo ha seleccionado ningun filtro de busqueda";  Respuesta = true;}
            if(Check_Box_Codigo.isSelected()){
            if(Text_Field_Codigo.getText().isEmpty()){Mensaje += "\nEl campo del codigo de usuario se encuentra vacio";  Respuesta = true;}}
            if(Respuesta==true){Mensajes.Mensaje(Mensaje);}

        return Respuesta;
    }
    
    EventHandler<ActionEvent> Consultar_busqueda = new EventHandler<ActionEvent>(){       
        public void handle(ActionEvent me) {
            
            ObservableList<Integer> Tipos_de_busqueda;    
            boolean busqueda_por_codigo = false;
            boolean validar= false;
            String Codigo_Busqueda = null;
             
                if(Validar()==false){
                    try{
                        if(Check_Box_Codigo.isSelected()){
                            Codigo_Busqueda = Text_Field_Codigo.getText();
                            busqueda_por_codigo = true;
                            }
                                LocalDate Fecha_Inicio   = Date_Picker_Inicio.getValue();    
                                LocalDate Fecha_Fin   = Date_Picker_Final.getValue();  
                            
                                Tipos_de_busqueda = FXCollections.observableList(CheckComboBox_Busqueda.getCheckModel().getCheckedIndices());
                                Modelo modelo = new Modelo();
                                ArrayList<DatosBitacora> Datos_consultados = modelo.consultar_bitacora(Fecha_Inicio, Fecha_Fin,Codigo_Busqueda, Tipos_de_busqueda,busqueda_por_codigo);
                                Cargar_Tabla(Datos_consultados);
                        }catch(Exception e){Mensajes.Mensaje("Error en la extraccion de datos  2"+ e.getMessage());  }
                    }
                };   
            };
    
    EventHandler<ActionEvent> Fecha_Final_Validar = new EventHandler<ActionEvent>(){       
        public void handle(ActionEvent me) {
                try{
                if (Date_Picker_Final.getValue().compareTo(Date_Picker_Inicio.getValue())<0){
            Mensajes.Mensaje("Seleccione la fecha inicial");
            Date_Picker_Final.setValue(null);
            }  
                }catch(Exception e){
                    Mensajes.Mensaje("Seleccione la fecha inicial");
                }   
            }
    }; 
    
    EventHandler<ActionEvent> Seleccionar_busqueda = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent me) {
                if(Check_Box_Codigo.isSelected()==true) Text_Field_Codigo.setDisable(false); Text_Field_Codigo.clear();  
                if(Check_Box_Codigo.isSelected()==false) Text_Field_Codigo.setDisable(true);
            }
    };
    
    EventHandler<ActionEvent> Exportar = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent me) {

        criarPlanilha(TableView_Bitacora);    
            }
    };
    
    EventHandler<ActionEvent> Exportar_PDF = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent me){
            
                try {
   
                    reporte();
                } catch (SQLException ex) {
                    System.out.println("Error en buton" + ex);
                }
            }  
            };
       void reporte()throws SQLException{
           try{
                try {
                    
                    Map parametro = new HashMap();
                    
                    parametro.put("idAccion_bitacora",4);
                    parametro.put("idAccion_bitacora",3);
                    Conexion conexion = new Conexion();
                    Connection canal = conexion.Conectar();

                    String path= "src\\Reportes\\Reporte_Bitacora.jasper";
                     JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
                    JasperPrint jprint = JasperFillManager.fillReport(reporte,parametro,canal);
                    JasperViewer view = new JasperViewer(jprint,false);
                    view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    view.setVisible(true);
                } catch (JRException ex) {
                    System.out.println("ERROR" + ex);
                    Logger.getLogger(A_BitacoraControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }catch(Exception ex){
                System.out.println("Error"+ ex);
            }
       }
       
      
public void criarPlanilha(TableView<DatosBitacora> TablaDatos) {
    try{
        HSSFWorkbook workbook = new HSSFWorkbook();
	HSSFSheet spreadsheet = workbook.createSheet("sample");
	HSSFRow row = null;
        boolean prueba = true;
        Font font = (Font) workbook.createFont();
     //   font.setBoldweight(Font.);
        CellStyle style = (CellStyle) workbook.createCellStyle();
        style.setFont(font);
        
            for (int i = 1; i < TablaDatos.getItems().size()+1; i++) {
                if(prueba){
                    row = spreadsheet.createRow(0);
                    row.createCell ((short)0).setCellValue("Nombre de usuario");
                    row.createCell((short) 1).setCellValue("Actividad");
                    row.createCell((short) 2).setCellValue("Descripcion");
                    row.createCell((short) 3).setCellValue("Fecha");
                    prueba = false;

                    row.getCell((short) 0).setCellStyle((HSSFCellStyle) style);
                    row.getCell((short) 1).setCellStyle((HSSFCellStyle) style);
                    row.getCell((short) 2).setCellStyle((HSSFCellStyle) style);
                    row.getCell((short) 3).setCellStyle((HSSFCellStyle) style);
                
                            }	
                    row = spreadsheet.createRow(i);
                
           for (int j = 0; j < TablaDatos.getColumns().size()+1; j++) {
               
                try{	
                   row.createCell((short) j).setCellValue( TablaDatos.getColumns().get(j+1).getCellData(i-1).toString());
                    }catch(Exception e){System.out.println("error de lectura "+ e);}
                }
            }
    try {
        String ruta = null;
        JFileChooser ch = new JFileChooser();
        ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int se = ch.showSaveDialog(null);
            if(se == JFileChooser.APPROVE_OPTION){
                ruta = ch.getSelectedFile().getPath();
		FileOutputStream fileOut = new FileOutputStream(""+ruta+"\\DatosEnExcel.xls");
                    try {
			workbook.write(fileOut);
			fileOut.close();
			
                        }catch (IOException e) {e.printStackTrace();}
                    }
                }catch (FileNotFoundException e) {e.printStackTrace();
            }
        }catch(Exception e){System.out.println("Error" + e);
    }
}

}

