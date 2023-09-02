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
    private Double zona_latitud_1;
    private Double zona_longitud_1;
    private Double zona_latitud_2;
    private Double zona_longitud_2;
    private Double zona_latitud_3;
    private Double zona_longitud_3;
    private Double zona_latitud_4;
    private Double zona_longitud_4;
    private Double zona_latitud_5;
    private Double zona_longitud_5;

    public Cliente_Destino(Long id_cliente_destino, String codigo, String nombre, Cliente cliente, Double zona_latitud_1, Double zona_longitud_1, Double zona_latitud_2, Double zona_longitud_2, Double zona_latitud_3, Double zona_longitud_3, Double zona_latitud_4, Double zona_longitud_4, Double zona_latitud_5, Double zona_longitud_5) {
        this.id_cliente_destino = id_cliente_destino;
        this.codigo = codigo;
        this.nombre = nombre;
        this.cliente = cliente;
        this.zona_latitud_1 = zona_latitud_1;
        this.zona_longitud_1 = zona_longitud_1;
        this.zona_latitud_2 = zona_latitud_2;
        this.zona_longitud_2 = zona_longitud_2;
        this.zona_latitud_3 = zona_latitud_3;
        this.zona_longitud_3 = zona_longitud_3;
        this.zona_latitud_4 = zona_latitud_4;
        this.zona_longitud_4 = zona_longitud_4;
        this.zona_latitud_5 = zona_latitud_5;
        this.zona_longitud_5 = zona_longitud_5;
    }

    public Cliente_Destino() {
    }

    @Override
    public String toString() {
        return "Cliente_Destino{" + "id_cliente_destino=" + id_cliente_destino + ", codigo=" + codigo + ", nombre=" + nombre + ", cliente=" + cliente + ", zona_latitud_1=" + zona_latitud_1 + ", zona_longitud_1=" + zona_longitud_1 + ", zona_latitud_2=" + zona_latitud_2 + ", zona_longitud_2=" + zona_longitud_2 + ", zona_latitud_3=" + zona_latitud_3 + ", zona_longitud_3=" + zona_longitud_3 + ", zona_latitud_4=" + zona_latitud_4 + ", zona_longitud_4=" + zona_longitud_4 + ", zona_latitud_5=" + zona_latitud_5 + ", zona_longitud_5=" + zona_longitud_5 + '}';
    }
        
}
