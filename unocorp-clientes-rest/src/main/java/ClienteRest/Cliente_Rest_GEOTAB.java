package ClienteRest;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import org.glassfish.jersey.client.ClientConfig;

public class Cliente_Rest_GEOTAB implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String BASE_URI = "https://my.geotab.com/apiv1/";
    private ClientConfig clientConfig;
    private Client client;

    public Cliente_Rest_GEOTAB() {
        try {
            this.clientConfig = new ClientConfig();
            this.clientConfig.register(String.class);
            this.client = ClientBuilder.newClient(this.clientConfig);
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-clientes-rest, CLASE: " + this.getClass().getName() + ", METODO: ClienteRestApi(), ERRROR: " + ex.toString());
        }
    }
    
    public String GeoTab_Services(String jsonString) {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI);
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.post(Entity.text(jsonString));
            // System.out.println("GeoTab_Services: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-clientes-rest, CLASE: " + this.getClass().getName() + ", METODO: GeoTab_Services(), ERRROR: " + ex.toString());
        }

        return resultado;
    }

}
