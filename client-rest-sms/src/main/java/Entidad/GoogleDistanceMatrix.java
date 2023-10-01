package Entidad;

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
public class GoogleDistanceMatrix implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private List <String> destination_addresses;
    private List <String> origin_addresses;
    private List <GoogleDistanceMatrixRows> rows;
    private String status;

    @Override
    public String toString() {
        return "GoogleDistanceMatrix{" + "destination_addresses=" + destination_addresses + ", origin_addresses=" + origin_addresses + ", rows=" + rows + ", status=" + status + '}';
    }
    
}
