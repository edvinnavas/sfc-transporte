package Control;

import ClienteRest.Cliente_Rest_SMS_OPEN;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Ctrl_SMS_OPEN implements Serializable {

    private static final long serialVersionUID = 1L;

    public Ctrl_SMS_OPEN() {
    }

    public String ObtenerUbicaciones() {
        String resultado = "";

        Connection conn = null;

        try {
            Control_Base_Datos control_base_datos = new Control_Base_Datos();
            conn = control_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            String xml_request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:syn=\"http://localhost/SyncVehiculosunopetrol\">"
                    + "<soapenv:Header/>"
                    + "<soapenv:Body>"
                    + "<syn:strObtenerUbicacion>"
                    + "<syn:StrUsuario>CSC-UNO</syn:StrUsuario>"
                    + "<syn:StrContrasena>XFL1&amp;TM</syn:StrContrasena>"
                    + "</syn:strObtenerUbicacion>"
                    + "</soapenv:Body>"
                    + "</soapenv:Envelope>";

            Cliente_Rest_SMS_OPEN cliente_rest_sms_open = new Cliente_Rest_SMS_OPEN();
            String response_cliente_rest_sms_open = cliente_rest_sms_open.ObtenerUbicaciones(xml_request);
            response_cliente_rest_sms_open = response_cliente_rest_sms_open.replaceAll("xmlns=\"http://localhost/SyncVehiculosunopetrol\"", "");

            SOAPMessage soap_response = MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(response_cliente_rest_sms_open.getBytes("UTF-8")));
            Unmarshaller unmarshaller = JAXBContext.newInstance(Entidad.StrObtenerUbicacionResponse.class).createUnmarshaller();
            Entidad.StrObtenerUbicacionResponse str_obtener_ubicacion_response = (Entidad.StrObtenerUbicacionResponse) unmarshaller.unmarshal(soap_response.getSOAPBody().extractContentAsDocument());

            String[] lst_getStrUbicacion = str_obtener_ubicacion_response.getStrUbicacion().split("¥");
            List<Entidad.Ubicacion> lista_ubicaciones = new ArrayList<>();
            for (Integer i = 1; i < lst_getStrUbicacion.length; i++) {
                String[] lst_Ubicacion = lst_getStrUbicacion[i].split("¤");
                Entidad.Ubicacion ubicacion = new Entidad.Ubicacion();
                ubicacion.setName(lst_Ubicacion[0].replaceAll("Â", ""));
                ubicacion.setIMEI(lst_Ubicacion[1].replaceAll("Â", ""));
                ubicacion.setOdometer(lst_Ubicacion[2].replaceAll("Â", ""));
                ubicacion.setLatitude(lst_Ubicacion[3].replaceAll("Â", ""));
                ubicacion.setLongitude(lst_Ubicacion[4].replaceAll("Â", ""));
                ubicacion.setDateTime(lst_Ubicacion[5].replaceAll("Â", ""));
                ubicacion.setSpeed(lst_Ubicacion[6].replaceAll("Â", ""));
                ubicacion.setSpeedMeasure(lst_Ubicacion[7].replaceAll("Â", ""));
                ubicacion.setHeading(lst_Ubicacion[8].replaceAll("Â", ""));
                ubicacion.setLocationDescription(lst_Ubicacion[9].replaceAll("Â", ""));
                ubicacion.setDirverName(lst_Ubicacion[10].replaceAll("Â", ""));
                ubicacion.setDraverCode(lst_Ubicacion[11].replaceAll("Â", ""));
                ubicacion.setIgnition(lst_Ubicacion[12].replaceAll("Â", ""));
                ubicacion.setDateUTC(lst_Ubicacion[13].replaceAll("Â", ""));
                ubicacion.setAddress(lst_Ubicacion[14].replaceAll("Â", ""));
                lista_ubicaciones.add(ubicacion);
            }

            conn.commit();
            conn.setAutoCommit(true);

            Gson gson = new GsonBuilder().serializeNulls().create();
            resultado = gson.toJson(lista_ubicaciones);
        } catch (Exception ex) {
            try {
                resultado = "PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString());
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;

                    resultado = "PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString();
                    System.out.println("PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString());
                }
            } catch (Exception ex1) {
                resultado = "PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones_rollback()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones_rollback()|ERROR:" + ex.toString());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones_finally()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones_finally()|ERROR:" + ex.toString());
            }
        }

        return resultado;
    }

}
