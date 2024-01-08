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
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Double latitude;
    private Double longitude;
    private Double speed;
    private String dateTime;
    private Device device;
    private String id;

    @Override
    public String toString() {
        return "Data{" + "latitude=" + latitude + ", longitude=" + longitude + ", speed=" + speed + ", dateTime=" + dateTime + ", device=" + device + ", id=" + id + '}';
    }
    
}
