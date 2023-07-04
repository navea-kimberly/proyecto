package Entidades;

public class Beneficio {
    private int id_tipo_beneficio, cantidad, id_Rdg;
    private double monto;
    
    private String tipoBeneficio;
    private double costo;
    
    public Beneficio() {
    }
    
    public Beneficio(int id_tipo_beneficio, int cantidad, double monto, int id_Rdg) {
        this.id_tipo_beneficio = id_tipo_beneficio;
        this.cantidad = cantidad;
        this.monto = monto;
        this.id_Rdg = id_Rdg;
    }

    public int getId_tipo_beneficio() {
        return id_tipo_beneficio;
    }

    public void setId_tipo_beneficio(int id_tipo_beneficio) {
        this.id_tipo_beneficio = id_tipo_beneficio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_Rdg() {
        return id_Rdg;
    }

    public void setId_Rdg(int id_Rdg) {
        this.id_Rdg = id_Rdg;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getTipoBeneficio() {
        return tipoBeneficio;
    }

    public void setTipoBeneficio(String tipoBeneficio) {
        this.tipoBeneficio = tipoBeneficio;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getIdRdg() {
        return idRdg;
    }

    public void setIdRdg(String idRdg) {
        this.idRdg = idRdg;
    }
    
    
    
    String NombreSB, idRdg;
    int CantidadSB, CodigoSB;
    Double MontoSB, CostoSB;

    public Beneficio(String NombreSB, String dniVigilanteSB, int CantidadSB, Double MontoSB, Double CostoSB, int CodigoSB) {
        this.NombreSB = NombreSB;
        this.idRdg = idRdg;
        this.CantidadSB = CantidadSB;
        this.MontoSB = MontoSB;
        this.CostoSB = CostoSB;
        this.CodigoSB = CodigoSB;
    }

    public String getNombreSB() {
        return NombreSB;
    }

    public void setNombreSB(String NombreSB) {
        this.NombreSB = NombreSB;
    }

    public String getidRdg() {
        return idRdg;
    }

    public void setDniidRdg(String idRdg) {
        this.idRdg = idRdg;
    }

    public int getCantidadSB() {
        return CantidadSB;
    }

    public void setCantidadSB(int CantidadSB) {
        this.CantidadSB = CantidadSB;
    }

    public Double getMontoSB() {
        return MontoSB;
    }

    public void setMontoSB(Double MontoSB) {
        this.MontoSB = MontoSB;
    }

    public int getCodigoSB() {
        return CodigoSB;
    }

    public void setCodigoSB(int CodigoSB) {
        this.CodigoSB = CodigoSB;
    }

    public Double getCostoSB() {
        return CostoSB;
    }

    public void setCostoSB(Double CostoSB) {
        this.CostoSB = CostoSB;
    }
    
    
    
}