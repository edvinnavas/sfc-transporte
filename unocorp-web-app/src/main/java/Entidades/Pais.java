package Entidades;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_pais;
    private String codigo;
    private String nombre;

    public Pais(Long id_pais, String codigo, String nombre) {
        this.id_pais = id_pais;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Pais() {
    }

    @Override
    public String toString() {
        return "Pais{" + "id_pais=" + id_pais + ", codigo=" + codigo + ", nombre=" + nombre + '}';
    }
    
}
