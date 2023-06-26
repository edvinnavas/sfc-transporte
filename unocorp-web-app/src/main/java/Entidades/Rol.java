package Entidades;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_rol;
    private String nombre;
    private Integer activo;
    private String fecha_hora;
    private List<Menu> lista_menu;

    public Rol(Long id_rol, String nombre, Integer activo, String fecha_hora, List<Menu> lista_menu) {
        this.id_rol = id_rol;
        this.nombre = nombre;
        this.activo = activo;
        this.fecha_hora = fecha_hora;
        this.lista_menu = lista_menu;
    }

    public Rol() {
    }

    @Override
    public String toString() {
        return "Rol{" + "id_rol=" + id_rol + ", nombre=" + nombre + ", activo=" + activo + ", fecha_hora=" + fecha_hora + ", lista_menu=" + lista_menu + '}';
    }

}
