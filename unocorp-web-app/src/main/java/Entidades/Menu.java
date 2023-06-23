package Entidades;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_menu;
    private String nombre;

    public Menu(Long id_menu, String nombre) {
        this.id_menu = id_menu;
        this.nombre = nombre;
    }

    public Menu() {
    }

    @Override
    public String toString() {
        return "Menu{" + "id_menu=" + id_menu + ", nombre=" + nombre + '}';
    }

}
