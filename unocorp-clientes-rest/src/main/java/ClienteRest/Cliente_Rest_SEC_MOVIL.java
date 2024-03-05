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

public class Cliente_Rest_SEC_MOVIL implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String BASE_URI = "https://app.seguridadmovil.com.ni/api/";
    private HttpAuthenticationFeature feature;
    private ClientConfig clientConfig;
    private Client client;

    public Cliente_Rest_SEC_MOVIL(String user, String pass) {
        try {
            this.feature = HttpAuthenticationFeature.basic(user, pass);
            this.clientConfig = new ClientConfig();
            this.clientConfig.register(this.feature);
            this.clientConfig.register(String.class);
            this.client = ClientBuilder.newClient(this.clientConfig);
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-clientes-rest, CLASE: " + this.getClass().getName() + ", METODO: Cliente_Rest_SEC_MOVIL(), ERRROR: " + ex.toString());
        }
    }
    
    public String fleet_status() {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI).path("fleet/status");
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            // System.out.println("GeoTab_Services: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-clientes-rest, CLASE: " + this.getClass().getName() + ", METODO: fleet_status(), ERRROR: " + ex.toString());
        }

        return resultado;
    }

}
