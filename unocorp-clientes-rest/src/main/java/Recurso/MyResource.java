package Recurso;

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
            Control.Ctrl_Sfc_Transportes ctrl_sfc_transportes = new Control.Ctrl_Sfc_Transportes("usersfc", "eyb61gfP7M");
            resultado = ctrl_sfc_transportes.obtner_viajes(fecha);
        } catch (Exception ex) {
            resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:obtener_viajes()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:obtener_viajes()|ERROR:" + ex.toString());
        }

        return resultado;
    }

    @GET
    @Path("ObtenerUbicaciones")
    @Produces(MediaType.APPLICATION_JSON)
    public String ObtenerUbicaciones() {
        String resultado;

        try {
            Control.Ctrl_SMS_OPEN ctrl_sms_open = new Control.Ctrl_SMS_OPEN();
            resultado = ctrl_sms_open.ObtenerUbicaciones();
        } catch (Exception ex) {
            resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:obtener_viajes()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:obtener_viajes()|ERROR:" + ex.toString());
        }

        return resultado;
    }

    @GET
    @Path("ObtenerUbicaciones/{database}")
    @Produces(MediaType.APPLICATION_JSON)
    public String ObtenerUbicaciones(@PathParam("database") String database) {
        String resultado;

        try {
            Control.Ctrl_GEOTAB ctrl_geotab = new Control.Ctrl_GEOTAB();
            resultado = ctrl_geotab.ObtenerUbicaciones(database);
        } catch (Exception ex) {
            resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString());
        }

        return resultado;
    }
    
    @GET
    @Path("GoogleDistanceMatrix/{departure_time}/{origins}/{destinations}/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public String distancematrix(
            @PathParam("departure_time") String departure_time, 
            @PathParam("origins") String origins, 
            @PathParam("destinations") String destinations, 
            @PathParam("key") String key) {
        String resultado;

        try {
            Control.Ctrl_Google_Api ctrl_google_api = new Control.Ctrl_Google_Api();
            resultado = ctrl_google_api.distancematrix(departure_time, origins, destinations, key);
        } catch (Exception ex) {
            resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:distancematrix()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:distancematrix()|ERROR:" + ex.toString());
        }

        return resultado;
    }

}
