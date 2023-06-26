package Entidades;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente_Destino implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_cliente_destino;
    private String codigo;
    private String nombre;
    private Cliente cliente;

    public Cliente_Destino(Long id_cliente_destino, String codigo, String nombre, Cliente cliente) {
        this.id_cliente_destino = id_cliente_destino;
        this.codigo = codigo;
        this.nombre = nombre;
        this.cliente = cliente;
    }

    public Cliente_Destino() {
    }

    @Override
    public String toString() {
        return "Cliente_Destino{" + "id_cliente_destino=" + id_cliente_destino + ", codigo=" + codigo + ", nombre=" + nombre + ", cliente=" + cliente + '}';
    }
        
}
