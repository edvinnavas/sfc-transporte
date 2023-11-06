package ClientesRest;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import org.glassfish.jersey.client.ClientConfig;

public class ClienteRestApi implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String BASE_URI = "http://UNOCORP-REST-API:8080/unocorp/";
    private ClientConfig clientConfig;
    private Client client;

    public ClienteRestApi() {
        try {
            this.clientConfig = new ClientConfig();
            this.clientConfig.register(String.class);
            this.client = ClientBuilder.newClient(this.clientConfig);
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: ClienteRestApi(), ERRROR: " + ex.toString());
        }
    }

    public String autenticar(String usuario, String contrasena) {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI).path("autenticar/" + usuario + "/" + contrasena);
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            // System.out.println("CONEXION AUTENTICAR: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: autenticar(), ERRROR: " + ex.toString());
        }

        return resultado;
    }
    
    public String lista_viajes(String fecha_inicio, String fecha_final, String estado, String tipo_flete, String rastreable) {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI).path("lista_viajes/" + fecha_inicio + "/" + fecha_final + "/" + estado + "/" + tipo_flete + "/" + rastreable);
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            // System.out.println("LISTA-VIAJES: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: lista_viajes(), ERRROR: " + ex.toString());
        }

        return resultado;
    }
    
    public String lista_viajes_ubicaciones(String codigo_pais, String codigo_compania, String codigo_planta, Long numero_viaje, String codigo_cliente, String codigo_cliente_destino) {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI).path("lista_viajes_ubicaciones/" + codigo_pais + "/" + codigo_compania + "/" + codigo_planta + "/" + numero_viaje + "/" + codigo_cliente + "/" + codigo_cliente_destino);
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            // System.out.println("LISTA-VIAJES-UBICACIONES: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: lista_viajes_ubicaciones(), ERRROR: " + ex.toString());
        }

        return resultado;
    }
    
    public String obtener_cliente_destino(String codigo_cliente_destino) {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI).path("obtener_cliente_destino/" + codigo_cliente_destino);
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            // System.out.println("OBTENER-CLIENTE-DESTINO: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: obtener_cliente_destino(), ERRROR: " + ex.toString());
        }

        return resultado;
    }
    
    public String cliente_destino_modificar_geozona(String parametros_cliente_destino) {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI).path("cliente_destino/modificar_geozona");
            String data = parametros_cliente_destino;
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
            Response response = invocationBuilder.put(Entity.text(data));
            // System.out.println("CLIENTE-DESTINO-MODIFICAR-GEOZONA: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: cliente_destino_modificar_geozona(), ERRROR: " + ex.toString());
        }

        return resultado;
    }
    
    public String disponibilidad(Long id_transportista, Long id_predio, String fecha) {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI).path("disponibilidad/" + id_transportista + "/" + id_predio + "/" + fecha);
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            // System.out.println("LISTA-VIAJES: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: disponibilidad(), ERRROR: " + ex.toString());
        }

        return resultado;
    }
    
    public String usuario_predio(Long id_usuario) {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI).path("usuario_predio/" + id_usuario);
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            // System.out.println("LISTA-VIAJES: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: usuario_predio(), ERRROR: " + ex.toString());
        }

        return resultado;
    }
    
    public String lista_cabezales(Long id_transportista, Long id_predio) {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI).path("lista_cabezales/" + id_transportista + "/" + id_predio);
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            // System.out.println("LISTA-VIAJES: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: lista_cabezales(), ERRROR: " + ex.toString());
        }

        return resultado;
    }
    
    public String lista_plantas(Long id_transportista) {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI).path("lista_plantas/" + id_transportista);
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            // System.out.println("LISTA-VIAJES: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: lista_plantas(), ERRROR: " + ex.toString());
        }

        return resultado;
    }
    
    public String guardar_disponibilidad(String jsonString) {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI).path("guardar_disponibilidad");
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.post(Entity.text(jsonString));
            // System.out.println("LISTA-VIAJES: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: guardar_disponibilidad(), ERRROR: " + ex.toString());
        }

        return resultado;
    }

}
