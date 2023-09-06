package Entidades;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Viaje_Ubicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    List<Ubicacion> lista_ubicaciones;
    Planta planta;
    Cliente_Destino cliente_destino;

    public Viaje_Ubicacion(List<Ubicacion> lista_ubicaciones, Planta planta, Cliente_Destino cliente_destino) {
        this.lista_ubicaciones = lista_ubicaciones;
        this.planta = planta;
        this.cliente_destino = cliente_destino;
    }

    public Viaje_Ubicacion() {
    }

    @Override
    public String toString() {
        return "Viaje_Ubicacion{" + "lista_ubicaciones=" + lista_ubicaciones + ", planta=" + planta + ", cliente_destino=" + cliente_destino + '}';
    }
    
}
