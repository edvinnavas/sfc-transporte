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

public class Cliente_Rest_TECHNO_WEB implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String BASE_URI = "https://tecnologicaweb.com/svc/";
    private ClientConfig clientConfig;
    private Client client;

    public Cliente_Rest_TECHNO_WEB() {
        try {
            this.clientConfig = new ClientConfig();
            this.clientConfig.register(String.class);
            this.client = ClientBuilder.newClient(this.clientConfig);
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-clientes-rest, CLASE: " + this.getClass().getName() + ", METODO: Cliente_Rest_TECHNO_WEB(), ERRROR: " + ex.toString());
        }
    }
    
    public String svc(String jsonString) {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI);
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.post(Entity.text(jsonString));
            // System.out.println("Techno_Web_Services: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            System.out.println("PROYECTO: unocorp-clientes-rest, CLASE: " + this.getClass().getName() + ", METODO: svc(), ERRROR: " + ex.toString());
        }

        return resultado;
    }

}
