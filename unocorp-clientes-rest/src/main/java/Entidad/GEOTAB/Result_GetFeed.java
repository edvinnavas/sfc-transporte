package Entidad.GEOTAB;

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
public class Result_GetFeed implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private List<Data> data;
    private String toVersion;

    @Override
    public String toString() {
        return "Result_GetFeed{" + "data=" + data + ", toVersion=" + toVersion + '}';
    }
    
}
