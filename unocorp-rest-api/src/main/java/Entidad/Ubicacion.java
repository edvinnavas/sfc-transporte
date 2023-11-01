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
public class Ubicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fecha_hora_ubicacion;
    private String imei;
    private String latitude;
    private String logitude;
    private String descripcion_ubicacion;
    private String eta_hora;
    private String eda_kms;

    @Override
    public String toString() {
        return "Ubicacion{" + "fecha_hora_ubicacion=" + fecha_hora_ubicacion + ", imei=" + imei + ", latitude=" + latitude + ", logitude=" + logitude + ", descripcion_ubicacion=" + descripcion_ubicacion + ", eta_hora=" + eta_hora + ", eda_kms=" + eda_kms + '}';
    }
    
}
