package Entidad;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Disponibilidad implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id_transportista;
    private String nombre_transportista;
    private Long id_predio;
    private String nombre_predio;
    private Long id_cisterna;
    private String nombre_cisterna;
    private Long id_cabezal;
    private String nombre_cabezal;
    private String fecha;

    public Disponibilidad(Long id_transportista, String nombre_transportista, Long id_predio, String nombre_predio, Long id_cisterna, String nombre_cisterna, Long id_cabezal, String nombre_cabezal, String fecha) {
        this.id_transportista = id_transportista;
        this.nombre_transportista = nombre_transportista;
        this.id_predio = id_predio;
        this.nombre_predio = nombre_predio;
        this.id_cisterna = id_cisterna;
        this.nombre_cisterna = nombre_cisterna;
        this.id_cabezal = id_cabezal;
        this.nombre_cabezal = nombre_cabezal;
        this.fecha = fecha;
    }

    public Disponibilidad() {
    }

    @Override
    public String toString() {
        return "Disponibilidad{" + "id_transportista=" + id_transportista + ", nombre_transportista=" + nombre_transportista + ", id_predio=" + id_predio + ", nombre_predio=" + nombre_predio + ", id_cisterna=" + id_cisterna + ", nombre_cisterna=" + nombre_cisterna + ", id_cabezal=" + id_cabezal + ", nombre_cabezal=" + nombre_cabezal + ", fecha=" + fecha + '}';
    }
    
}
