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
public class GoogleDistanceMatrixNodo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String text;
    private Integer value;

    @Override
    public String toString() {
        return "GoogleDistanceMatrixNodo{" + "text=" + text + ", value=" + value + '}';
    }
    
}
