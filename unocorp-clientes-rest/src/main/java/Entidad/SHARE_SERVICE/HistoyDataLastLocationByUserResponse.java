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
@XmlRootElement(name = "HistoyDataLastLocationByUserResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class HistoyDataLastLocationByUserResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "HistoyDataLastLocationByUserResult")
    private HistoyDataLastLocationByUserResult HistoyDataLastLocationByUserResult;

    @Override
    public String toString() {
        return "HistoyDataLastLocationByUserResponse [HistoyDataLastLocationByUserResult=" + HistoyDataLastLocationByUserResult + "]";
    }
    
}
