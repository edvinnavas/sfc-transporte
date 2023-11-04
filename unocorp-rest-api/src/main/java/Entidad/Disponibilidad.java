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
public class Disponibilidad implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id_transportista;
    private String nombre_transportista;
    private Long id_predio;
    private String nombre_predio;
    private Long id_cisterna;
    private String nombre_cisterna;
    private Long id_tipo_carga_cisterna;
    private String nombre_tipo_carga_cisterna;
    private String bomba_cisterna;
    private Long id_cabezal;
    private String nombre_cabezal;
    private String fecha;
    private String hora_inicio;
    private String hora_final;
    private Long id_planta;
    private String codigo_planta;
    private String nombre_planta;
    private String disponibilida;

    @Override
    public String toString() {
        return "Disponibilidad{" + "id_transportista=" + id_transportista + ", nombre_transportista=" + nombre_transportista + ", id_predio=" + id_predio + ", nombre_predio=" + nombre_predio + ", id_cisterna=" + id_cisterna + ", nombre_cisterna=" + nombre_cisterna + ", id_tipo_carga_cisterna=" + id_tipo_carga_cisterna + ", nombre_tipo_carga_cisterna=" + nombre_tipo_carga_cisterna + ", bomba_cisterna=" + bomba_cisterna + ", id_cabezal=" + id_cabezal + ", nombre_cabezal=" + nombre_cabezal + ", fecha=" + fecha + ", hora_inicio=" + hora_inicio + ", hora_final=" + hora_final + ", id_planta=" + id_planta + ", codigo_planta=" + codigo_planta + ", nombre_planta=" + nombre_planta + ", disponibilida=" + disponibilida + '}';
    }
    
}
