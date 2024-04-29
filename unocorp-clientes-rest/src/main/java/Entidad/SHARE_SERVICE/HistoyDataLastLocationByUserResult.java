package Entidad.SHARE_SERVICE;

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
@XmlRootElement(name = "HistoyDataLastLocationByUserResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class HistoyDataLastLocationByUserResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "space")
    private Space space;

    @Override
    public String toString() {
        return "HistoyDataLastLocationByUserResult [space=" + space + "]";
    }
    
}
