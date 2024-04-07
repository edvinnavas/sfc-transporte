package Entidad.TRAMAQ;

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
@XmlRootElement(name = "Location")
@XmlAccessorType(XmlAccessType.FIELD)
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "LicensePlate")
    private String LicensePlate;

    @XmlElement(name = "Name")
    private String Name;

    @XmlElement(name = "IMEI")
    private String IMEI;

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

    @XmlElement(name = "Ignition")
    private String Ignition;

    @XmlElement(name = "DateUtc")
    private String DateUtc;

    @XmlElement(name = "Address")
    private String Address;

    @XmlElement(name = "Eventos")
    private String Eventos;

    @XmlElement(name = "Referencia")
    private String Referencia;

    @Override
    public String toString() {
        return "Location [LicensePlate=" + LicensePlate + ", Name=" + Name + ", IMEI=" + IMEI + ", Odometer=" + Odometer
                + ", Latitude=" + Latitude + ", Longitude=" + Longitude + ", DateTime=" + DateTime + ", Speed=" + Speed
                + ", SpeedMeasure=" + SpeedMeasure + ", Heading=" + Heading + ", EngineStatus=" + EngineStatus
                + ", LocationDescription=" + LocationDescription + ", DriverName=" + DriverName + ", DriverCode="
                + DriverCode + ", Ignition=" + Ignition + ", DateUtc=" + DateUtc + ", Address=" + Address + ", Eventos="
                + Eventos + ", Referencia=" + Referencia + "]";
    }
    
}
