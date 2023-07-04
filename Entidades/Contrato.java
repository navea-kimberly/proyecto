package Entidades;

import java.time.LocalDate;

public class Contrato {
    private int idContrato, id_turno;
    private String dni_cliente, turno, hora_inicio, hora_final;
    private LocalDate fecha_inicio, fecha_final;

    public Contrato(){
    }
    
    public Contrato (int idContrato, String dni_cliente, LocalDate fecha_inicio, LocalDate fecha_final, int id_turno, String hora_inicio, String hora_final) {
        this.idContrato = idContrato;
        this.dni_cliente = dni_cliente;
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
        this.id_turno = id_turno;
        this.hora_inicio = hora_inicio;
        this.hora_final = hora_final;
    }
    
    public Contrato (int idContrato, String dni_cliente, LocalDate fecha_inicio, LocalDate fecha_final, String turno, String hora_inicio, String hora_final) {
        this.idContrato = idContrato;
        this.dni_cliente = dni_cliente;
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
        this.turno = turno;
        this.hora_inicio = hora_inicio;
        this.hora_final = hora_final;
    }
    
    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_final(LocalDate fecha_final) {
        this.fecha_final = fecha_final;
    }
    
    public LocalDate getFecha_final() {
        return fecha_final;
    }

    public void setId_turno(int id_turno) {
        this.id_turno = id_turno;
    }

    public int getId_turno() {
        return id_turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getTurno() {
        return turno;
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

}
