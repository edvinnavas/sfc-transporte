package Entidad;

import java.io.Serializable;

public class Viaje implements Serializable {

    private static final long serialVersionUID = 1L;

    private String PAIS;
    private String CODIGO_COMPANIA;
    private String NOMBRE_COMPANIA;
    private Long CODIGO_PLANTA;
    private String NOMBRE_PLANTA;
    private Long NO_VIAJE;
    private String FECHA_VIAJE;
    private String CODIGO_ESTADO_VIAJE;
    private String ESTADO_VIAJE;
    private String VEHICULO;
    private Long CODIGO_TRANSPORTISTA;
    private String NOMBRE_TRANSPORTISTA;
    private String TIPO_ORDEN_VENTA;
    private Long NO_ORDEN_VENTA;
    private Long CODIGO_CLIENTE;
    private String NOMBRE_CLIENTE;
    private Long CODIGO_CLIENTE_DESTINO;
    private String NOMBRE_CLIENTE_DESTINO;

    public Viaje(String PAIS, String CODIGO_COMPANIA, String NOMBRE_COMPANIA, Long CODIGO_PLANTA, String NOMBRE_PLANTA, Long NO_VIAJE, String FECHA_VIAJE, String CODIGO_ESTADO_VIAJE, String ESTADO_VIAJE, String VEHICULO, Long CODIGO_TRANSPORTISTA, String NOMBRE_TRANSPORTISTA, String TIPO_ORDEN_VENTA, Long NO_ORDEN_VENTA, Long CODIGO_CLIENTE, String NOMBRE_CLIENTE, Long CODIGO_CLIENTE_DESTINO, String NOMBRE_CLIENTE_DESTINO) {
        this.PAIS = PAIS;
        this.CODIGO_COMPANIA = CODIGO_COMPANIA;
        this.NOMBRE_COMPANIA = NOMBRE_COMPANIA;
        this.CODIGO_PLANTA = CODIGO_PLANTA;
        this.NOMBRE_PLANTA = NOMBRE_PLANTA;
        this.NO_VIAJE = NO_VIAJE;
        this.FECHA_VIAJE = FECHA_VIAJE;
        this.CODIGO_ESTADO_VIAJE = CODIGO_ESTADO_VIAJE;
        this.ESTADO_VIAJE = ESTADO_VIAJE;
        this.VEHICULO = VEHICULO;
        this.CODIGO_TRANSPORTISTA = CODIGO_TRANSPORTISTA;
        this.NOMBRE_TRANSPORTISTA = NOMBRE_TRANSPORTISTA;
        this.TIPO_ORDEN_VENTA = TIPO_ORDEN_VENTA;
        this.NO_ORDEN_VENTA = NO_ORDEN_VENTA;
        this.CODIGO_CLIENTE = CODIGO_CLIENTE;
        this.NOMBRE_CLIENTE = NOMBRE_CLIENTE;
        this.CODIGO_CLIENTE_DESTINO = CODIGO_CLIENTE_DESTINO;
        this.NOMBRE_CLIENTE_DESTINO = NOMBRE_CLIENTE_DESTINO;
    }

    public Viaje() {
    }

    public String getPAIS() {
        return PAIS;
    }

    public void setPAIS(String PAIS) {
        this.PAIS = PAIS;
    }

    public String getCODIGO_COMPANIA() {
        return CODIGO_COMPANIA;
    }

    public void setCODIGO_COMPANIA(String CODIGO_COMPANIA) {
        this.CODIGO_COMPANIA = CODIGO_COMPANIA;
    }

    public String getNOMBRE_COMPANIA() {
        return NOMBRE_COMPANIA;
    }

    public void setNOMBRE_COMPANIA(String NOMBRE_COMPANIA) {
        this.NOMBRE_COMPANIA = NOMBRE_COMPANIA;
    }

    public Long getCODIGO_PLANTA() {
        return CODIGO_PLANTA;
    }

    public void setCODIGO_PLANTA(Long CODIGO_PLANTA) {
        this.CODIGO_PLANTA = CODIGO_PLANTA;
    }

    public String getNOMBRE_PLANTA() {
        return NOMBRE_PLANTA;
    }

    public void setNOMBRE_PLANTA(String NOMBRE_PLANTA) {
        this.NOMBRE_PLANTA = NOMBRE_PLANTA;
    }

    public Long getNO_VIAJE() {
        return NO_VIAJE;
    }

    public void setNO_VIAJE(Long NO_VIAJE) {
        this.NO_VIAJE = NO_VIAJE;
    }

    public String getFECHA_VIAJE() {
        return FECHA_VIAJE;
    }

    public void setFECHA_VIAJE(String FECHA_VIAJE) {
        this.FECHA_VIAJE = FECHA_VIAJE;
    }

