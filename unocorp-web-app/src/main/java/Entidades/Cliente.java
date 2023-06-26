package Entidades;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_cliente;
    private String codigo;
    private String nombre;

    public Cliente(Long id_cliente, String codigo, String nombre) {
        this.id_cliente = id_cliente;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Cliente() {
    }

    @Override
    public String toString() {
        return "Cliente{" + "id_cliente=" + id_cliente + ", codigo=" + codigo + ", nombre=" + nombre + '}';
    }
    
}
