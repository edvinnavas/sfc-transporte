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
public class Cliente_Destino implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_cliente_destino;
    private String cliente;
    private String nombre;
    private String pais;
    private Double pt1;
    private Double pt2;
    private Double pt3;
    private Double pt4;
    private Double pt5;
    private Double pt6;
    private Double pt7;
    private Double pt8;
    private Double pt9;
    private Double pt10;
    
    @Override
    public String toString() {
        return "Cliente_Destino [id_cliente_destino=" + id_cliente_destino + ", cliente=" + cliente + ", nombre="
                + nombre + ", pais=" + pais + ", pt1=" + pt1 + ", pt2=" + pt2 + ", pt3=" + pt3 + ", pt4=" + pt4
                + ", pt5=" + pt5 + ", pt6=" + pt6 + ", pt7=" + pt7 + ", pt8=" + pt8 + ", pt9=" + pt9 + ", pt10=" + pt10
                + "]";
    }
    
}
