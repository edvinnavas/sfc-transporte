package Entidad;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Credentials implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String database;
    private String userName;
    private String password;
    private String sessionId;

    @Override
    public String toString() {
        return "Credentials{" + "database=" + database + ", userName=" + userName + ", password=" + password + ", sessionId=" + sessionId + '}';
    }
    
}
