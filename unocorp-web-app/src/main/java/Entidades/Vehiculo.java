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
public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_vehiculo;
    private String codigo;
    private String placa;
    private Transportista transportista;

    @Override
    public String toString() {
        return "Vehiculo{" + "id_vehiculo=" + id_vehiculo + ", codigo=" + codigo + ", placa=" + placa + ", transportista=" + transportista + '}';
    }
        
}
