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
public class Params implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String database;
    private String userName;
    private String password;

    @Override
    public String toString() {
        return "Params{" + "database=" + database + ", userName=" + userName + ", password=" + password + '}';
    }
    
}
