package Entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Respuesta_WS_Viaje implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mensaje;
    private Integer numero_viajes;
    private Date fecha_viajes;
    private List<Viaje> lista_viajes;

    public Respuesta_WS_Viaje(String mensaje, Integer numero_viajes, Date fecha_viajes, List<Viaje> lista_viajes) {
        this.mensaje = mensaje;
        this.numero_viajes = numero_viajes;
        this.fecha_viajes = fecha_viajes;
        this.lista_viajes = lista_viajes;
    }

    public Respuesta_WS_Viaje() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getNumero_viajes() {
        return numero_viajes;
    }

    public void setNumero_viajes(Integer numero_viajes) {
        this.numero_viajes = numero_viajes;
    }

    public Date getFecha_viajes() {
        return fecha_viajes;
    }

    public void setFecha_viajes(Date fecha_viajes) {
        this.fecha_viajes = fecha_viajes;
    }

    public List<Viaje> getLista_viajes() {
        return lista_viajes;
    }

    public void setLista_viajes(List<Viaje> lista_viajes) {
        this.lista_viajes = lista_viajes;
    }

    @Override
    public String toString() {
        return "Respuesta_WS_Viaje{" + "mensaje=" + mensaje + ", numero_viajes=" + numero_viajes + ", fecha_viajes=" + fecha_viajes + ", lista_viajes=" + lista_viajes + '}';
    }
    
}
