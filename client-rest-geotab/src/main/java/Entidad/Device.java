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
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String id;

    @Override
    public String toString() {
        return "Device{" + "id=" + id + '}';
    }
    
}
