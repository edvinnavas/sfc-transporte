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
public class UsuarioSesion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_usuario;
    private String nombre_usuario;
    private String nombre_sesion_usuario;
    private String contrasena_usuario;
    private List<String> lista_opcion_menu;

    @Override
    public String toString() {
        return "UsuarioSesion{" + "id_usuario=" + id_usuario + ", nombre_usuario=" + nombre_usuario + ", nombre_sesion_usuario=" + nombre_sesion_usuario + ", contrasena_usuario=" + contrasena_usuario + ", lista_opcion_menu=" + lista_opcion_menu + '}';
    }

}
