package Entidad;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
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
    private String disponibilidad;
    private Vehiculo cisterna_disponibilidad;
    private Cabezal cabezal_disponibilidad;
    private Integer numero_ubicaciones_gps;

    @Override
    public String toString() {
        return "Viaje{" + "pais=" + pais + ", compania=" + compania + ", planta=" + planta + ", numero_viaje=" + numero_viaje + ", fecha_viaje=" + fecha_viaje + ", estado_viaje=" + estado_viaje + ", vehiculo=" + vehiculo + ", transportista=" + transportista + ", tipo_orden_venta=" + tipo_orden_venta + ", numero_orden_venta=" + numero_orden_venta + ", cliente=" + cliente + ", cliente_destino=" + cliente_destino + ", tipo_flete_viaje=" + tipo_flete_viaje + ", fecha_hora=" + fecha_hora + ", estado=" + estado + ", fecha_hora_terminado=" + fecha_hora_terminado + ", disponibilidad=" + disponibilidad + ", cisterna_disponibilidad=" + cisterna_disponibilidad + ", cabezal_disponibilidad=" + cabezal_disponibilidad + ", numero_ubicaciones_gps=" + numero_ubicaciones_gps + '}';
    }
    
}
