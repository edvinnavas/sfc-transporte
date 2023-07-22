package Entidades;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ubicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fecha_hora_ubicacion;
    private String imei;
    private String latitude;
    private String logitude;
    private String descripcion_ubicacion;
    private Double eta_hora;
    private Double eda_kms;

    public Ubicacion(String fecha_hora_ubicacion, String imei, String latitude, String logitude, String descripcion_ubicacion, Double eta_hora, Double eda_kms) {
        this.fecha_hora_ubicacion = fecha_hora_ubicacion;
        this.imei = imei;
        this.latitude = latitude;
        this.logitude = logitude;
        this.descripcion_ubicacion = descripcion_ubicacion;
        this.eta_hora = eta_hora;
        this.eda_kms = eda_kms;
    }

    public Ubicacion() {
    }

    @Override
    public String toString() {
        return "Ubicacion{" + "fecha_hora_ubicacion=" + fecha_hora_ubicacion + ", imei=" + imei + ", latitude=" + latitude + ", logitude=" + logitude + ", descripcion_ubicacion=" + descripcion_ubicacion + ", eta_hora=" + eta_hora + ", eda_kms=" + eda_kms + '}';
    }
    
}
