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
public class Transportista implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_transportista;
    private String codigo;
    private String nombre;
    private Pais pais;
    private Integer rastreable;
    
    @Override
    public String toString() {
        return "Transportista{" + "id_transportista=" + id_transportista + ", codigo=" + codigo + ", nombre=" + nombre + ", pais=" + pais + ", rastreable=" + rastreable + '}';
    }
        
}
