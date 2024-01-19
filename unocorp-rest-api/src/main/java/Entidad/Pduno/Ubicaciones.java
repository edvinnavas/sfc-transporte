package Entidad.Pduno;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ubicaciones implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fecha_hora;
    private Double latitud_actual;
    private Double longitud_actual;

    @Override
    public String toString() {
        return "Ubicaciones [fecha_hora=" + fecha_hora + ", latitud_actual=" + latitud_actual + ", longitud_actual=" + longitud_actual + "]";
    }
    
}
