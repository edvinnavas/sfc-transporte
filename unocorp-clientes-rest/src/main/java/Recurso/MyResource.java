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
    @Path("obtener-viajes-jde/{fecha}")
    @Produces(MediaType.APPLICATION_JSON)
    public String obtener_viajes_jde(@PathParam("fecha") String fecha) {
        String resultado = "";

        try {
            Control.Ctrl_Sfc_Transportes ctrl_sfc_transportes = new Control.Ctrl_Sfc_Transportes("usersfc", "eyb61gfP7M");
            resultado = ctrl_sfc_transportes.obtner_viajes(fecha);
        } catch (Exception ex) {
            resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:obtener_viajes_jde()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:obtener_viajes_jde()|ERROR:" + ex.toString());
        }

        return resultado;
    }

    @GET
    @Path("ws-client-sms-open")
    @Produces(MediaType.APPLICATION_JSON)
    public String ws_client_sms_open() {
        String resultado;

        try {
            Control.Ctrl_SMS_OPEN ctrl_sms_open = new Control.Ctrl_SMS_OPEN();
            resultado = ctrl_sms_open.ObtenerUbicaciones();
        } catch (Exception ex) {
            resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ws_client_sms_open()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ws_client_sms_open()|ERROR:" + ex.toString());
        }

        return resultado;
    }

    @GET
    @Path("ws-client-geotab-cr")
    @Produces(MediaType.APPLICATION_JSON)
    public String ws_client_geotab_cr() {
        String resultado;

        try {
            Control.Ctrl_GEOTAB_CR ctrl_geotab = new Control.Ctrl_GEOTAB_CR();
            resultado = ctrl_geotab.ObtenerUbicaciones();
        } catch (Exception ex) {
            resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ws_client_geotab_cr()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ws_client_geotab_cr()|ERROR:" + ex.toString());
        }

        return resultado;
    }

    @GET
    @Path("ws-client-geotab-gt")
    @Produces(MediaType.APPLICATION_JSON)
    public String ws_client_geotab_gt() {
        String resultado;

        try {
            Control.Ctrl_GEOTAB_GT ctrl_geotab = new Control.Ctrl_GEOTAB_GT();
            resultado = ctrl_geotab.ObtenerUbicaciones();
        } catch (Exception ex) {
            resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ws_client_geotab_gt()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ws_client_geotab_gt()|ERROR:" + ex.toString());
        }

        return resultado;
    }

    @GET
    @Path("ws-client-disatel")
    @Produces(MediaType.APPLICATION_JSON)
    public String ws_client_disatel() {

        String resultado;

        try {
            Control.Ctrl_DISATEL ctrl_disatel = new Control.Ctrl_DISATEL();
            resultado = ctrl_disatel.ListaVehiculos();
        } catch (Exception ex) {
            resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ws_client_disatel()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ws_client_disatel()|ERROR:" + ex.toString());
        }

        return resultado;
    }
    
    @GET
    @Path("ws-client-sec-movil")
    @Produces(MediaType.APPLICATION_JSON)
    public String ws_client_sec_movil() {

        String resultado;

        try {
            Control.Ctrl_SEC_MOVIL ctrl_sec_movil = new Control.Ctrl_SEC_MOVIL("421_5fadff295f483", "roberto");
            resultado = ctrl_sec_movil.fleet_status();
        } catch (Exception ex) {
            resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ws_client_sec_movil()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ws_client_sec_movil()|ERROR:" + ex.toString());
        }

        return resultado;
    }

    @GET
    @Path("ws-client-detektor")
    @Produces(MediaType.APPLICATION_JSON)
    public String ws_client_detektor() {
        String resultado;

        try {
            Control.Ctrl_DETEKTOR ctrl_detektor = new Control.Ctrl_DETEKTOR();
            resultado = ctrl_detektor.Replica();
        } catch (Exception ex) {
            resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ws_client_detektor()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ws_client_detektor()|ERROR:" + ex.toString());
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
