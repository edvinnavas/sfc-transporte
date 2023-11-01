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
public class Planta implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_planta;
    private String codigo;
    private String nombre;
    private Compania compania;
    private Double zona_latitud_1;
    private Double zona_longitud_1;
    private Double zona_latitud_2;
    private Double zona_longitud_2;
    private Double zona_latitud_3;
    private Double zona_longitud_3;
    private Double zona_latitud_4;
    private Double zona_longitud_4;
    private Double zona_latitud_5;
    private Double zona_longitud_5;

    @Override
    public String toString() {
        return "Planta{" + "id_planta=" + id_planta + ", codigo=" + codigo + ", nombre=" + nombre + ", compania=" + compania + ", zona_latitud_1=" + zona_latitud_1 + ", zona_longitud_1=" + zona_longitud_1 + ", zona_latitud_2=" + zona_latitud_2 + ", zona_longitud_2=" + zona_longitud_2 + ", zona_latitud_3=" + zona_latitud_3 + ", zona_longitud_3=" + zona_longitud_3 + ", zona_latitud_4=" + zona_latitud_4 + ", zona_longitud_4=" + zona_longitud_4 + ", zona_latitud_5=" + zona_latitud_5 + ", zona_longitud_5=" + zona_longitud_5 + '}';
    }
    
}
