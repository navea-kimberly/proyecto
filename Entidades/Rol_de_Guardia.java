package Entidades;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Rol_de_Guardia {
    private int idRdg, id_contrato, id_remuneracion;
    private String dni_vigilante, hora_inicio, hora_final, hora_llegada, hora_salida;
    private LocalDate fecha_rdg;
    private char asistencia;
    private boolean asistenciaV;
    
    // Remuneración
    private double monto_rem;
    
    // Objetos
    public Beneficio beneficio;
    public Incidencia incidencia;
    
    private ObservableList<Beneficio> beneficios = FXCollections.observableArrayList();
    private ObservableList<Incidencia> incidencias = FXCollections.observableArrayList();
    
    public Rol_de_Guardia() {
    }

    public Rol_de_Guardia(int idRdg, int id_contrato, LocalDate fecha_rdg, String hora_inicio, String hora_final, String dni_vigilante, char asistencia, String hora_llegada, String hora_salida, int monto_rem) {
        
    }
    
    public Rol_de_Guardia(int idRdg, int id_contrato, LocalDate fecha_rdg, String hora_inicio, String hora_final, String dni_vigilante, boolean asistenciaV, String hora_llegada, String hora_salida, int monto_rem) {
        this.idRdg = idRdg;
        this.id_contrato = id_contrato;
        this.fecha_rdg = fecha_rdg;
        this.hora_inicio = hora_inicio;
        this.hora_final = hora_final;
        this.dni_vigilante = dni_vigilante;
        this.asistenciaV = asistenciaV;
        this.hora_llegada = hora_llegada;
        this.hora_salida = hora_salida;
        this.monto_rem = monto_rem;
    }

    public Rol_de_Guardia(int idRdg, int id_contrato, LocalDate fecha_rdg, String hora_inicio, String hora_final, String dni_vigilante, boolean asistenciaV, String hora_llegada, String hora_salida, int monto_rem, Beneficio beneficio, Incidencia incidencia) {
        // Variables de RdG
        this.idRdg = idRdg;
        this.id_contrato = id_contrato;
        this.fecha_rdg = fecha_rdg;
        this.hora_inicio = hora_inicio;
        this.hora_final = hora_final;
        this.dni_vigilante = dni_vigilante;
        this.asistenciaV = asistenciaV;
        this.hora_llegada = hora_llegada;
        this.hora_salida = hora_salida;
        this.monto_rem = monto_rem;
        
        // Objetos
        this.beneficio = beneficio;
        this.incidencia = incidencia;
    }

    public void agregarBeneficio(Beneficio objeto) {
        this.beneficios.add(objeto);
    }
    
    public ObservableList<Beneficio> obtenerBeneficios() {
        return beneficios;
    }
    
    public void agregarIncidencias(Incidencia objeto) {
        this.incidencias.add(objeto);
    }
    
    public ObservableList<Incidencia> obtenerIncidencias() {
        return incidencias;
    }
    
    public void setIdRdg(int idRdg) {
        this.idRdg = idRdg;
    }
    
    public int getIdRdg() {
        return idRdg;
    }

    public void setId_contrato(int id_contrato) {
        this.id_contrato = id_contrato;
    }

    public int getId_contrato() {
        return id_contrato;
    }

    public void setDni_vigilante(String dni_vigilante) {
        this.dni_vigilante = dni_vigilante;
    }

    public String getDni_vigilante() {
        return dni_vigilante;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_final(String hora_final) {
        this.hora_final = hora_final;
    }

    public String getHora_final() {
        return hora_final;
    }

    public void setFecha_rdg(LocalDate fecha_rdg) {
        this.fecha_rdg = fecha_rdg;
    }
    
    public LocalDate getFecha_rdg() {
        return fecha_rdg;
    }

    public void setAsistencia(char asistencia) {
        this.asistencia = asistencia;
    }

    public char getAsistencia() {
        return asistencia;
    }

    public void setAsistenciaV(boolean asistenciaV) {
        this.asistenciaV = asistenciaV;
    }

    public boolean isAsistenciaV() {
        return asistenciaV;
    }

    public int getId_remuneracion() {
        return id_remuneracion;
    }

    public void setId_remuneracion(int id_remuneracion) {
        this.id_remuneracion = id_remuneracion;
    }

    public String getHora_llegada() {
        return hora_llegada;
    }

    public void setHora_llegada(String hora_llegada) {
        this.hora_llegada = hora_llegada;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public double getMonto_rem() {
        return monto_rem;
    }

    public void setMonto_rem(double monto_rem) {
        this.monto_rem = monto_rem;
    }
}
