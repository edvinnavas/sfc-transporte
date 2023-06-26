package Entidades;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSesion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_usuario;
    private String nombre_usuario;
    private String nombre_sesion_usuario;
    private String contrasena_usuario;
    private List<String> lista_opcion_menu;

    public UsuarioSesion(Long id_usuario, String nombre_usuario, String nombre_sesion_usuario, String contrasena_usuario, List<String> lista_opcion_menu) {
        this.id_usuario = id_usuario;
        this.nombre_usuario = nombre_usuario;
        this.nombre_sesion_usuario = nombre_sesion_usuario;
        this.contrasena_usuario = contrasena_usuario;
        this.lista_opcion_menu = lista_opcion_menu;
    }

    public UsuarioSesion() {
    }

    @Override
    public String toString() {
        return "UsuarioSesion{" + "id_usuario=" + id_usuario + ", nombre_usuario=" + nombre_usuario + ", nombre_sesion_usuario=" + nombre_sesion_usuario + ", contrasena_usuario=" + contrasena_usuario + ", lista_opcion_menu=" + lista_opcion_menu + '}';
    }

}
