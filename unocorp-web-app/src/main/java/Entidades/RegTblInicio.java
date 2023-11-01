package Entidades;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegTblInicio implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id_reg_tbl_inicio;
    private String asunto;
    private Date fecha;
    private String leido;

    @Override
    public String toString() {
        return "RegTblInicio{" + "id_reg_tbl_inicio=" + id_reg_tbl_inicio + ", asunto=" + asunto + ", fecha=" + fecha + ", leido=" + leido + '}';
    }

}
