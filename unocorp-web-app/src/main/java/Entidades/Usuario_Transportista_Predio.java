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
public class Usuario_Transportista_Predio implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id_predio;
    private String nombre_predio;

    @Override
    public String toString() {
        return "Transportista_Predio{" + "id_predio=" + id_predio + ", nombre_predio=" + nombre_predio + '}';
    }
    
}
