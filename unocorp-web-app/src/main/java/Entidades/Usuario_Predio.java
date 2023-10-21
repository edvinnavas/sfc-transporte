package Entidades;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario_Predio implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id_usuario;
    private String nombre_usuario;
    List<Usuario_Predio_Transportista> lst_transportista;

    public Usuario_Predio(Long id_usuario, String nombre_usuario, List<Usuario_Predio_Transportista> lst_transportista) {
        this.id_usuario = id_usuario;
        this.nombre_usuario = nombre_usuario;
        this.lst_transportista = lst_transportista;
    }

    public Usuario_Predio() {
    }

    @Override
    public String toString() {
        return "Usuario_Predio{" + "id_usuario=" + id_usuario + ", nombre_usuario=" + nombre_usuario + ", lst_transportista=" + lst_transportista + '}';
    }
    
}
