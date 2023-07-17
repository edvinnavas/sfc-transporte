package Entidades;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transportista implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_transportista;
    private String codigo;
    private String nombre;
    private Pais pais;
    private Integer rastreable;

    public Transportista(Long id_transportista, String codigo, String nombre, Pais pais, Integer rastreable) {
        this.id_transportista = id_transportista;
        this.codigo = codigo;
        this.nombre = nombre;
        this.pais = pais;
        this.rastreable = rastreable;
    }

    public Transportista() {
    }

    @Override
    public String toString() {
        return "Transportista{" + "id_transportista=" + id_transportista + ", codigo=" + codigo + ", nombre=" + nombre + ", pais=" + pais + ", rastreable=" + rastreable + '}';
    }
        
}
