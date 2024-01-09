package Entidad.Pduno;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tracking implements Serializable {

    private static final long serialVersionUID = 1L;

    private Planta planta_origen;
    private Cliente_Destino cliente_destino;
    private String fecha_hora;
    private String tipo_orden;
    private Long numero_orden;
    private Long numero_viaje;
    private Double latitud_actual;
    private Double longitud_actual;
    private String estado_viaje;
    private String tiempo_estimado_llegada;
    private String distancia_estimado_llegada;

    @Override
    public String toString() {
        return "Tracking [planta_origen=" + planta_origen + ", cliente_destino=" + cliente_destino + ", fecha_hora=" + fecha_hora + ", tipo_orden=" + tipo_orden + ", numero_orden=" + numero_orden + ", numero_viaje=" + numero_viaje + ", latitud_actual=" + latitud_actual + ", longitud_actual=" + longitud_actual + ", estado_viaje=" + estado_viaje + ", tiempo_estimado_llegada=" + tiempo_estimado_llegada + ", distancia_estimado_llegada=" + distancia_estimado_llegada + "]";
    }
    
}
