package Recurso;

import Control.Ctrl_Sfc_Transportes;
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
    @Path("obtener_viajes/{fecha}")
    @Produces(MediaType.APPLICATION_JSON)
    public String obtener_viajes(@PathParam("fecha") String fecha) {
        String resultado = "";

        try {
            Ctrl_Sfc_Transportes ctrl_sfc_transportes = new Ctrl_Sfc_Transportes("usersfc", "eyb61gfP7M");
            resultado = ctrl_sfc_transportes.obtner_viajes(fecha);
        } catch (Exception ex) {
            resultado = "PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:obtener_viajes()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:obtener_viajes()|ERROR:" + ex.toString());
        }

        return resultado;
    }

}
