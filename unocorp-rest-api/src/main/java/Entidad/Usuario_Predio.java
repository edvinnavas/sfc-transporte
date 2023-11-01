package Entidad;

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
public class Usuario_Predio implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id_usuario;
    private String nombre_usuario;
    private List<Usuario_Predio_Transportista> lst_transportista;
    
    @Override
    public String toString() {
        return "Usuario_Predio{" + "id_usuario=" + id_usuario + ", nombre_usuario=" + nombre_usuario + ", lst_transportista=" + lst_transportista + '}';
    }
    
}
