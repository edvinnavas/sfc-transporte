package Recurso;

import Control.Ctrl_SMS_OPEN;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;

@Path("unocorp")
public class MyResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @GET
    @Path("ObtenerUbicaciones")
    @Produces(MediaType.APPLICATION_JSON)
    public String ObtenerUbicaciones() {
        String resultado;

        try {
            Ctrl_SMS_OPEN ctrl_sms_open = new Ctrl_SMS_OPEN();
            resultado = ctrl_sms_open.ObtenerUbicaciones();
        } catch (Exception ex) {
            resultado = "PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:obtener_viajes()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:obtener_viajes()|ERROR:" + ex.toString());
        }

        return resultado;
    }
    
    @GET
    @Path("GoogleDistanceMatrix")
    @Produces(MediaType.APPLICATION_JSON)
    public String distancematrix() {
        String resultado;

        try {
            Ctrl_SMS_OPEN ctrl_sms_open = new Ctrl_SMS_OPEN();
            resultado = ctrl_sms_open.distancematrix("now", "14.601372%2C-90.515170", "13.931052%2C-90.797667", "AIzaSyCKLDaTjpMnm-reYkJpAiW2jrl0L4IzyF0");
        } catch (Exception ex) {
            resultado = "PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:distancematrix()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:distancematrix()|ERROR:" + ex.toString());
        }

        return resultado;
    }

}
