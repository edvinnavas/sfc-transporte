package Entidad.DETEKTOR;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ubicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    private String placa;
	private String codigo_equipo;
	private String motivo_trasmision;
	private String fecha_ubicacion;
	private String ignicion;
	private String bateria;
	private String voltaje_bateria;
	private String grados_desplazamiento;
	private String latitud;
	private String longitud;
	private String velocidad;
	private String altitud;
	private String distancia;
	private String fecha_grabacion;
	private String id_vehiculo;
    
    @Override
    public String toString() {
        return "Ubicacion [placa=" + placa + ", codigo_equipo=" + codigo_equipo + ", motivo_trasmision="
                + motivo_trasmision + ", fecha_ubicacion=" + fecha_ubicacion + ", ignicion=" + ignicion + ", bateria="
                + bateria + ", voltaje_bateria=" + voltaje_bateria + ", grados_desplazamiento=" + grados_desplazamiento
                + ", latitud=" + latitud + ", longitud=" + longitud + ", velocidad=" + velocidad + ", altitud="
                + altitud + ", distancia=" + distancia + ", fecha_grabacion=" + fecha_grabacion + ", id_vehiculo="
                + id_vehiculo + "]";
    }
    
}
