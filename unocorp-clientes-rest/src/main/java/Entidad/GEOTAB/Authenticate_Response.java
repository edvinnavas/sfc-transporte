package Entidad.GEOTAB;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Authenticate_Response implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Result_Authenticate result;
    private String jsonrpc;

    @Override
    public String toString() {
        return "Authenticate_Response{" + "result=" + result + ", jsonrpc=" + jsonrpc + '}';
    }
    
}
