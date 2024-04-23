package Entidad.DISATEL;

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
@XmlRootElement(name = "ListaVehiculosResponse", namespace = "http://srv.disatelavl.net/ws/")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListaVehiculosResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElementWrapper(name = "vinfo")
    @XmlElement(name = "item")
    private List<Item> items;

    @Override
    public String toString() {
        return "ListaVehiculosResponse [items=" + items + "]";
    }
    
}
