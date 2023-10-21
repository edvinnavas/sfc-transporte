package ClienteRest;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class Cliente_Rest_SFC_JDE implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String BASE_URI = "https://sfc.endtoend.com.mx/api-rest-sfc-transporte/sfc/transporte";
    private HttpAuthenticationFeature feature;
    private ClientConfig clientConfig;
    private Client client;

    public Cliente_Rest_SFC_JDE(String user, String pass) {
        try {
            this.feature = HttpAuthenticationFeature.basic(user, pass);
            this.clientConfig = new ClientConfig();
            this.clientConfig.register(this.feature);
            this.clientConfig.register(String.class);
            this.client = ClientBuilder.newClient(this.clientConfig);
        } catch (Exception ex) {
            System.out.println("PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:Cliente_Rest_SFC_JDE()|ERROR:" + ex.toString());
        }
    }

    public String obtener_viajes(String fecha) {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI).path("obtener_viajes/" + fecha);
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            // System.out.println("RESPONSE-STATUS: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            resultado = ex.toString();
            System.out.println("PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:obtener_viajes()|ERROR:" + ex.toString());
        }

        return resultado;
    }

}
