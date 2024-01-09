package Control;

import java.io.Serializable;

public class Ctrl_Tracking implements Serializable {

    private static final long serialVersionUID = 1L;

    public String tracking(String tipo_orden, Long numero_orden) {
        String resultado = "";

        try {
            ClientesRest.ClienteRestApi cliente_rest_api = new ClientesRest.ClienteRestApi();            
            resultado = cliente_rest_api.tracking(tipo_orden, numero_orden);
        } catch (Exception ex) {
            resultado = "PROYECTO:unocorp-pduno-rest-api|CLASE:" + this.getClass().getName() + "|METODO:tracking()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:unocorp-pduno-rest-api|CLASE:" + this.getClass().getName() + "|METODO:tracking()|ERROR:" + ex.toString());
        }

        return resultado;
    }

}
