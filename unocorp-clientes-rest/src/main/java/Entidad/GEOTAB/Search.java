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
public class Search implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String fromDate;

    @Override
    public String toString() {
        return "Search{" + "fromDate=" + fromDate + '}';
    }
    
}
