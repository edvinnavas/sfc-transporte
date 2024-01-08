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
public class Authenticate implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String method;
    private Params params;

    @Override
    public String toString() {
        return "Authenticate{" + "method=" + method + ", params=" + params + '}';
    }
    
}
