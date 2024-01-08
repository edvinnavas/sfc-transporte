package Entidad.GOOGLE;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoogleDistanceMatrixElements implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private GoogleDistanceMatrixNodo distance;
    private GoogleDistanceMatrixNodo duration;
    private GoogleDistanceMatrixNodo duration_in_traffic;
    private String status;

    @Override
    public String toString() {
        return "GoogleDistanceMatrixElements{" + "distance=" + distance + ", duration=" + duration + ", duration_in_traffic=" + duration_in_traffic + ", status=" + status + '}';
    }
    
}
