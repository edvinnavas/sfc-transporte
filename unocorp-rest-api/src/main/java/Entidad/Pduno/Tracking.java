package Entidad.Pduno;

import java.io.Serializable;
import java.util.List;
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
    private Long numero_viaje;
    private String statusTiempo;
    private String fechahora;
    private String tipo_orden;
    private Long pedido;
    private List<Ubicaciones> lst_ubicaciones;
    private String tiempo_estimado_llegada;
    private String distancia_estimado_llegada;
    
    @Override
    public String toString() {
        return "Tracking [planta_origen=" + planta_origen + ", cliente_destino=" + cliente_destino + ", numero_viaje="
                + numero_viaje + ", statusTiempo=" + statusTiempo + ", fechahora=" + fechahora + ", tipo_orden="
                + tipo_orden + ", pedido=" + pedido + ", lst_ubicaciones=" + lst_ubicaciones
                + ", tiempo_estimado_llegada=" + tiempo_estimado_llegada + ", distancia_estimado_llegada="
                + distancia_estimado_llegada + "]";
    }
    
}
