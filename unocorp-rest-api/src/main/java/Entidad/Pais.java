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
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_pais;
    private String codigo;
    private String nombre;
    
    @Override
    public String toString() {
        return "Pais{" + "id_pais=" + id_pais + ", codigo=" + codigo + ", nombre=" + nombre + '}';
    }
    
}
