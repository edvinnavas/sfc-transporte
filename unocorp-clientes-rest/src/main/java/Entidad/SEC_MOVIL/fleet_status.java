package Entidad.SEC_MOVIL;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class fleet_status implements Serializable {

    private static final long serialVersionUID = 1L;

    private Number vehId;
    private String driverId;
    private String vehDescr;
    private String licensePlate;
    private Number vehStat;
    private String remoteId;
    private String place;
    private Number refId;
    private String epoch;
    private String x;
    private String y;
    private Number event;
    private String eventDescription;
    private Number speed;
    private Number heading;
    private Number odometer;
    private Number ignOn;
    private Number ignOnHrs;
    private Number tripDist;
    private Number direction;

    @Override
    public String toString() {
        return "fleet_status [vehId=" + vehId + ", driverId=" + driverId + ", vehDescr=" + vehDescr + ", licensePlate=" + licensePlate + ", vehStat=" + vehStat + ", remoteId=" + remoteId + ", place=" + place + ", refId=" + refId + ", epoch=" + epoch + ", x=" + x + ", y=" + y + ", event=" + event + ", eventDescription=" + eventDescription + ", speed=" + speed + ", heading=" + heading + ", odometer=" + odometer + ", ignOn=" + ignOn + ", ignOnHrs=" + ignOnHrs + ", tripDist=" + tripDist + ", direction=" + direction + "]";
    }
    
}
