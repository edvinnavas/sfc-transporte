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
import java.sql.Statement;
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
            
            Control_Base_Datos control_base_datos = new Control_Base_Datos();
            conn = control_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);
            
            Long ID_SMS_OPEN = control_base_datos.ObtenerLong("SELECT IFNULL(MAX(A.ID_SMS_OPEN),0)+1 MAX_ID FROM SMS_OPEN_ENCABEZADO A", conn);
            
            String cadenasql = "INSERT INTO SMS_OPEN_ENCABEZADO (ID_SMS_OPEN, FECHA_ACTUALIZACION, NUMERO_UBICACIONES) VALUES (" 
                    + ID_SMS_OPEN + ","
                    + "CURRENT_TIMESTAMP" + ","
                    + "0" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            
            Integer CORRELATIVO = 0;
            for (Integer i = 0; i < lista_ubicaciones.size(); i++) {
                CORRELATIVO++;
                
                cadenasql = "INSERT INTO SMS_OPEN_DETALLE ("
                        + "ID_SMS_OPEN, "
                        + "CORRELATIVO, "
                        + "NAME_VEHICULO, "
                        + "IMEI, "
                        + "ODOMETER, "
                        + "LATITUDE, "
                        + "LONGITUDE, "
                        + "DATETIME_UBICACION, "
                        + "SPEED, "
                        + "SPEEDMEASURE, "
                        + "HEADING, "
                        + "LOCATIONDESCRIPTION, "
                        + "DIRVERNAME, "
                        + "DRAVERCODE, "
                        + "IGNITION, "
                        + "DATEUTC, "
                        + "ADDRESS,"
                        + "FECHA_HORA) VALUES ("
                        + ID_SMS_OPEN + ","
                        + CORRELATIVO + ",'"
                        + lista_ubicaciones.get(i).getName() + "','"
                        + lista_ubicaciones.get(i).getIMEI() + "','"
                        + lista_ubicaciones.get(i).getOdometer() + "','"
                        + lista_ubicaciones.get(i).getLatitude() + "','"
                        + lista_ubicaciones.get(i).getLongitude() + "','"
                        + lista_ubicaciones.get(i).getDateTime() + "','"
                        + lista_ubicaciones.get(i).getSpeed() + "','"
                        + lista_ubicaciones.get(i).getSpeedMeasure() + "','"
                        + lista_ubicaciones.get(i).getHeading() + "','"
                        + lista_ubicaciones.get(i).getLocationDescription() + "','"
                        + lista_ubicaciones.get(i).getDirverName() + "','"
                        + lista_ubicaciones.get(i).getDraverCode() + "','"
                        + lista_ubicaciones.get(i).getIgnition() + "','"
                        + lista_ubicaciones.get(i).getDateUTC() + "','"
                        + lista_ubicaciones.get(i).getAddress() + "',"
                        + "CURRENT_TIMESTAMP" + ")";
                stmt = conn.createStatement();
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }
            
            cadenasql = "UPDATE SMS_OPEN_ENCABEZADO SET NUMERO_UBICACIONES=" + CORRELATIVO + " WHERE ID_SMS_OPEN=" + ID_SMS_OPEN;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();
            
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
