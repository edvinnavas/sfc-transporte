package Entidades;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegTblViajes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_reg_tbl_viajes;
    private String codigo_pais;
    private String nombre_pais;
    private String codigo_compania;
    private String nombre_compania;
    private Long codigo_planta;
    private String nombre_planta;
    private Long numero_viaje;
    private String fecha_viaje;
    private Integer codigo_viaje;
    private String nombre_viaje;
    private String vehiculo;
    private String placa_vehiculo;
    private Long codigo_transportista;
    private String nombre_transportista;
    private String tipo_orden_venta;
    private Long numero_orden_venta;
    private Long codigo_cliente;
    private String nombre_cliente;
    private Long codigo_cliente_destino;
    private String nombre_cliente_destino;
    private String tipo_flete_viaje;
    private String fecha_hora;
    private String estado;
    private String fecha_hora_terminado;
    private String rastreable;
    private String disponibilidad;
    private String cisterna_disponibilidad;
    private String cabezal;
    private String placa_cabezal;
    private String imei_cabezal;
    private Integer numero_ubicaciones_gps;

    public RegTblViajes(Long id_reg_tbl_viajes, String codigo_pais, String nombre_pais, String codigo_compania, String nombre_compania, Long codigo_planta, String nombre_planta, Long numero_viaje, String fecha_viaje, Integer codigo_viaje, String nombre_viaje, String vehiculo, String placa_vehiculo, Long codigo_transportista, String nombre_transportista, String tipo_orden_venta, Long numero_orden_venta, Long codigo_cliente, String nombre_cliente, Long codigo_cliente_destino, String nombre_cliente_destino, String tipo_flete_viaje, String fecha_hora, String estado, String fecha_hora_terminado, String rastreable, String disponibilidad, String cisterna_disponibilidad, String cabezal, String placa_cabezal, String imei_cabezal, Integer numero_ubicaciones_gps) {
        this.id_reg_tbl_viajes = id_reg_tbl_viajes;
        this.codigo_pais = codigo_pais;
        this.nombre_pais = nombre_pais;
        this.codigo_compania = codigo_compania;
        this.nombre_compania = nombre_compania;
        this.codigo_planta = codigo_planta;
        this.nombre_planta = nombre_planta;
        this.numero_viaje = numero_viaje;
        this.fecha_viaje = fecha_viaje;
        this.codigo_viaje = codigo_viaje;
        this.nombre_viaje = nombre_viaje;
        this.vehiculo = vehiculo;
        this.placa_vehiculo = placa_vehiculo;
        this.codigo_transportista = codigo_transportista;
        this.nombre_transportista = nombre_transportista;
        this.tipo_orden_venta = tipo_orden_venta;
        this.numero_orden_venta = numero_orden_venta;
        this.codigo_cliente = codigo_cliente;
        this.nombre_cliente = nombre_cliente;
        this.codigo_cliente_destino = codigo_cliente_destino;
        this.nombre_cliente_destino = nombre_cliente_destino;
        this.tipo_flete_viaje = tipo_flete_viaje;
        this.fecha_hora = fecha_hora;
        this.estado = estado;
        this.fecha_hora_terminado = fecha_hora_terminado;
        this.rastreable = rastreable;
        this.disponibilidad = disponibilidad;
        this.cisterna_disponibilidad = cisterna_disponibilidad;
        this.cabezal = cabezal;
        this.placa_cabezal = placa_cabezal;
        this.imei_cabezal = imei_cabezal;
        this.numero_ubicaciones_gps = numero_ubicaciones_gps;
    }

    public RegTblViajes() {
    }

    @Override
    public String toString() {
        return "RegTblViajes{" + "id_reg_tbl_viajes=" + id_reg_tbl_viajes + ", codigo_pais=" + codigo_pais + ", nombre_pais=" + nombre_pais + ", codigo_compania=" + codigo_compania + ", nombre_compania=" + nombre_compania + ", codigo_planta=" + codigo_planta + ", nombre_planta=" + nombre_planta + ", numero_viaje=" + numero_viaje + ", fecha_viaje=" + fecha_viaje + ", codigo_viaje=" + codigo_viaje + ", nombre_viaje=" + nombre_viaje + ", vehiculo=" + vehiculo + ", placa_vehiculo=" + placa_vehiculo + ", codigo_transportista=" + codigo_transportista + ", nombre_transportista=" + nombre_transportista + ", tipo_orden_venta=" + tipo_orden_venta + ", numero_orden_venta=" + numero_orden_venta + ", codigo_cliente=" + codigo_cliente + ", nombre_cliente=" + nombre_cliente + ", codigo_cliente_destino=" + codigo_cliente_destino + ", nombre_cliente_destino=" + nombre_cliente_destino + ", tipo_flete_viaje=" + tipo_flete_viaje + ", fecha_hora=" + fecha_hora + ", estado=" + estado + ", fecha_hora_terminado=" + fecha_hora_terminado + ", rastreable=" + rastreable + ", disponibilidad=" + disponibilidad + ", cisterna_disponibilidad=" + cisterna_disponibilidad + ", cabezal=" + cabezal + ", placa_cabezal=" + placa_cabezal + ", imei_cabezal=" + imei_cabezal + ", numero_ubicaciones_gps=" + numero_ubicaciones_gps + '}';
    }
    
}
