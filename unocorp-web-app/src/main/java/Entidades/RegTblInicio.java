package Entidades;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegTblInicio implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_reg_tbl_inicio;
    private String asunto;
    private Date fecha;
    private String leido;

    public RegTblInicio(Long id_reg_tbl_inicio, String asunto, Date fecha, String leido) {
        this.id_reg_tbl_inicio = id_reg_tbl_inicio;
        this.asunto = asunto;
        this.fecha = fecha;
        this.leido = leido;
    }

    public RegTblInicio() {
    }

    @Override
    public String toString() {
        return "RegTblInicio{" + "id_reg_tbl_inicio=" + id_reg_tbl_inicio + ", asunto=" + asunto + ", fecha=" + fecha + ", leido=" + leido + '}';
    }

}
