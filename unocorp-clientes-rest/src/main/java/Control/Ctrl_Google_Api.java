package Control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;

public class Ctrl_Google_Api implements Serializable {

    private static final long serialVersionUID = 1L;

    public Ctrl_Google_Api() {
    }

    public String distancematrix(String departure_time, String origins, String destinations, String key) {
        String resultado = "";
        try {
            ClienteRest.Cliente_Rest_Google_Maps cliente_rest_google_maps = new ClienteRest.Cliente_Rest_Google_Maps();
            String json_result = cliente_rest_google_maps.distancematrix(departure_time, origins, destinations, key);
            
            Type google_distance_matrix_type = new TypeToken<Entidad.GOOGLE.GoogleDistanceMatrix>() {
            }.getType();
            Entidad.GOOGLE.GoogleDistanceMatrix google_distance_matrix = new Gson().fromJson(json_result, google_distance_matrix_type);
            
            Gson gson = new GsonBuilder().serializeNulls().create();
            resultado = gson.toJson(google_distance_matrix);
        } catch (Exception ex) {
            System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:distancematrix()|ERROR:" + ex.toString());
        }
        
        return resultado;
    }

}
