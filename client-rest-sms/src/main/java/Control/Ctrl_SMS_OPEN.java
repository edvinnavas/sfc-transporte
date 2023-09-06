package Control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
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
                    + "SOD.LOCATIONDESCRIPTION DESCRIPCION_UBICACION "
                    + "FROM "
                    + "VIAJES V "
                    + "LEFT JOIN TRANSPORTISTA T ON (V.ID_TRANSPORTISTA=T.ID_TRANSPORTISTA) "
                    + "LEFT JOIN DISPONIBILIDAD D ON (V.ID_TRANSPORTISTA=D.ID_TRANSPORTISTA AND V.ID_VEHICULO=D.ID_VEHICULO AND D.FECHA BETWEEN '" + dateFormat1.format(new Date()) + "' AND '" + dateFormat1.format(new Date()) + "') "
                    + "LEFT JOIN CABEZAL CD ON (D.ID_CABEZAL=CD.ID_CABEZAL) "
                    + "LEFT JOIN SMS_OPEN_DETALLE SOD ON (CD.IMEI=SOD.IMEI AND STR_TO_DATE(SOD.DATETIME_UBICACION, '%d-%m-%Y %H:%i:%s') BETWEEN '" + dateFormat1.format(new Date()) + " 00:00:00' AND '" + dateFormat1.format(new Date()) + " 23:59:59') "
                    + "WHERE "
                    + "V.FECHA_VIAJE BETWEEN '" + dateFormat1.format(new Date()) + "' AND '" + dateFormat1.format(new Date()) + "' AND "
                    + "V.ESTADO='ACT' AND "
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
                Double ETA_HORAS = 0.00;
                Double EDA_KMS = 0.00;

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
                            + LOCATIONDESCRIPTION + "',"
                            + ETA_HORAS + ","
                            + EDA_KMS + ")";
                    stmt1 = conn.createStatement();
                    stmt1.executeUpdate(cadenasql);
                    stmt1.close();
                    
                    String query_get_id_cliente_destino = "SELECT "
                            + "V.ID_CLIENTE_DESTINO "
                            + "FROM "
                            + "VIAJES V "
                            + "WHERE "
                            + "V.ID_PAIS=" + ID_PAIS + " AND "
                            + "V.ID_COMPANIA=" + ID_COMPANIA + " AND "
                            + "V.ID_PLANTA=" + ID_PLANTA + " AND "
                            + "V.NUMERO_VIAJE=" + NUMERO_VIAJE;
                    Long ID_CLIENTE_DESTINO = control_base_datos.ObtenerLong(query_get_id_cliente_destino, conn);

                    if (ID_CLIENTE_DESTINO != null) {
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

                        Point ubicacion_actual = new Point(Double.valueOf(LATITUDE), Double.valueOf(LONGITUDE));

                        Poligono poligono = new Poligono();
                        if (poligono.isInside(geozona, 5, ubicacion_actual)) {
                            System.out.println("GEO-ZONA: {"
                                    + "(" + ZONA_LATITUD_1 + "," + ZONA_LONGITUD_1 + ");"
                                    + "(" + ZONA_LATITUD_2 + "," + ZONA_LONGITUD_2 + ");"
                                    + "(" + ZONA_LATITUD_3 + "," + ZONA_LONGITUD_3 + ");"
                                    + "(" + ZONA_LATITUD_4 + "," + ZONA_LONGITUD_4 + ");"
                                    + "(" + ZONA_LATITUD_5 + "," + ZONA_LONGITUD_5 + ")"
                                    + "}");
                            System.out.println("UBICACIÓN-ACTUAL: {(" + Double.valueOf(LATITUDE) + "," + Double.valueOf(LONGITUDE) + ")}");
                            
                            cadenasql = "UPDATE VIAJES SET ID_ESTADO_VIAJE=5, ESTADO='TER' WHERE ID_PAIS=" + ID_PAIS + " AND ID_COMPANIA=" + ID_COMPANIA + " AND ID_PLANTA=" + ID_PLANTA + " AND NUMERO_VIAJE=" + NUMERO_VIAJE;
                            stmt1 = conn.createStatement();
                            System.out.println("CADENASQL-CERRAR-VIAJE: " + cadenasql);
                            stmt1.executeUpdate(cadenasql);
                            stmt1.close();
                        }
                    }
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

}
