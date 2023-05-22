package Recurso;

import Control.Ctrl_Transportes_JDE;
import Entidad.Respuesta_WS_Viaje;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;

@Path("transporte")
public class MyResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @Path("obtener_viajes/{fecha}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String obtener_vaijaes(@PathParam("fecha") String fecha) {
        String resultado = "";

        try {
            Ctrl_Transportes_JDE ctrl_transportes_jde = new Ctrl_Transportes_JDE();
            Respuesta_WS_Viaje respuesta_ws_viaje = ctrl_transportes_jde.obtener_viajes(fecha);

            Gson gson = new GsonBuilder().serializeNulls().create();
            resultado = gson.toJson(respuesta_ws_viaje);
        } catch (Exception ex) {
            System.out.println("PROYECTO:api-grupoterra-svfel-v3|CLASE:" + this.getClass().getName() + "|METODO:obtener_viajes()|ERROR:" + ex.toString());
        }

        return resultado;
    }

}
