package Entidades;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Viaje implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Pais pais;
    private Compania compania;
    private Planta planta;
    private Long numero_viaje;
    private String fecha_viaje;
    private Estado_Viaje estado_viaje;
    private Vehiculo vehiculo;
    private Transportista transportista;
    private String tipo_orden_venta;
    private Long numero_orden_venta;
    private Cliente cliente;
    private Cliente_Destino cliente_destino;
    private String tipo_flete_viaje;
    private String fecha_hora;
    private String estado;
    private String fecha_hora_terminado;

    public Viaje(Pais pais, Compania compania, Planta planta, Long numero_viaje, String fecha_viaje, Estado_Viaje estado_viaje, Vehiculo vehiculo, Transportista transportista, String tipo_orden_venta, Long numero_orden_venta, Cliente cliente, Cliente_Destino cliente_destino, String tipo_flete_viaje, String fecha_hora, String estado, String fecha_hora_terminado) {
        this.pais = pais;
        this.compania = compania;
        this.planta = planta;
        this.numero_viaje = numero_viaje;
        this.fecha_viaje = fecha_viaje;
        this.estado_viaje = estado_viaje;
        this.vehiculo = vehiculo;
        this.transportista = transportista;
        this.tipo_orden_venta = tipo_orden_venta;
        this.numero_orden_venta = numero_orden_venta;
        this.cliente = cliente;
        this.cliente_destino = cliente_destino;
        this.tipo_flete_viaje = tipo_flete_viaje;
        this.fecha_hora = fecha_hora;
        this.estado = estado;
        this.fecha_hora_terminado = fecha_hora_terminado;
    }

    public Viaje() {
    }

    @Override
    public String toString() {
        return "Viaje{" + "pais=" + pais + ", compania=" + compania + ", planta=" + planta + ", numero_viaje=" + numero_viaje + ", fecha_viaje=" + fecha_viaje + ", estado_viaje=" + estado_viaje + ", vehiculo=" + vehiculo + ", transportista=" + transportista + ", tipo_orden_venta=" + tipo_orden_venta + ", numero_orden_venta=" + numero_orden_venta + ", cliente=" + cliente + ", cliente_destino=" + cliente_destino + ", tipo_flete_viaje=" + tipo_flete_viaje + ", fecha_hora=" + fecha_hora + ", estado=" + estado + ", fecha_hora_terminado=" + fecha_hora_terminado + '}';
    }
    
}
