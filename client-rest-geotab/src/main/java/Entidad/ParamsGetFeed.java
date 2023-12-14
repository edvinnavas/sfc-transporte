package Entidad;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParamsGetFeed implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Search search;
    private String typeName;
    private Credentials credentials;

    @Override
    public String toString() {
        return "ParamsGetFeed{" + "search=" + search + ", typeName=" + typeName + ", credentials=" + credentials + '}';
    }
    
}
