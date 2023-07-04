
package Entidades;

import java.time.LocalDate;
import java.util.ArrayList;


public class Transacciones {
    
    private String tipodepago, rdg, contrato, dniCliente, nombreCliente, dniVigilante, nombreVigilante,
            monto, estadoPago, metodoPago,referencia;
    
    private LocalDate diaRealizado, inicioPago, diaPagado,fecha__inicio_contrato,fecha_rdg,fecha_final_contrato;
    private ArrayList<String> datos_metodo_transaccion;   
    private ArrayList<String> datos_estado_transaccion;
    private ArrayList<String> datos_tipo_transaccion;
    public Transacciones() {
    }
    public Transacciones(ArrayList<String> datos_metodo_transaccion, ArrayList<String> datos_estado_transaccion, ArrayList<String> datos_tipo_transaccion) {
    this.datos_metodo_transaccion= datos_metodo_transaccion;
    this.datos_estado_transaccion=datos_estado_transaccion;
    this.datos_tipo_transaccion=datos_tipo_transaccion;
    }
    public Transacciones(String tipodepago, String rdg, String contrato, String dniCliente, String nombreCliente, String dniVigilante,
            String nombreVigilante, String monto, String estadoPago, String metodoPago, LocalDate diaRealizado, LocalDate inicioPago, LocalDate diaPagado) {
        this.tipodepago = tipodepago;
        this.rdg = rdg;
        this.contrato = contrato;
        this.dniCliente = dniCliente;
        this.nombreCliente = nombreCliente;
        this.dniVigilante = dniVigilante;
        this.nombreVigilante = nombreVigilante;
        this.monto = monto;
        this.estadoPago = estadoPago;
        this.metodoPago = metodoPago;
        this.diaRealizado = diaRealizado;
        this.inicioPago = inicioPago;
        this.diaPagado = diaPagado;
    }

    public Transacciones(String fecha_rdg, String Codigo_contrato, String fecha__inicio_contrato, String dni_cliente, String nombre_cliente, String dni_vigilante, String nombre_vigilante) {
     this.fecha_rdg=LocalDate.parse(fecha_rdg); 
    this.contrato = Codigo_contrato;
    this.fecha__inicio_contrato= LocalDate.parse(fecha__inicio_contrato);
    this.dniCliente=dni_cliente;
    this.nombreCliente=nombre_cliente;
    this.dniVigilante=dni_vigilante;
    this.nombreVigilante= nombre_vigilante;
                    
    }

    public Transacciones(int tipo_pago, LocalDate dia_realizado, String codigo_rdg, Double monto, int estado_pago, int metodo_pago, LocalDate fecha_del_pago,String referencia,String contrato) {
    this.tipodepago=String.valueOf(tipo_pago);
    this.diaRealizado= dia_realizado;
    this.rdg= codigo_rdg;
    this.monto = String.valueOf(monto);
    this.estadoPago = String.valueOf(estado_pago);
    this.metodoPago= String.valueOf(metodo_pago);
    this.diaPagado = fecha_del_pago;
    this.referencia = referencia;
    this.contrato= contrato;
    }

    public Transacciones(String fecha_inicio, String fecha_final, String dni_cliente, String nombre_cliente) {
        this.fecha__inicio_contrato = LocalDate.parse(fecha_inicio);
        this.fecha_final_contrato = LocalDate.parse(fecha_final);
        this.dniCliente = dni_cliente;
        this.nombreCliente= nombre_cliente;
        }

    public ArrayList<String> getDatos_metodo_transaccion() {
        return datos_metodo_transaccion;
    }

    public ArrayList<String> getDatos_estado_transaccion() {
        return datos_estado_transaccion;
    }

    public ArrayList<String> getDatos_tipo_transaccion() {
        return datos_tipo_transaccion;
    }

    public void setDatos_metodo_transaccion(ArrayList<String> datos_metodo_transaccion) {
        this.datos_metodo_transaccion = datos_metodo_transaccion;
    }

    public void setDatos_estado_transaccion(ArrayList<String> datos_estado_transaccion) {
        this.datos_estado_transaccion = datos_estado_transaccion;
    }

    public void setDatos_tipo_transaccion(ArrayList<String> datos_tipo_transaccion) {
        this.datos_tipo_transaccion = datos_tipo_transaccion;
    }

    

    public void setFecha__inicio_contrato(LocalDate fecha__inicio_contrato) {
        this.fecha__inicio_contrato = fecha__inicio_contrato;
    }

    public LocalDate getFecha__inicio_contrato() {
        return fecha__inicio_contrato;
    }

    public void setFecha_rdg(LocalDate fecha_rdg) {
        this.fecha_rdg = fecha_rdg;
    }

    public LocalDate getFecha_rdg() {
        return fecha_rdg;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getReferencia() {
        return referencia;
    }
            

        
    public String getTipodepago() {
        return tipodepago;
    }

    public void setTipodepago(String tipodepago) {
        this.tipodepago = tipodepago;
    }

    public String getRdg() {
        return rdg;
    }

    public void setRdg(String rdg) {
        this.rdg = rdg;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public void setFecha_final_contrato(LocalDate fecha_final_contrato) {
        this.fecha_final_contrato = fecha_final_contrato;
    }

    public LocalDate getFecha_final_contrato() {
        return fecha_final_contrato;
    }

  


    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDniVigilante() {
        return dniVigilante;
    }

    public void setDniVigilante(String dniVigilante) {
        this.dniVigilante = dniVigilante;
    }

    public String getNombreVigilante() {
        return nombreVigilante;
    }

    public void setNombreVigilante(String nombreVigilante) {
        this.nombreVigilante = nombreVigilante;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDate getDiaRealizado() {
        return diaRealizado;
    }

    public void setDiaRealizado(LocalDate diaRealizado) {
        this.diaRealizado = diaRealizado;
    }

    public LocalDate getInicioPago() {
        return inicioPago;
    }

    public void setInicioPago(LocalDate inicioPago) {
        this.inicioPago = inicioPago;
    }

    public LocalDate getDiaPagado() {
        return diaPagado;
    }

    public void setDiaPagado(LocalDate diaPagado) {
        this.diaPagado = diaPagado;
    }
    
    
    
    
    
    
    
}

