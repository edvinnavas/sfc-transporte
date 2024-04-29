package Entidad.SHARE_SERVICE;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "hst")
@XmlAccessorType(XmlAccessType.FIELD)
public class Hst implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlAttribute(name = "id")
    private String id;

    @XmlElement(name = "DateTimeGPS")
    private String DateTimeGPS;

    @XmlElement(name = "DateTimeServer")
    private String DateTimeServer;

    @XmlElement(name = "Latitude")
    private String Latitude;

    @XmlElement(name = "Longitude")
    private String Longitude;

    @XmlElement(name = "Speed")
    private String Speed;

    @XmlElement(name = "Heading")
    private String Heading;

    @XmlElement(name = "Altitud")
    private String Altitud;

    @XmlElement(name = "Location")
    private String Location;

    @XmlElement(name = "Satellites")
    private String Satellites;

    @XmlElement(name = "IOState")
    private String IOState;

    @XmlElement(name = "EventID")
    private String EventID;

    @XmlElement(name = "Event")
    private String Event;

    @XmlElement(name = "PDI")
    private String PDI;

    @XmlElement(name = "ZONES")
    private String ZONES;

    @XmlElement(name = "FIRMWARE")
    private String FIRMWARE;

    @XmlElement(name = "Ignition")
    private String Ignition;

    @XmlElement(name = "LastOn")
    private String LastOn;

    @XmlElement(name = "LastOff")
    private String LastOff;

    @XmlElement(name = "Odometer")
    private String Odometer;

    @Override
    public String toString() {
        return "Hst [id=" + id + ", DateTimeGPS=" + DateTimeGPS + ", DateTimeServer=" + DateTimeServer + ", Latitude="
                + Latitude + ", Longitude=" + Longitude + ", Speed=" + Speed + ", Heading=" + Heading + ", Altitud="
                + Altitud + ", Location=" + Location + ", Satellites=" + Satellites + ", IOState=" + IOState
                + ", EventID=" + EventID + ", Event=" + Event + ", PDI=" + PDI + ", ZONES=" + ZONES + ", FIRMWARE="
                + FIRMWARE + ", Ignition=" + Ignition + ", LastOn=" + LastOn + ", LastOff=" + LastOff + ", Odometer="
                + Odometer + "]";
    }
    
}
