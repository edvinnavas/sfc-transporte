package Entidad.GOOGLE;

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
public class GoogleDistanceMatrixRows implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private List <GoogleDistanceMatrixElements> elements;

    @Override
    public String toString() {
        return "GoogleDistanceMatrixRows{" + "elements=" + elements + '}';
    }
    
}
