package Recurso;

import Control.Ctrl_GEOTAB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
    @Path("ObtenerUbicaciones/{database}")
    @Produces(MediaType.APPLICATION_JSON)
    public String ObtenerUbicaciones(@PathParam("database") String database) {
        String resultado;

        try {
            Ctrl_GEOTAB ctrl_geotab = new Ctrl_GEOTAB();
            resultado = ctrl_geotab.ObtenerUbicaciones(database);
        } catch (Exception ex) {
            resultado = "PROYECTO:client-rest-geotab|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:client-rest-geotab|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString());
        }

        return resultado;
    }

}
