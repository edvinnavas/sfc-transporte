package Entidad;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Viaje_Ubicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Ubicacion> lista_ubicaciones;
    private Planta planta;
    private Cliente_Destino cliente_destino;

    @Override
    public String toString() {
        return "Viaje_Ubicacion{" + "lista_ubicaciones=" + lista_ubicaciones + ", planta=" + planta + ", cliente_destino=" + cliente_destino + '}';
    }
    
}
