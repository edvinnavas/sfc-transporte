package Entidad.TECHNO_WEB;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Values implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String LicensePlate;
    private String Name;
    private Long Odometer;
    private String imei;
    private String Latitude;
    private String Longitude;
    private String DateTime;
    private Long SpeedMax;
    private String SpeedAVG;
    private String SpeedMeasure;
    private String EngineStatus;
    private Long TripId;
    private String DateUtc;
    
    @Override
    public String toString() {
        return "Values [LicensePlate=" + LicensePlate + ", Name=" + Name + ", Odometer=" + Odometer + ", imei=" + imei
                + ", Latitude=" + Latitude + ", Longitude=" + Longitude + ", DateTime=" + DateTime + ", SpeedMax="
                + SpeedMax + ", SpeedAVG=" + SpeedAVG + ", SpeedMeasure=" + SpeedMeasure + ", EngineStatus="
                + EngineStatus + ", TripId=" + TripId + ", DateUtc=" + DateUtc + "]";
    }
    
}
