package Entidad.SHARE_SERVICE;

import java.io.Serializable;
import java.util.List;
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
@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "Status")
    private Status Status;

    @XmlElement(name = "Plate")
    private List<Plate> Plates;

    @Override
    public String toString() {
        return "Response [Status=" + Status + ", Plates=" + Plates + "]";
    }
    
}