    public String getCODIGO_ESTADO_VIAJE() {
        return CODIGO_ESTADO_VIAJE;
    }

    public void setCODIGO_ESTADO_VIAJE(String CODIGO_ESTADO_VIAJE) {
        this.CODIGO_ESTADO_VIAJE = CODIGO_ESTADO_VIAJE;
    }

    public String getESTADO_VIAJE() {
        return ESTADO_VIAJE;
    }

    public void setESTADO_VIAJE(String ESTADO_VIAJE) {
        this.ESTADO_VIAJE = ESTADO_VIAJE;
    }

    public String getVEHICULO() {
        return VEHICULO;
    }

    public void setVEHICULO(String VEHICULO) {
        this.VEHICULO = VEHICULO;
    }

    public Long getCODIGO_TRANSPORTISTA() {
        return CODIGO_TRANSPORTISTA;
    }

    public void setCODIGO_TRANSPORTISTA(Long CODIGO_TRANSPORTISTA) {
        this.CODIGO_TRANSPORTISTA = CODIGO_TRANSPORTISTA;
    }

    public String getNOMBRE_TRANSPORTISTA() {
        return NOMBRE_TRANSPORTISTA;
    }

    public void setNOMBRE_TRANSPORTISTA(String NOMBRE_TRANSPORTISTA) {
        this.NOMBRE_TRANSPORTISTA = NOMBRE_TRANSPORTISTA;
    }

    public String getTIPO_ORDEN_VENTA() {
        return TIPO_ORDEN_VENTA;
    }

    public void setTIPO_ORDEN_VENTA(String TIPO_ORDEN_VENTA) {
        this.TIPO_ORDEN_VENTA = TIPO_ORDEN_VENTA;
    }

    public Long getNO_ORDEN_VENTA() {
        return NO_ORDEN_VENTA;
    }

    public void setNO_ORDEN_VENTA(Long NO_ORDEN_VENTA) {
        this.NO_ORDEN_VENTA = NO_ORDEN_VENTA;
    }

    public Long getCODIGO_CLIENTE() {
        return CODIGO_CLIENTE;
    }

    public void setCODIGO_CLIENTE(Long CODIGO_CLIENTE) {
        this.CODIGO_CLIENTE = CODIGO_CLIENTE;
    }

    public String getNOMBRE_CLIENTE() {
        return NOMBRE_CLIENTE;
    }

    public void setNOMBRE_CLIENTE(String NOMBRE_CLIENTE) {
        this.NOMBRE_CLIENTE = NOMBRE_CLIENTE;
    }

    public Long getCODIGO_CLIENTE_DESTINO() {
        return CODIGO_CLIENTE_DESTINO;
    }

    public void setCODIGO_CLIENTE_DESTINO(Long CODIGO_CLIENTE_DESTINO) {
        this.CODIGO_CLIENTE_DESTINO = CODIGO_CLIENTE_DESTINO;
    }

    public String getNOMBRE_CLIENTE_DESTINO() {
        return NOMBRE_CLIENTE_DESTINO;
    }

    public void setNOMBRE_CLIENTE_DESTINO(String NOMBRE_CLIENTE_DESTINO) {
        this.NOMBRE_CLIENTE_DESTINO = NOMBRE_CLIENTE_DESTINO;
    }

    @Override
    public String toString() {
        return "Viaje{" + "PAIS=" + PAIS + ", CODIGO_COMPANIA=" + CODIGO_COMPANIA + ", NOMBRE_COMPANIA=" + NOMBRE_COMPANIA + ", CODIGO_PLANTA=" + CODIGO_PLANTA + ", NOMBRE_PLANTA=" + NOMBRE_PLANTA + ", NO_VIAJE=" + NO_VIAJE + ", FECHA_VIAJE=" + FECHA_VIAJE + ", CODIGO_ESTADO_VIAJE=" + CODIGO_ESTADO_VIAJE + ", ESTADO_VIAJE=" + ESTADO_VIAJE + ", VEHICULO=" + VEHICULO + ", CODIGO_TRANSPORTISTA=" + CODIGO_TRANSPORTISTA + ", NOMBRE_TRANSPORTISTA=" + NOMBRE_TRANSPORTISTA + ", TIPO_ORDEN_VENTA=" + TIPO_ORDEN_VENTA + ", NO_ORDEN_VENTA=" + NO_ORDEN_VENTA + ", CODIGO_CLIENTE=" + CODIGO_CLIENTE + ", NOMBRE_CLIENTE=" + NOMBRE_CLIENTE + ", CODIGO_CLIENTE_DESTINO=" + CODIGO_CLIENTE_DESTINO + ", NOMBRE_CLIENTE_DESTINO=" + NOMBRE_CLIENTE_DESTINO + '}';
    }
    
}
