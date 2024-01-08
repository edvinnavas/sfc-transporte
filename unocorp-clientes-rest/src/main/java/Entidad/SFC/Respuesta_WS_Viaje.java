package Entidad.SFC;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Respuesta_WS_Viaje implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mensaje;
    private Integer numero_viajes;
    private Date fecha_viajes;
    private List<Viaje> lista_viajes;

    @Override
    public String toString() {
        return "Respuesta_WS_Viaje{" + "mensaje=" + mensaje + ", numero_viajes=" + numero_viajes + ", fecha_viajes=" + fecha_viajes + ", lista_viajes=" + lista_viajes + '}';
    }
    
}
