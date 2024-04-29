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
import java.util.Calendar;
import java.util.List;

public class Ctrl_SHARE_SERVICE implements Serializable {

    private static final long serialVersionUID = 1L;

    public Ctrl_SHARE_SERVICE() {
    }

    public String HistoyDataLastLocationByUser() {
        String resultado = "";

        Connection conn = null;

        try {
            String xml_request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:shar=\"http://shareservice.co/\">"
                    + "<soapenv:Header/>"
                    + "<soapenv:Body>"
                    + "<shar:HistoyDataLastLocationByUser>"
                    + "<shar:sLogin>unopetrol</shar:sLogin>"
                    + "<shar:sPassword>unopetrol</shar:sPassword>"
                    + "</shar:HistoyDataLastLocationByUser>"
                    + "</soapenv:Body>"
                    + "</soapenv:Envelope>";

            ClienteRest.Cliente_Rest_SHARE_SERVICE cliente_rest_share_service = new ClienteRest.Cliente_Rest_SHARE_SERVICE();
            String response_cliente_rest_share_service = cliente_rest_share_service.HistoyDataLastLocationByUser(xml_request);
            response_cliente_rest_share_service = response_cliente_rest_share_service.replaceAll("xmlns=\"http://shareservice.co/\"", "");
            response_cliente_rest_share_service = response_cliente_rest_share_service.replaceAll("xmlns=\"\"", "");

            SOAPMessage soap_response = MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(response_cliente_rest_share_service.getBytes("UTF-8")));
            Unmarshaller unmarshaller = JAXBContext.newInstance(Entidad.SHARE_SERVICE.HistoyDataLastLocationByUserResponse.class).createUnmarshaller();
            Entidad.SHARE_SERVICE.HistoyDataLastLocationByUserResponse histoy_data_last_location_by_user_response = (Entidad.SHARE_SERVICE.HistoyDataLastLocationByUserResponse) unmarshaller.unmarshal(soap_response.getSOAPBody().extractContentAsDocument());

            List<Entidad.SHARE_SERVICE.Plate> lista_plate = histoy_data_last_location_by_user_response.getHistoyDataLastLocationByUserResult().getSpace().getResponse().getPlates();

            List<Entidad.SHARE_SERVICE.Plate> lista_plate_temp = new ArrayList<>();
            for (Integer i = 0; i < lista_plate.size(); i++) {
                
                Calendar fecha_consulta_inicial = Calendar.getInstance();
                fecha_consulta_inicial.add(Calendar.HOUR, -1);

                Calendar fecha_consulta_final = Calendar.getInstance();
                fecha_consulta_final.add(Calendar.HOUR, 1);

                Calendar fecha_registro_ws = Calendar.getInstance();
                SimpleDateFormat dateFormat_share = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                fecha_registro_ws.setTime(dateFormat_share.parse(lista_plate.get(i).getHst().getDateTimeGPS()));

                SimpleDateFormat dateFormat_temp = new SimpleDateFormat("yyyyMMddHHmmss");

                Long fechaInicial = Long.valueOf(dateFormat_temp.format(fecha_consulta_inicial.getTime()));
                Long fechaFinal = Long.valueOf(dateFormat_temp.format(fecha_consulta_final.getTime()));
                Long fechaRegistro = Long.valueOf(dateFormat_temp.format(fecha_registro_ws.getTime()));

                if((fechaRegistro >= fechaInicial) && (fechaRegistro <= fechaFinal)) {
                    Entidad.SHARE_SERVICE.Plate plate = lista_plate.get(i);
                    lista_plate_temp.add(plate);
                }
            }
            
            List<Entidad.SHARE_SERVICE.Plate> lista_plate_temp_2 = new ArrayList<>();
            for (Integer i = 0; i < lista_plate_temp.size(); i++) {
                Boolean existe = false;
                for(Integer j = 0; j > lista_plate_temp_2.size(); j++) {
                    if(lista_plate_temp.get(i).getId().equals(lista_plate_temp_2.get(j).getId()) && lista_plate_temp.get(i).getHst().getDateTimeGPS().equals(lista_plate_temp_2.get(j).getHst().getDateTimeGPS()) && lista_plate_temp.get(i).getHst().getLatitude().equals(lista_plate_temp_2.get(j).getHst().getLatitude()) && lista_plate_temp.get(i).getHst().getLongitude().equals(lista_plate_temp_2.get(j).getHst().getLongitude())) {
                        existe = true;
                    }
                }
                if(!existe) {
                    lista_plate_temp_2.add(lista_plate_temp.get(i));
                }
            }

            lista_plate = lista_plate_temp_2;

            histoy_data_last_location_by_user_response.getHistoyDataLastLocationByUserResult().getSpace().getResponse().setPlates(lista_plate_temp_2);

            Control_Base_Datos control_base_datos = new Control_Base_Datos();
            conn = control_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            /* String sql = "DELETE FROM SHARE_SERVICE_DETALLE WHERE ID_SHARE_SERVICE > 0";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close(); */

            Long ID_SHARE_SERVICE = control_base_datos.ObtenerLong("SELECT IFNULL(MAX(A.ID_SHARE_SERVICE),0)+1 MAX_ID FROM SHARE_SERVICE_ENCABEZADO A", conn);

            String sql = "INSERT INTO SHARE_SERVICE_ENCABEZADO (ID_SHARE_SERVICE, FECHA_ACTUALIZACION, NUMERO_UBICACIONES) VALUES ("
                    + ID_SHARE_SERVICE + ","
                    + "CURRENT_TIMESTAMP" + ","
                    + "0" + ")";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            Integer CORRELATIVO = 0;
            for (Integer i = 0; i < lista_plate.size(); i++) {
                CORRELATIVO++;

                sql = "INSERT INTO SHARE_SERVICE_DETALLE ("
                        + "ID_SHARE_SERVICE, "
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
                        + ID_SHARE_SERVICE + ","
                        + CORRELATIVO + ",'"
                        + lista_plate.get(i).getId() + "','"
                        + lista_plate.get(i).getId() + "','"
                        + lista_plate.get(i).getHst().getOdometer() + "','"
                        + lista_plate.get(i).getHst().getLatitude() + "','"
                        + lista_plate.get(i).getHst().getLongitude() + "','"
                        + lista_plate.get(i).getHst().getDateTimeGPS() + "','"
                        + lista_plate.get(i).getHst().getSpeed() + "','"
                        + lista_plate.get(i).getHst().getSpeed() + "','"
                        + lista_plate.get(i).getHst().getHeading() + "','"
                        + lista_plate.get(i).getHst().getLocation() + "','"
                        + lista_plate.get(i).getHst().getId() + "','"
                        + lista_plate.get(i).getHst().getId() + "','"
                        + lista_plate.get(i).getHst().getIgnition() + "','"
                        + lista_plate.get(i).getHst().getDateTimeServer() + "','"
                        + lista_plate.get(i).getHst().getLocation() + "',"
                        + "CURRENT_TIMESTAMP" + ")";
                stmt = conn.createStatement();
                // System.out.println("SQL: " + sql);
                stmt.executeUpdate(sql);
                stmt.close();
            }

            sql = "UPDATE SHARE_SERVICE_ENCABEZADO SET NUMERO_UBICACIONES=" + CORRELATIVO + " WHERE ID_SHARE_SERVICE=" + ID_SHARE_SERVICE;
            stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            sql = "SELECT " 
                    + "STR_TO_DATE(A.DATETIME_UBICACION, '%Y/%m/%d %H:%i:%s') FECHA_HORA_UBICACION, " 
                    + "A.IMEI IMEI, " 
                    + "A.LATITUDE LATITUD_UBICACION, " 
                    + "A.LONGITUDE LONGITUD_UBICACION, " 
                    + "A.LOCATIONDESCRIPTION DESCRIPCION_UBICACION " 
                    + "FROM "
                    + "SHARE_SERVICE_DETALLE A";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String GPS_FECHA_HORA_UBICACION = rs.getString(1);
                String GPS_IMEI = rs.getString(2);
                String GPS_LATITUD_UBICACION = rs.getString(3);
                String GPS_LONGITUD_UBICACION = rs.getString(4);
                String GPS_DESCRIPCION_UBICACION = rs.getString(5);

                Long ID_CABEZAL = control_base_datos.ObtenerLong("SELECT A.ID_CABEZAL FROM CABEZAL A WHERE A.IMEI='" + GPS_IMEI + "'", conn);
                if (ID_CABEZAL == null) {
                    ID_CABEZAL = Long.valueOf("0");
                }

                sql = "SELECT DISTINCT " 
                        + "V.ID_PAIS, " 
                        + "V.ID_COMPANIA, " 
                        + "V.ID_PLANTA, " 
                        + "V.NUMERO_VIAJE, " 
                        + "V.TIPO_ORDEN_VENTA, " 
                        + "V.NUMERO_ORDEN_VENTA, " 
                        + "V.ID_CLIENTE_DESTINO " 
                        + "FROM " 
                        + "VIAJES V " 
                        + "LEFT JOIN DISPONIBILIDAD D ON (V.ID_TRANSPORTISTA=D.ID_TRANSPORTISTA AND V.ID_VEHICULO=D.ID_VEHICULO AND V.FECHA_VIAJE=D.FECHA) " 
                        + "WHERE " 
                        + "(V.ID_ESTADO_VIAJE NOT IN (2, 5, 10)) AND " 
                        + "(V.ID_TRANSPORTISTA IN (1, 18)) AND "
                        + "(D.ID_CABEZAL=" + ID_CABEZAL + ")";
                Statement stmt1 = conn.createStatement();
                ResultSet rs1 = stmt1.executeQuery(sql);
                while (rs1.next()) {
                    Long ID_PAIS = rs1.getLong(1);
                    Long ID_COMPANIA = rs1.getLong(2);
                    Long ID_PLANTA = rs1.getLong(3);
                    Long NUMERO_VIAJE = rs1.getLong(4);
                    String TIPO_ORDEN_VENTA = rs1.getString(5);
                    Long NUMERO_ORDEN_VENTA = rs1.getLong(6);
                    // Long ID_CLIENTE_DESTINO = rs1.getLong(7);
                    String ETA_HORAS = "0.00";
                    String EDA_KMS = "0.00";

                    try {
                        sql = "INSERT INTO VIAJE_UBICACIONES_SHARE_SERVICE ("
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
                                + GPS_FECHA_HORA_UBICACION + "','"
                                + GPS_IMEI + "','"
                                + GPS_LATITUD_UBICACION + "','"
                                + GPS_LONGITUD_UBICACION + "','"
                                + GPS_DESCRIPCION_UBICACION + "','"
                                + ETA_HORAS + "','"
                                + EDA_KMS + "')";
                        Statement stmt2 = conn.createStatement();
                        // System.out.println("SQL: " + sql);
                        stmt2.executeUpdate(sql);
                        stmt2.close();
                    } catch (Exception ex) {
                        // System.out.println("SHARE-SERVICE: UBICACION YA EXISTE." + ex.toString());
                    }

                    // this.validar_viajes_cerrados(ID_PAIS, ID_COMPANIA, ID_PLANTA, NUMERO_VIAJE, TIPO_ORDEN_VENTA, NUMERO_ORDEN_VENTA, ID_CLIENTE_DESTINO, conn);

                    sql = "DELETE FROM SHARE_SERVICE_DETALLE WHERE "
                            + "STR_TO_DATE(DATETIME_UBICACION, '%Y/%m/%d %H:%i:%s')='" + GPS_FECHA_HORA_UBICACION + "' AND " 
                            + "IMEI='" + GPS_IMEI + "' AND " 
                            + "LATITUDE='" + GPS_LATITUD_UBICACION + "' AND " 
                            + "LONGITUDE='" + GPS_LONGITUD_UBICACION + "'";
                    Statement stmt2 = conn.createStatement();
                    // System.out.println("SQL: " + sql);
                    stmt2.executeUpdate(sql);
                    stmt2.close();
                }
                rs1.close();
                stmt1.close();
            }
            rs.close();
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            Gson gson = new GsonBuilder().serializeNulls().create();
            resultado = gson.toJson(histoy_data_last_location_by_user_response);
            // resultado = gson.toJson(response_cliente_rest_share_service);
        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;
                }
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:HistoyDataLastLocationByUser()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:HistoyDataLastLocationByUser()|ERROR:" + ex.toString());
            } catch (Exception ex1) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:HistoyDataLastLocationByUser()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:HistoyDataLastLocationByUser()|ERROR:" + ex.toString());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:HistoyDataLastLocationByUser()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:HistoyDataLastLocationByUser()|ERROR:" + ex.toString());
            }
        }

        return resultado;
    }

}
