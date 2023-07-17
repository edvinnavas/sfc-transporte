package Entidades;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cabezal implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_cabezal;
    private String codigo;
    private String placa;
    private String imei;
    private Transportista transportista;

    public Cabezal(Long id_cabezal, String codigo, String placa, String imei, Transportista transportista) {
        this.id_cabezal = id_cabezal;
        this.codigo = codigo;
        this.placa = placa;
        this.imei = imei;
        this.transportista = transportista;
    }

    public Cabezal() {
    }

    @Override
    public String toString() {
        return "Cabezal{" + "id_cabezal=" + id_cabezal + ", codigo=" + codigo + ", placa=" + placa + ", imei=" + imei + ", transportista=" + transportista + '}';
    }
    
}
