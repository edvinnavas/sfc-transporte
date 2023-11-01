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
public class Cabezal implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_cabezal;
    private String codigo;
    private String placa;
    private String imei;
    private Transportista transportista;
    
    @Override
    public String toString() {
        return "Cabezal{" + "id_cabezal=" + id_cabezal + ", codigo=" + codigo + ", placa=" + placa + ", imei=" + imei + ", transportista=" + transportista + '}';
    }
    
}
