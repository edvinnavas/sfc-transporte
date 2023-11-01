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
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id_rol;
    private String nombre;
    private Integer activo;
    private String fecha_hora;
    private List<Menu> lista_menu;

    @Override
    public String toString() {
        return "Rol{" + "id_rol=" + id_rol + ", nombre=" + nombre + ", activo=" + activo + ", fecha_hora=" + fecha_hora + ", lista_menu=" + lista_menu + '}';
    }
    
}
