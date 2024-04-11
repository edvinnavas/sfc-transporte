package Entidad.Pduno;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Planta implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_planta;
    private String codigo;
    private String planta;
    private String pais;
    private String pt1;
    private String pt2;
    private String pt3;
    private String pt4;

    @Override
    public String toString() {
        return "Planta [id_planta=" + id_planta + ", codigo=" + codigo + ", planta=" + planta + ", pais=" + pais + ", pt1=" + pt1 + ", pt2=" + pt2 + ", pt3=" + pt3 + ", pt4=" + pt4 + "]";
    }
    
}
