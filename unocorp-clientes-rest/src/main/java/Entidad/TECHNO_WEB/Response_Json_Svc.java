package Entidad.TECHNO_WEB;

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
public class Response_Json_Svc implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer estate;
    private String message;
    private Integer count;
    private String dateIni;
    private String dateEnd;
    private List<Values> values;

    @Override
    public String toString() {
        return "Response_Json_Svc [estate=" + estate + ", message=" + message + ", count=" + count + ", dateIni="
                + dateIni + ", dateEnd=" + dateEnd + ", values=" + values + "]";
    }

}
