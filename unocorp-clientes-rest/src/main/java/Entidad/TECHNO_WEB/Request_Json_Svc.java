package Entidad.TECHNO_WEB;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Request_Json_Svc implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String password;
    private String dateIni;
    private String dateEnd;

    @Override
    public String toString() {
        return "request_json_svc [password=" + password + ", dateIni=" + dateIni + ", dateEnd=" + dateEnd + "]";
    }
    
}
