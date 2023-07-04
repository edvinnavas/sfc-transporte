package ClienteRest;

import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.io.entity.StringEntity;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.io.Serializable;

public class Cliente_Rest_SMS_OPEN implements Serializable {

    private static final long serialVersionUID = 1L;

    public Cliente_Rest_SMS_OPEN() {

    }

    public String ObtenerUbicaciones(String xml_request) {
        String xml_response = "";

        try {
            // System.out.println("XML_REQUEST: " + xml_request);
            HttpPost http_request = new HttpPost("http://190.120.7.3/wsunopetrol/service.asmx?WSDL");
            http_request.setHeader("Accept-Encoding", "gzip,deflate");
            http_request.setHeader("Content-Type", "text/xml;charset=UTF-8;");
            http_request.setHeader("SOAPAction", "\"http://localhost/SyncVehiculosunopetrol/strObtenerUbicacion\"");
            http_request.setHeader("Host", "190.120.7.3");
            http_request.setHeader("Connection", "Keep-Alive");
            http_request.setHeader("User-Agent", "Apache-HttpClient/4.5.5 (Java/16.0.1)");
            StringEntity entity = new StringEntity(xml_request, Charset.forName("UTF-8"));
            http_request.setEntity(entity);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse httpResponse = httpClient.execute(http_request);
            // System.out.println("CLIENTE-REST-SMS-OPEN: " + httpResponse.getCode());
            if (httpResponse.getCode() == 200) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();
                xml_response = response.toString();
            } else {
                xml_response = httpResponse.getCode() + " " + httpResponse.getReasonPhrase();
            }
            httpClient.close();
        } catch (Exception ex) {
            System.out.println("PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString());
            xml_response = "PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString();
        }

        // System.out.println("XML_RESPONSE: " + xml_response);

        return xml_response;
    }

}
