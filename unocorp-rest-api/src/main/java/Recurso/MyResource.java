package Recurso;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
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
            resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: lista_viajes(), ERRROR: " + ex.toString();
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
            resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: lista_viajes_ubicaciones(), ERRROR: " + ex.toString();
        }

        return resultado;
    }
    
    @GET
    @Path("obtener_cliente_destino/{codigo_cliente_destino}")
    @Produces(MediaType.APPLICATION_JSON)
    public String obtener_cliente_destino(
            @PathParam("codigo_cliente_destino") String codigo_cliente_destino) {
        
        String resultado;

        try {
            Control.Cliente_Destino ctrl_cliente_destino = new Control.Cliente_Destino();
            resultado = ctrl_cliente_destino.obtener_cliente_destino(codigo_cliente_destino);
        } catch (Exception ex) {
            resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: obtener_cliente_destino(), ERRROR: " + ex.toString();
        }

        return resultado;
    }
    
    @PUT
    @Path("cliente_destino/modificar_geozona")
    public String cliente_destino_modificar_geozona(String parametros_cliente_destino) {
        String resultado;

        try {
            String[] parametros = parametros_cliente_destino.split("♣");
            
            Long id_cliente_destino = Long.valueOf(parametros[0]);
            String coordenada1 = parametros[1]; 
            String coordenada2 = parametros[2];
            
            Control.Cliente_Destino ctrl_cliente_destino = new Control.Cliente_Destino();
            resultado = ctrl_cliente_destino.modificar_geozona(id_cliente_destino, coordenada1, coordenada2);
        } catch (Exception ex) {
            resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: cliente_destino_modificar_geozona(), ERRROR: " + ex.toString();
        }

        return resultado;
    }
    
    @GET
    @Path("disponibilidad/{id_transportista}/{id_predio}/{fecha}")
    @Produces(MediaType.APPLICATION_JSON)
    public String disponibilidad(
            @PathParam("id_transportista") Long id_transportista,
            @PathParam("id_predio") Long id_predio,
            @PathParam("fecha") String fecha) {

        String resultado;

        try {
            Control.Viajes ctrl_viajes = new Control.Viajes();
            resultado = ctrl_viajes.disponibilidad(id_transportista, id_predio, fecha);
        } catch (Exception ex) {
            resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: disponibilidad(), ERRROR: " + ex.toString();
        }

        return resultado;
    }
    
    @GET
    @Path("usuario_predio/{id_usuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public String usuario_predio(
            @PathParam("id_usuario") Long id_usuario) {

        String resultado;

        try {
            Control.Usuario ctrl_usuario = new Control.Usuario();
            resultado = ctrl_usuario.usuario_predio(id_usuario);
        } catch (Exception ex) {
            resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: usuario_predio(), ERRROR: " + ex.toString();
        }

        return resultado;
    }
    
    @GET
    @Path("lista_cabezales/{id_transportista}/{id_predio}")
    @Produces(MediaType.APPLICATION_JSON)
    public String lista_cabezales(
            @PathParam("id_transportista") Long id_transportista,
            @PathParam("id_predio") Long id_predio) {

        String resultado;

        try {
            Control.Cabezal ctrl_cabezal = new Control.Cabezal();
            resultado = ctrl_cabezal.lista_cabezales(id_transportista, id_predio);
        } catch (Exception ex) {
            resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: lista_cabezales(), ERRROR: " + ex.toString();
        }

        return resultado;
    }
    
    @GET
    @Path("lista_plantas/{id_transportista}")
    @Produces(MediaType.APPLICATION_JSON)
    public String lista_plantas(
            @PathParam("id_transportista") Long id_transportista) {

        String resultado;

        try {
            Control.Planta ctrl_planta = new Control.Planta();
            resultado = ctrl_planta.lista_plantas(id_transportista);
        } catch (Exception ex) {
            resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: lista_plantas(), ERRROR: " + ex.toString();
        }

        return resultado;
    }
    
    @POST
    @Path("guardar_disponibilidad")
    @Produces(MediaType.TEXT_PLAIN)
    public String guardar_disponibilidad(String jsonString) {
        String resultado;

        try {
            Control.Viajes ctrl_viajes = new Control.Viajes();
            resultado = ctrl_viajes.guardar_disponibilidad(jsonString);
        } catch (Exception ex) {
            resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: guardar_disponibilidad(), ERRROR: " + ex.toString();
        }

        return resultado;
    }

}
