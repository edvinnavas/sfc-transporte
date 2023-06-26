package Entidades;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Planta implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_planta;
    private String codigo;
    private String nombre;
    private Compania compania;

    public Planta(Long id_planta, String codigo, String nombre, Compania compania) {
        this.id_planta = id_planta;
        this.codigo = codigo;
        this.nombre = nombre;
        this.compania = compania;
    }

    public Planta() {
    }

    @Override
    public String toString() {
        return "Planta{" + "id_planta=" + id_planta + ", codigo=" + codigo + ", nombre=" + nombre + ", compania=" + compania + '}';
    }
    
}
