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
@XmlRootElement(name = "Plate")
@XmlAccessorType(XmlAccessType.FIELD)
public class Plate implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "img")
    private String img;

    @XmlAttribute(name = "MobileID")
    private String MobileID;

    @XmlElement(name = "hst")
    private Hst hst;

    @Override
    public String toString() {
        return "Plate [id=" + id + ", img=" + img + ", MobileID=" + MobileID + ", hst=" + hst + "]";
    }
    
}
