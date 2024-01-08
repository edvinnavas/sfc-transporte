package Entidad.GEOTAB;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Credentials_Result implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String database;
    private String sessionId;
    private String userName;

    @Override
    public String toString() {
        return "Credentials_Result{" + "database=" + database + ", sessionId=" + sessionId + ", userName=" + userName + '}';
    }
    
}
