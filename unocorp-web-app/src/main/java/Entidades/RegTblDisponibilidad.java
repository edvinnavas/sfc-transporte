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
public class RegTblDisponibilidad implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_reg_tbl_disponibilidad;
    private String cisterna;
    private String tipo_carga;
    private String bomba;
    private String cabezal;
    private String hora_inicio;
    private String hora_final;
    private String disponibilidad;
    private String planta;

    @Override
    public String toString() {
        return "RegTblDisponibilidad{" + "id_reg_tbl_disponibilidad=" + id_reg_tbl_disponibilidad + ", cisterna=" + cisterna + ", tipo_carga=" + tipo_carga + ", bomba=" + bomba + ", cabezal=" + cabezal + ", hora_inicio=" + hora_inicio + ", hora_final=" + hora_final + ", disponibilidad=" + disponibilidad + ", planta=" + planta + '}';
    }
    
}
