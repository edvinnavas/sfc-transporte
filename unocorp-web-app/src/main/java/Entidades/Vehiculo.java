package Entidades;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_vehiculo;
    private String codigo;
    private String placa;
    private Transportista transportista;

    public Vehiculo(Long id_vehiculo, String codigo, String placa, Transportista transportista) {
        this.id_vehiculo = id_vehiculo;
        this.codigo = codigo;
        this.placa = placa;
        this.transportista = transportista;
    }

    public Vehiculo() {
    }

    @Override
    public String toString() {
        return "Vehiculo{" + "id_vehiculo=" + id_vehiculo + ", codigo=" + codigo + ", placa=" + placa + ", transportista=" + transportista + '}';
    }
        
}
