package Entidad;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario_Transportista_Predio implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id_predio;
    private String nombre_predio;

    public Usuario_Transportista_Predio(Long id_predio, String nombre_predio) {
        this.id_predio = id_predio;
        this.nombre_predio = nombre_predio;
    }

    public Usuario_Transportista_Predio() {
    }

    @Override
    public String toString() {
        return "Transportista_Predio{" + "id_predio=" + id_predio + ", nombre_predio=" + nombre_predio + '}';
    }
    
}
