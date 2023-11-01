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
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id_usuario;
    private String nombre_completo;
    private String nombre_usuario;
    private String contrasena;
    private String correo_electronico;
    private Integer activo;
    private String descripcion;
    private Rol rol;
    private String fecha_hora;

    @Override
    public String toString() {
        return "Usuario{" + "id_usuario=" + id_usuario + ", nombre_completo=" + nombre_completo + ", nombre_usuario=" + nombre_usuario + ", contrasena=" + contrasena + ", correo_electronico=" + correo_electronico + ", activo=" + activo + ", descripcion=" + descripcion + ", rol=" + rol + ", fecha_hora=" + fecha_hora + '}';
    }
    
}
