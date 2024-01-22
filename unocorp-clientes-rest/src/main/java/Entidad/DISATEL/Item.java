package Entidad.DISATEL;

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

    @XmlElement(name = "ID")
    private String ID;

    @XmlElement(name = "Name")
    private String Name;

    @XmlElement(name = "Plate")
    private String Plate;

    @XmlElement(name = "Fleet")
    private String Fleet;

    @XmlElement(name = "Date")
    private String Date;

    @XmlElement(name = "SystemDate")
    private String SystemDate;

    @XmlElement(name = "Latitude")
    private String Latitude;

    @XmlElement(name = "Longitude")
    private String Longitude;

    @XmlElement(name = "Speed")
    private String Speed;

    @XmlElement(name = "Course")
    private String Course;

    @XmlElement(name = "Address")
    private String Address;

    @XmlElement(name = "Event")
    private String Event;

    @XmlElement(name = "OdometerSW")
    private String OdometerSW;

    @XmlElement(name = "OdometerHW")
    private String OdometerHW;

    @XmlElement(name = "Model")
    private String Model;

    @XmlElement(name = "Color")
    private String Color;

    @XmlElement(name = "Serial")
    private String Serial;

    @XmlElement(name = "Installed")
    private String Installed;

    @XmlElement(name = "ExID")
    private String ExID;

    @XmlElement(name = "IMEI")
    private String IMEI;

    @XmlElement(name = "Phone")
    private String Phone;

    @XmlElement(name = "Brand")
    private String Brand;

    @XmlElement(name = "VIN")
    private String VIN;

    @XmlElement(name = "Description")
    private String Description;

    @XmlElement(name = "Observ")
    private String Observ;

    @XmlElement(name = "Driver")
    private String Driver;

    @Override
    public String toString() {
        return "Viaje [ID=" + ID + ", Name=" + Name + ", Plate=" + Plate + ", Fleet=" + Fleet + ", Date=" + Date + ", SystemDate=" + SystemDate + ", Latitude=" + Latitude + ", Longitude=" + Longitude + ", Speed=" + Speed + ", Course=" + Course + ", Address=" + Address + ", Event=" + Event + ", OdometerSW=" + OdometerSW + ", OdometerHW=" + OdometerHW + ", Model=" + Model + ", Color=" + Color + ", Serial=" + Serial + ", Installed=" + Installed + ", ExID=" + ExID + ", IMEI=" + IMEI + ", Phone=" + Phone + ", Brand=" + Brand + ", VIN=" + VIN + ", Description=" + Description + ", Observ=" + Observ + ", Driver=" + Driver + "]";
    }
    
}
