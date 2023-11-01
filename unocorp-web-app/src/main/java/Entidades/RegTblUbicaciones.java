package Entidades;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegTblUbicaciones implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_reg_tbl_ubicaciones;
    private String fecha_hora_ubicacion;
    private String latitude;
    private String logitude;
    private String descripcion_ubicacion;
    private String eta_hora;
    private String eda_kms;

    @Override
    public String toString() {
        return "RegTblUbicaciones{" + "id_reg_tbl_ubicaciones=" + id_reg_tbl_ubicaciones + ", fecha_hora_ubicacion=" + fecha_hora_ubicacion + ", latitude=" + latitude + ", logitude=" + logitude + ", descripcion_ubicacion=" + descripcion_ubicacion + ", eta_hora=" + eta_hora + ", eda_kms=" + eda_kms + '}';
    }
    
}
