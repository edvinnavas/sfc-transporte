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
public class Compania implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_compania;
    private String codigo;
    private String nombre;
    private Pais pais;
    
    @Override
    public String toString() {
        return "Compania{" + "id_compania=" + id_compania + ", codigo=" + codigo + ", nombre=" + nombre + ", pais=" + pais + '}';
    }
    
}
