package Entidades;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario_Predio_Transportista implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id_transportista;
    private String nombre_transportista;
    private List<Usuario_Transportista_Predio> lst_predio;

    @Override
    public String toString() {
        return "Predio_Transportista{" + "id_transportista=" + id_transportista + ", nombre_transportista=" + nombre_transportista + ", lst_predio=" + lst_predio + '}';
    }
    
}
