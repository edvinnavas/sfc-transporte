package Entidad.SMS_OPEN;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ubicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String Name;
    private String IMEI;
    private String Odometer;
    private String Latitude;
    private String Longitude;
    private String DateTime;
    private String Speed;
    private String SpeedMeasure;
    private String Heading;
    private String LocationDescription;
    private String DirverName;
    private String DraverCode;
    private String Ignition;
    private String DateUTC;
    private String Address;

    @Override
    public String toString() {
        return "Ubicacion{" + "Name=" + Name + ", IMEI=" + IMEI + ", Odometer=" + Odometer + ", Latitude=" + Latitude + ", Longitude=" + Longitude + ", DateTime=" + DateTime + ", Speed=" + Speed + ", SpeedMeasure=" + SpeedMeasure + ", Heading=" + Heading + ", LocationDescription=" + LocationDescription + ", DirverName=" + DirverName + ", DraverCode=" + DraverCode + ", Ignition=" + Ignition + ", DateUTC=" + DateUTC + ", Address=" + Address + '}';
    }
    
}
