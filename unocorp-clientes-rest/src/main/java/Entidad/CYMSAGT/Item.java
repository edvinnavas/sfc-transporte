package Entidad.CYMSAGT;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
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
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "LicensePlate")
    private String LicensePlate;

    @XmlElement(name = "Name")
    private String Name;

    @XmlElement(name = "Imei")
    private String Imei;

    @XmlElement(name = "Odometer")
    private String Odometer;

    @XmlElement(name = "Latitude")
    private String Latitude;

    @XmlElement(name = "Longitude")
    private String Longitude;

    @XmlElement(name = "DateTime")
    private String DateTime;

    @XmlElement(name = "Speed")
    private String Speed;

    @XmlElement(name = "SpeedMeasure")
    private String SpeedMeasure;

    @XmlElement(name = "Heading")
    private String Heading;

    @XmlElement(name = "EngineStatus")
    private String EngineStatus;

    @XmlElement(name = "LocationDescription")
    private String LocationDescription;

    @XmlElement(name = "DriverName")
    private String DriverName;

    @XmlElement(name = "DriverCode")
    private String DriverCode;

    @XmlElement(name = "TripId")
    private String TripId;

    @XmlElement(name = "Ignition")
    private String Ignition;

    @XmlElement(name = "DateUtc")
    private String DateUtc;

    @XmlElement(name = "Address")
    private String Address;

    @Override
    public String toString() {
        return "Item [id=" + id + ", LicensePlate=" + LicensePlate + ", Name=" + Name + ", Imei=" + Imei + ", Odometer="
                + Odometer + ", Latitude=" + Latitude + ", Longitude=" + Longitude + ", DateTime=" + DateTime
                + ", Speed=" + Speed + ", SpeedMeasure=" + SpeedMeasure + ", Heading=" + Heading + ", EngineStatus="
                + EngineStatus + ", LocationDescription=" + LocationDescription + ", DriverName=" + DriverName
                + ", DriverCode=" + DriverCode + ", TripId=" + TripId + ", Ignition=" + Ignition + ", DateUtc="
                + DateUtc + ", Address=" + Address + "]";
    }
    
}
