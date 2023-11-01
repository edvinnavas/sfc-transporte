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
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_cliente;
    private String codigo;
    private String nombre;

    @Override
    public String toString() {
        return "Cliente{" + "id_cliente=" + id_cliente + ", codigo=" + codigo + ", nombre=" + nombre + '}';
    }
    
}
