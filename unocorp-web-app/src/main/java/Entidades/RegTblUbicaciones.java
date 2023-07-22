package Entidades;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegTblUbicaciones implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_reg_tbl_ubicaciones;
    private String fecha_hora_ubicacion;
    private String latitude;
    private String logitude;
    private String descripcion_ubicacion;
    private Double eta_hora;
    private Double eda_kms;

    public RegTblUbicaciones(Long id_reg_tbl_ubicaciones, String fecha_hora_ubicacion, String latitude, String logitude, String descripcion_ubicacion, Double eta_hora, Double eda_kms) {
        this.id_reg_tbl_ubicaciones = id_reg_tbl_ubicaciones;
        this.fecha_hora_ubicacion = fecha_hora_ubicacion;
        this.latitude = latitude;
        this.logitude = logitude;
        this.descripcion_ubicacion = descripcion_ubicacion;
        this.eta_hora = eta_hora;
        this.eda_kms = eda_kms;
    }

    public RegTblUbicaciones() {
    }

    @Override
    public String toString() {
        return "RegTblUbicaciones{" + "id_reg_tbl_ubicaciones=" + id_reg_tbl_ubicaciones + ", fecha_hora_ubicacion=" + fecha_hora_ubicacion + ", latitude=" + latitude + ", logitude=" + logitude + ", descripcion_ubicacion=" + descripcion_ubicacion + ", eta_hora=" + eta_hora + ", eda_kms=" + eda_kms + '}';
    }
    
}
