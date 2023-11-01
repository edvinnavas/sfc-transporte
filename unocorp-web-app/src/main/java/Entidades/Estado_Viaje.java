package Entidades;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Estado_Viaje implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_estado_viaje;
    private String codigo;
    private String nombre;

    @Override
    public String toString() {
        return "Estado_Viaje{" + "id_estado_viaje=" + id_estado_viaje + ", codigo=" + codigo + ", nombre=" + nombre + '}';
    }
    
}
