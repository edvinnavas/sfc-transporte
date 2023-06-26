package Entidades;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Estado_Viaje implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_estado_viaje;
    private String codigo;
    private String nombre;

    public Estado_Viaje(Long id_estado_viaje, String codigo, String nombre) {
        this.id_estado_viaje = id_estado_viaje;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Estado_Viaje() {
    }

    @Override
    public String toString() {
        return "Estado_Viaje{" + "id_estado_viaje=" + id_estado_viaje + ", codigo=" + codigo + ", nombre=" + nombre + '}';
    }
    
}
