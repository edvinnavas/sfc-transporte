package ClientesRest;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import org.glassfish.jersey.client.ClientConfig;

public class ClienteRestApi implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String BASE_URI = "http://UNOCORP-REST-API:8080/unocorp/";
    // private static final String BASE_URI = "http://192.200.109.21:9003/unocorp/";
    private ClientConfig clientConfig;
    private Client client;

    public ClienteRestApi() {
        try {
            this.clientConfig = new ClientConfig();
            this.clientConfig.register(String.class);
            this.client = ClientBuilder.newClient(this.clientConfig);
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-pduno-rest-api, CLASE: " + this.getClass().getName() + ", METODO: ClienteRestApi(), ERRROR: " + ex.toString());
        }
    }

    public String tracking(String tipo_orden, Long numero_orden) {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI).path("tracking/" + tipo_orden + "/" + numero_orden);
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            // System.out.println("LISTA-VIAJES: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-pduno-rest-api, CLASE: " + this.getClass().getName() + ", METODO: lista_plantas(), ERRROR: " + ex.toString());
        }

        return resultado;
    }

}
