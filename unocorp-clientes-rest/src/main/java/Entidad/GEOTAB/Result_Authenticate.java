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
public class Result_Authenticate implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Credentials_Result credentials;
    private String path;

    @Override
    public String toString() {
        return "Result_Authenticate{" + "credentials=" + credentials + ", path=" + path + '}';
    }
    
}
