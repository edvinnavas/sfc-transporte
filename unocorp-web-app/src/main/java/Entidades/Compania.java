package Entidades;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Compania implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_compania;
    private String codigo;
    private String nombre;
    private Pais pais;

    public Compania(Long id_compania, String codigo, String nombre, Pais pais) {
        this.id_compania = id_compania;
        this.codigo = codigo;
        this.nombre = nombre;
        this.pais = pais;
    }

    public Compania() {
    }

    @Override
    public String toString() {
        return "Compania{" + "id_compania=" + id_compania + ", codigo=" + codigo + ", nombre=" + nombre + ", pais=" + pais + '}';
    }
    
}
