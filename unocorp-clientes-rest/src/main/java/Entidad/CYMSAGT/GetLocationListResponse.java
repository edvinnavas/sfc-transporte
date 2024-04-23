package Entidad.CYMSAGT;

import java.io.Serializable;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "GetLocationListResponse", namespace = "urn:service_simiex")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetLocationListResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElementWrapper(name = "return")
    @XmlElement(name = "item")
    private List<Item> items;

    @Override
    public String toString() {
        return "GetLocationListResponse [items=" + items + "]";
    }
    
}
