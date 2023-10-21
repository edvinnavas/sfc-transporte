package Entidades;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegTblDisponibilidad implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_reg_tbl_disponibilidad;
    private String cisterna;
    private String cabezal;

    public RegTblDisponibilidad(Long id_reg_tbl_disponibilidad, String cisterna, String cabezal) {
        this.id_reg_tbl_disponibilidad = id_reg_tbl_disponibilidad;
        this.cisterna = cisterna;
        this.cabezal = cabezal;
    }

    public RegTblDisponibilidad() {
    }

    @Override
    public String toString() {
        return "RegTblDisponibilidad{" + "id_reg_tbl_disponibilidad=" + id_reg_tbl_disponibilidad + ", cisterna=" + cisterna + ", cabezal=" + cabezal + '}';
    }
    
}
