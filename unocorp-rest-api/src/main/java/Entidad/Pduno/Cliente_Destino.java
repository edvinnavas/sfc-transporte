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
    private String pt1;
    private String pt2;
    private String pt3;
    private String pt4;

    @Override
    public String toString() {
        return "Cliente_Destino [id_cliente_destino=" + id_cliente_destino + ", cliente=" + cliente + ", nombre=" + nombre + ", pais=" + pais + ", pt1=" + pt1 + ", pt2=" + pt2 + ", pt3=" + pt3 + ", pt4=" + pt4 + "]";
    }
    
}
