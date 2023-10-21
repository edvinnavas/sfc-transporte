package Entidades;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario_Predio_Transportista implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id_transportista;
    private String nombre_transportista;
    private List<Usuario_Transportista_Predio> lst_predio;

    public Usuario_Predio_Transportista(Long id_transportista, String nombre_transportista, List<Usuario_Transportista_Predio> lst_predio) {
        this.id_transportista = id_transportista;
        this.nombre_transportista = nombre_transportista;
        this.lst_predio = lst_predio;
    }

    public Usuario_Predio_Transportista() {
    }

    @Override
    public String toString() {
        return "Predio_Transportista{" + "id_transportista=" + id_transportista + ", nombre_transportista=" + nombre_transportista + ", lst_predio=" + lst_predio + '}';
    }
    
}
