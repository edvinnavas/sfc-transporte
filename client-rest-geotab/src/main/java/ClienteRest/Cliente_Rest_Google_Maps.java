package ClienteRest;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import org.glassfish.jersey.client.ClientConfig;

public class Cliente_Rest_Google_Maps implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String BASE_URI = "https://maps.googleapis.com/maps/api";
    private ClientConfig clientConfig;
    private Client client;

    public Cliente_Rest_Google_Maps() {
        try {
            this.clientConfig = new ClientConfig();
            this.clientConfig.register(String.class);
            this.client = ClientBuilder.newClient(this.clientConfig);
        } catch (Exception ex) {
            System.out.println("PROYECTO: cliente-rest-sms, CLASE: " + this.getClass().getName() + ", METODO: Cliente_Rest_Google_Maps(), ERRROR: " + ex.toString());
        }
    }

    public String distancematrix(String departure_time, String origins, String destinations, String key) {
        String resultado = "";

        try {
            WebTarget webTarget = this.client.target(BASE_URI)
                    .path("distancematrix/json")
                    .queryParam("departure_time", departure_time)
                    .queryParam("origins", origins)
                    .queryParam("destinations", destinations)
                    .queryParam("key", key);
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            // System.out.println("CONEXION AUTENTICAR: " + response.getStatus());
            if (response.getStatus() == 200) {
                resultado = response.readEntity(String.class);
            } else {
                resultado = response.getStatus() + ": " + response.getStatusInfo();
            }
        } catch (Exception ex) {
            System.out.println("PROYECTO: cliente-rest-sms, CLASE: " + this.getClass().getName() + ", METODO: distancematrix(), ERRROR: " + ex.toString());
        }

        return resultado;
    }

}
