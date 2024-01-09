package Recurso;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;

@Path("gps")
public class MyResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @Path("tracking/{tipo_orden}/{numero_orden}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String tracking_gps(
        @PathParam("tipo_orden") String tipo_orden, 
        @PathParam("numero_orden") Long numero_orden) {

        String resultado = "";

        try {
            Control.Ctrl_Tracking ctrl_tracking = new Control.Ctrl_Tracking();
            resultado = ctrl_tracking.tracking(tipo_orden, numero_orden);
        } catch (Exception ex) {
            System.out.println("PROYECTO:unocorp-pduno-rest-api|CLASE:" + this.getClass().getName() + "|METODO:tracking()|ERROR:" + ex.toString());
        }

        return resultado;
    }

}
