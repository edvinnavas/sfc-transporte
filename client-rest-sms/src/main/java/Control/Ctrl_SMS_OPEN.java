package Control;

import ClienteRest.Cliente_Rest_Google_Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

            ClienteRest.Cliente_Rest_SMS_OPEN cliente_rest_sms_open = new ClienteRest.Cliente_Rest_SMS_OPEN();
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

            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            cadenasql = "SELECT DISTINCT "
                    + "V.ID_PAIS, "
                    + "V.ID_COMPANIA, "
                    + "V.ID_PLANTA, "
                    + "V.NUMERO_VIAJE, "
                    + "V.TIPO_ORDEN_VENTA, "
                    + "V.NUMERO_ORDEN_VENTA, "
                    + "STR_TO_DATE(SOD.DATETIME_UBICACION, '%d-%m-%Y %H:%i:%s') FECHA_HORA_UBICACION, "
                    + "SOD.IMEI, "
                    + "SOD.LATITUDE LATITUD_UBICACION, "
                    + "SOD.LONGITUDE LONGITUD_UBICACION, "
                    + "SOD.LOCATIONDESCRIPTION DESCRIPCION_UBICACION, "
                    + "V.ID_CLIENTE_DESTINO "
                    + "FROM "
                    + "VIAJES V "
                    + "LEFT JOIN TRANSPORTISTA T ON (V.ID_TRANSPORTISTA=T.ID_TRANSPORTISTA) "
                    + "LEFT JOIN DISPONIBILIDAD D ON (V.ID_TRANSPORTISTA=D.ID_TRANSPORTISTA AND V.ID_VEHICULO=D.ID_VEHICULO AND D.FECHA BETWEEN '" + dateFormat1.format(new Date()) + "' AND '" + dateFormat1.format(new Date()) + "') "
                    + "LEFT JOIN CABEZAL CD ON (D.ID_CABEZAL=CD.ID_CABEZAL) "
                    + "LEFT JOIN SMS_OPEN_DETALLE SOD ON (CD.IMEI=SOD.IMEI AND STR_TO_DATE(SOD.DATETIME_UBICACION, '%d-%m-%Y %H:%i:%s') BETWEEN '" + dateFormat1.format(new Date()) + " 00:00:00' AND '" + dateFormat1.format(new Date()) + " 23:59:59') "
                    + "WHERE "
                    + "V.FECHA_VIAJE BETWEEN '" + dateFormat1.format(new Date()) + "' AND '" + dateFormat1.format(new Date()) + "' AND "
                    + "V.ID_ESTADO_VIAJE NOT IN (5) AND "
                    // + "V.ESTADO='ACT' AND "
                    + "T.RASTREABLE=1 AND "
                    + "SOD.ID_SMS_OPEN IS NOT NULL";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                Long ID_PAIS = rs.getLong(1);
                Long ID_COMPANIA = rs.getLong(2);
                Long ID_PLANTA = rs.getLong(3);
                Long NUMERO_VIAJE = rs.getLong(4);
                String TIPO_ORDEN_VENTA = rs.getString(5);
                Long NUMERO_ORDEN_VENTA = rs.getLong(6);
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date FECHA_HORA = dateFormat2.parse(rs.getString(7));
                String IMEI = rs.getString(8);
                String LATITUDE = rs.getString(9);
                String LONGITUDE = rs.getString(10);
                String LOCATIONDESCRIPTION = rs.getString(11);
                Long ID_CLIENTE_DESTINO = rs.getLong(12);
                
                /* CONSUME API-GOOGLE-DISTANCE-MATRIX */
                String departure_time = control_base_datos.ObtenerString("SELECT A.VALOR FROM PARAMETROS_GPS A WHERE A.ID_PARAMETRO=3", conn);
                String origins = LATITUDE + "%2C" + LONGITUDE;
                String LATITUDE_DESTINO = control_base_datos.ObtenerString("SELECT FORMAT((A.ZONA_LATITUD_1 + A.ZONA_LATITUD_2 + A.ZONA_LATITUD_3 + A.ZONA_LATITUD_4 + A.ZONA_LATITUD_5) / 5, 6) AVG_LATITUD FROM CLIENTE_DESTINO A WHERE A.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                String LONGITUDE_DESTINO = control_base_datos.ObtenerString("SELECT FORMAT((A.ZONA_LONGITUD_1 + A.ZONA_LONGITUD_2 + A.ZONA_LONGITUD_3 + A.ZONA_LONGITUD_4 + A.ZONA_LONGITUD_5) / 5, 6) AVG_LONGITUD FROM CLIENTE_DESTINO A WHERE A.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                String destinations = LATITUDE_DESTINO + "%2C" + LONGITUDE_DESTINO;
                String key = control_base_datos.ObtenerString("SELECT A.VALOR FROM PARAMETROS_GPS A WHERE A.ID_PARAMETRO=2", conn);
                
                Cliente_Rest_Google_Maps cliente_rest_google_maps = new Cliente_Rest_Google_Maps();
                String json_result = cliente_rest_google_maps.distancematrix(departure_time, origins, destinations, key);
            
                Entidad.GoogleDistanceMatrix google_distance_matrix = null;
                try {
                    Type google_distance_matrix_type = new TypeToken<Entidad.GoogleDistanceMatrix>() {
                    }.getType();
                    google_distance_matrix = new Gson().fromJson(json_result, google_distance_matrix_type);
                } catch(JsonSyntaxException json_ex) {
                    System.out.println("ERROR GSON-CONVERT JSON-RESULTA: " + json_ex.toString());
                }
                
                String ETA_HORAS = "0.00";
                String EDA_KMS = "0.00";
                if(google_distance_matrix != null) {
                    try {
                        ETA_HORAS = google_distance_matrix.getRows().get(0).getElements().get(0).getDuration_in_traffic().getText();
                    } catch(Exception traffic_ex) {
                        ETA_HORAS = "0.0";
                        System.out.println("DURATION-IN-TRAFFIC-GET-TEXT");
                        System.out.println("JSON-RESULT: " + json_result);
                    }
                    try {
                        EDA_KMS = google_distance_matrix.getRows().get(0).getElements().get(0).getDistance().getText();
                    } catch(Exception traffic_ex) {
                        EDA_KMS = "0.0";
                        System.out.println("DISTANCE-GET-TEXT");
                        System.out.println("JSON-RESULT: " + json_result);
                    }
                }
                System.out.println("ETA_HORAS:" + ETA_HORAS);
                System.out.println("EDA_KMS:" + EDA_KMS);

                /* VALIDA SI LA UBICACIÓN YA EXISTE EN LA TABLA VIAJE_UBICACIONES */
                Boolean no_existe = true;
                cadenasql = "SELECT "
                        + "A.LATITUDE, A.LONGITUDE "
                        + "FROM "
                        + "VIAJE_UBICACIONES A "
                        + "WHERE "
                        + "A.ID_PAIS=" + ID_PAIS + " AND "
                        + "A.ID_COMPANIA=" + ID_COMPANIA + " AND "
                        + "A.ID_PLANTA=" + ID_PLANTA + " AND "
                        + "A.NUMERO_VIAJE=" + NUMERO_VIAJE + " AND "
                        + "A.TIPO_ORDEN_VENTA='" + TIPO_ORDEN_VENTA + "' AND "
                        + "A.NUMERO_ORDEN_VENTA=" + NUMERO_ORDEN_VENTA + " AND "
                        + "A.FECHA_HORA='" + dateFormat2.format(FECHA_HORA) + "' AND "
                        + "A.IMEI='" + IMEI + "'";
                Statement stmt1 = conn.createStatement();
                ResultSet rs1 = stmt1.executeQuery(cadenasql);
                while (rs1.next()) {
                    no_existe = false;
                }
                rs1.close();
                stmt1.close();

                if (no_existe) {
                    cadenasql = "INSERT INTO VIAJE_UBICACIONES ("
                            + "ID_PAIS, "
                            + "ID_COMPANIA, "
                            + "ID_PLANTA, "
                            + "NUMERO_VIAJE, "
                            + "TIPO_ORDEN_VENTA, "
                            + "NUMERO_ORDEN_VENTA, "
                            + "FECHA_HORA, "
                            + "IMEI, "
                            + "LATITUDE, "
                            + "LONGITUDE, "
                            + "LOCATIONDESCRIPTION, "
                            + "ETA_HORAS, "
                            + "EDA_KMS) VALUES ("
                            + ID_PAIS + ","
                            + ID_COMPANIA + ","
                            + ID_PLANTA + ","
                            + NUMERO_VIAJE + ",'"
                            + TIPO_ORDEN_VENTA + "',"
                            + NUMERO_ORDEN_VENTA + ",'"
                            + dateFormat2.format(FECHA_HORA) + "','"
                            + IMEI + "','"
                            + LATITUDE + "','"
                            + LONGITUDE + "','"
                            + LOCATIONDESCRIPTION + "','"
                            + ETA_HORAS + "','"
                            + EDA_KMS + "')";
                    stmt1 = conn.createStatement();
                    stmt1.executeUpdate(cadenasql);
                    stmt1.close();

                    this.validar_viajes_cerrados(ID_PAIS, ID_COMPANIA, ID_PLANTA, NUMERO_VIAJE, TIPO_ORDEN_VENTA, NUMERO_ORDEN_VENTA, ID_CLIENTE_DESTINO, conn);
                }
            }
            rs.close();
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

    private void validar_viajes_cerrados(Long ID_PAIS, Long ID_COMPANIA, Long ID_PLANTA, Long NUMERO_VIAJE, String TIPO_ORDEN_VENTA, Long NUMERO_ORDEN_VENTA, Long ID_CLIENTE_DESTINO, Connection conn) {
        try {
            String cadenasql = "SELECT "
                    + "A.LATITUDE, "
                    + "A.LONGITUDE, "
                    + "A.FECHA_HORA "
                    + "FROM "
                    + "VIAJE_UBICACIONES A "
                    + "WHERE "
                    + "A.ID_PAIS=" + ID_PAIS + " AND "
                    + "A.ID_COMPANIA=" + ID_COMPANIA + " AND "
                    + "A.ID_PLANTA=" + ID_PLANTA + " AND "
                    + "A.NUMERO_VIAJE=" + NUMERO_VIAJE + " AND "
                    + "A.TIPO_ORDEN_VENTA='" + TIPO_ORDEN_VENTA + "' AND "
                    + "A.NUMERO_ORDEN_VENTA=" + NUMERO_ORDEN_VENTA;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                Double LATITUDE = rs.getDouble(1);
                Double LONGITUDE = rs.getDouble(2);
                String FECHA_HORA = rs.getString(3);

                if (ID_CLIENTE_DESTINO != null) {
                    Control_Base_Datos control_base_datos = new Control_Base_Datos();
                    Double ZONA_LATITUD_1 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LATITUD_1,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LONGITUD_1 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LONGITUD_1,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LATITUD_2 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LATITUD_2,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LONGITUD_2 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LONGITUD_2,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LATITUD_3 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LATITUD_3,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LONGITUD_3 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LONGITUD_3,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LATITUD_4 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LATITUD_4,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LONGITUD_4 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LONGITUD_4,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LATITUD_5 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LATITUD_5,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LONGITUD_5 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LONGITUD_5,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);

                    Point geozona[] = {
                        new Point(ZONA_LATITUD_1, ZONA_LONGITUD_1),
                        new Point(ZONA_LATITUD_2, ZONA_LONGITUD_2),
                        new Point(ZONA_LATITUD_3, ZONA_LONGITUD_3),
                        new Point(ZONA_LATITUD_4, ZONA_LONGITUD_4),
                        new Point(ZONA_LATITUD_5, ZONA_LONGITUD_5)
                    };

                    Point ubicacion_actual = new Point(LATITUDE, LONGITUDE);

                    Poligono poligono = new Poligono();
                    if (poligono.isInside(geozona, 5, ubicacion_actual)) {
                        System.out.println("GEO-ZONA: {"
                                + "(" + ZONA_LATITUD_1 + "," + ZONA_LONGITUD_1 + ");"
                                + "(" + ZONA_LATITUD_2 + "," + ZONA_LONGITUD_2 + ");"
                                + "(" + ZONA_LATITUD_3 + "," + ZONA_LONGITUD_3 + ");"
                                + "(" + ZONA_LATITUD_4 + "," + ZONA_LONGITUD_4 + ");"
                                + "(" + ZONA_LATITUD_5 + "," + ZONA_LONGITUD_5 + ")"
                                + "}");
                        System.out.println("UBICACIÓN-ACTUAL: {(" + LATITUDE + "," + LONGITUDE + ")}");

                        cadenasql = "UPDATE "
                                + "VIAJES "
                                + "SET "
                                + "ID_ESTADO_VIAJE=5, "
                                + "ESTADO='TER', "
                                + "FECHA_HORA_TERMINADO='" + FECHA_HORA + "' "
                                + "WHERE "
                                + "ID_PAIS=" + ID_PAIS + " AND "
                                + "ID_COMPANIA=" + ID_COMPANIA + " AND "
                                + "ID_PLANTA=" + ID_PLANTA + " AND "
                                + "NUMERO_VIAJE=" + NUMERO_VIAJE + " AND "
                                + "TIPO_ORDEN_VENTA='" + TIPO_ORDEN_VENTA + "' AND "
                                + "NUMERO_ORDEN_VENTA=" + NUMERO_ORDEN_VENTA;
                        Statement stmt1 = conn.createStatement();
                        System.out.println("CADENASQL-CERRAR-VIAJE: " + cadenasql);
                        stmt1.executeUpdate(cadenasql);
                        stmt1.close();
                    }
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println("PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:validar_viajes_cerrados()|ERROR:" + ex.toString());
        }
    }
    
    public String distancematrix(String departure_time, String origins, String destinations, String key) {
        String resultado = "";
        try {
            Cliente_Rest_Google_Maps cliente_rest_google_maps = new Cliente_Rest_Google_Maps();
            String json_result = cliente_rest_google_maps.distancematrix(departure_time, origins, destinations, key);
            
            Type google_distance_matrix_type = new TypeToken<Entidad.GoogleDistanceMatrix>() {
            }.getType();
            Entidad.GoogleDistanceMatrix google_distance_matrix = new Gson().fromJson(json_result, google_distance_matrix_type);
            
            Gson gson = new GsonBuilder().serializeNulls().create();
            resultado = gson.toJson(google_distance_matrix);
        } catch (Exception ex) {
            System.out.println("PROYECTO:client-rest-sms|CLASE:" + this.getClass().getName() + "|METODO:distancematrix()|ERROR:" + ex.toString());
        }
        
        return resultado;
    }

}
