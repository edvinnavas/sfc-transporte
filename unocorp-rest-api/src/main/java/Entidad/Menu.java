package Entidad;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_menu;
    private String nombre;

    @Override
    public String toString() {
        return "Menu{" + "id_menu=" + id_menu + ", nombre=" + nombre + '}';
    }

}
