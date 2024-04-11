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

    private String pedido;
    private String fecha_hora;
    private Double latitud;
    private Double longitud;

    @Override
    public String toString() {
        return "Ubicaciones [pedido=" + pedido + ", fecha_hora=" + fecha_hora + ", latitud=" + latitud + ", longitud=" + longitud + "]";
    }
    
}
