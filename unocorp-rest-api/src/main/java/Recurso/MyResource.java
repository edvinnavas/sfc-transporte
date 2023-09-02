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
    @Path("autenticar/{usuario}/{contrasena}")
    @Produces(MediaType.APPLICATION_JSON)
    public String autenticar(
            @PathParam("usuario") String usuario, 
            @PathParam("contrasena") String contrasena) {
        
        String resultado;

        try {
            Control.Usuario ctrl_usuario = new Control.Usuario();
            resultado = ctrl_usuario.autenticar(usuario, contrasena);
        } catch (Exception ex) {
            resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: autenticar(), ERRROR: " + ex.toString();
        }

        return resultado;
    }
    
    @GET
    @Path("lista_viajes/{fecha_inicio}/{fecha_final}/{estado}/{tipo_flete}/{rastreable}")
    @Produces(MediaType.APPLICATION_JSON)
    public String lista_viajes(
            @PathParam("fecha_inicio") String fecha_inicio, 
            @PathParam("fecha_final") String fecha_final,
            @PathParam("estado") String estado,
            @PathParam("tipo_flete") String tipo_flete,
            @PathParam("rastreable") String rastreable) {
        
        String resultado;

        try {
            Control.Viajes ctrl_viajes = new Control.Viajes();
            resultado = ctrl_viajes.lista_viajes(fecha_inicio, fecha_final, estado, tipo_flete, rastreable);
        } catch (Exception ex) {
            resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: autenticar(), ERRROR: " + ex.toString();
        }

        return resultado;
    }
    
    @GET
    @Path("lista_viajes_ubicaciones/{codigo_pais}/{codigo_compania}/{codigo_planta}/{numero_viaje}/{codigo_cliente}/{codigo_cliente_destino}")
    @Produces(MediaType.APPLICATION_JSON)
    public String lista_viajes_ubicaciones(
            @PathParam("codigo_pais") String codigo_pais, 
            @PathParam("codigo_compania") String codigo_compania,
            @PathParam("codigo_planta") String codigo_planta,
            @PathParam("numero_viaje") Long numero_viaje,
            @PathParam("codigo_cliente") String codigo_cliente, 
            @PathParam("codigo_cliente_destino") String codigo_cliente_destino) {
        
        String resultado;

        try {
            Control.Viajes ctrl_viajes = new Control.Viajes();
            resultado = ctrl_viajes.lista_viajes_ubicaciones(codigo_pais, codigo_compania, codigo_planta, numero_viaje, codigo_cliente, codigo_cliente_destino);
        } catch (Exception ex) {
            resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: autenticar(), ERRROR: " + ex.toString();
        }

        return resultado;
    }

}
