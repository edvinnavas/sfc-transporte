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
public class GetFeed_Response implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Result_GetFeed result;
    private String jsonrpc;

    @Override
    public String toString() {
        return "GetFeed_Response{" + "result=" + result + ", jsonrpc=" + jsonrpc + '}';
    }
    
}
