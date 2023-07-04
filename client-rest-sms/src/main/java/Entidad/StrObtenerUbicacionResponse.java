package Entidad;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "strObtenerUbicacionResponse")
@XmlAccessorType (XmlAccessType.FIELD)
public class StrObtenerUbicacionResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "strObtenerUbicacionResult")
    private String strObtenerUbicacionResult;
    
    @XmlElement(name = "strUbicacion")
    private String strUbicacion;
    
    @XmlElement(name = "StrError")
    private String StrError;

    @Override
    public String toString() {
        return "StrObtenerUbicacionResponse{" + "strObtenerUbicacionResult=" + strObtenerUbicacionResult + ", strUbicacion=" + strUbicacion + ", StrError=" + StrError + '}';
    }
    
}
