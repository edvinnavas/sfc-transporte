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

public class Ctrl_TRAMAQ implements Serializable {

    private static final long serialVersionUID = 1L;

    public Ctrl_TRAMAQ() {
    }

    public String GetLocationList() {
        String resultado = "";

        Connection conn = null;

        try {
            SimpleDateFormat dateFormat_ws = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            Calendar StartUtc = Calendar.getInstance();
            StartUtc.add(Calendar.MINUTE, -30);
            
            Calendar EndUtc = Calendar.getInstance();

            String xml_request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsdl=\"http://tramaq.stgands.com/wsdl\">"
                    + "<soapenv:Header/>" 
                    + "<soapenv:Body>" 
                    + "<wsdl:GetLocationList>" 
                    + "<wsdl:StartUtc>" + dateFormat_ws.format(StartUtc.getTime()) + "</wsdl:StartUtc>" 
                    + "<wsdl:EndUtc>" + dateFormat_ws.format(EndUtc.getTime()) + "</wsdl:EndUtc>" 
                    + "</wsdl:GetLocationList>" 
                    + "</soapenv:Body>" 
                    + "</soapenv:Envelope>";

            ClienteRest.Cliente_Rest_TRAMAQ cliente_rest_tramaq = new ClienteRest.Cliente_Rest_TRAMAQ();
            String response_cliente_rest_tramaq = cliente_rest_tramaq.GetLocationList(xml_request);

            response_cliente_rest_tramaq = response_cliente_rest_tramaq.replaceAll("xmlns=\"http://tramaq.stgands.com/wsdl\"", "");

            SOAPMessage soap_response = MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(response_cliente_rest_tramaq.getBytes("UTF-8")));
            Unmarshaller unmarshaller = JAXBContext.newInstance(Entidad.TRAMAQ.GetLocationListResponse.class).createUnmarshaller();
            Entidad.TRAMAQ.GetLocationListResponse get_location_list_response = (Entidad.TRAMAQ.GetLocationListResponse) unmarshaller.unmarshal(soap_response.getSOAPBody().extractContentAsDocument());

            List<Entidad.TRAMAQ.Location> lista_locations = get_location_list_response.getLocations();

            List<Entidad.TRAMAQ.Location> lista_locations_temp = new ArrayList<>();
            for (Integer i = 0; i < lista_locations.size(); i++) {
                
                Calendar fecha_consulta_inicial = Calendar.getInstance();
                fecha_consulta_inicial.add(Calendar.HOUR, -1);

                Calendar fecha_consulta_final = Calendar.getInstance();
                fecha_consulta_final.add(Calendar.HOUR, 1);

                Calendar fecha_registro_ws = Calendar.getInstance();
                SimpleDateFormat dateFormat_sms = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                fecha_registro_ws.setTime(dateFormat_sms.parse(lista_locations.get(i).getDateTime()));

                SimpleDateFormat dateFormat_temp = new SimpleDateFormat("yyyyMMddHHmmss");

                Long fechaInicial = Long.valueOf(dateFormat_temp.format(fecha_consulta_inicial.getTime()));
                Long fechaFinal = Long.valueOf(dateFormat_temp.format(fecha_consulta_final.getTime()));
                Long fechaRegistro = Long.valueOf(dateFormat_temp.format(fecha_registro_ws.getTime()));

                if((fechaRegistro >= fechaInicial) && (fechaRegistro <= fechaFinal)) {
                    Entidad.TRAMAQ.Location location = lista_locations.get(i);
                    lista_locations_temp.add(location);
                }
            }
            
            List<Entidad.TRAMAQ.Location> lista_locations_temp_2 = new ArrayList<>();
            for (Integer i = 0; i < lista_locations_temp.size(); i++) {
                Boolean existe = false;
                for(Integer j = 0; j > lista_locations_temp_2.size(); j++) {
                    if(lista_locations_temp.get(i).getIMEI().equals(lista_locations_temp_2.get(j).getIMEI()) && lista_locations_temp.get(i).getDateTime().equals(lista_locations_temp_2.get(j).getDateTime()) && lista_locations_temp.get(i).getLatitude().equals(lista_locations_temp_2.get(j).getLatitude()) && lista_locations_temp.get(i).getLongitude().equals(lista_locations_temp_2.get(j).getLongitude())) {
                        existe = true;
                    }
                }
                if(!existe) {
                    lista_locations_temp_2.add(lista_locations_temp.get(i));
                }
            }

            lista_locations = lista_locations_temp_2;

            Control_Base_Datos control_base_datos = new Control_Base_Datos();
            conn = control_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            /* String sql = "DELETE FROM TRAMAQ_DETALLE WHERE ID_TRAMAQ > 0";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close(); */

            Long ID_TRAMAQ = control_base_datos.ObtenerLong("SELECT IFNULL(MAX(A.ID_TRAMAQ),0)+1 MAX_ID FROM TRAMAQ_ENCABEZADO A", conn);

            String sql = "INSERT INTO TRAMAQ_ENCABEZADO (ID_TRAMAQ, FECHA_ACTUALIZACION, NUMERO_UBICACIONES) VALUES ("
                    + ID_TRAMAQ + ","
                    + "CURRENT_TIMESTAMP" + ","
                    + "0" + ")";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            Integer CORRELATIVO = 0;
            for (Integer i = 0; i < lista_locations.size(); i++) {
                CORRELATIVO++;

                sql = "INSERT INTO TRAMAQ_DETALLE ("
                        + "ID_TRAMAQ, "
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
                        + ID_TRAMAQ + ","
                        + CORRELATIVO + ",'"
                        + lista_locations.get(i).getName() + "','"
                        + lista_locations.get(i).getIMEI() + "','"
                        + lista_locations.get(i).getOdometer() + "','"
                        + lista_locations.get(i).getLatitude() + "','"
                        + lista_locations.get(i).getLongitude() + "','"
                        + lista_locations.get(i).getDateTime().replaceAll("T", " ") + "','"
                        + lista_locations.get(i).getSpeed() + "','"
                        + lista_locations.get(i).getSpeedMeasure() + "','"
                        + lista_locations.get(i).getHeading() + "','"
                        + lista_locations.get(i).getLocationDescription() + "','"
                        + lista_locations.get(i).getDriverName() + "','"
                        + lista_locations.get(i).getDriverCode() + "','"
                        + lista_locations.get(i).getIgnition() + "','"
                        + lista_locations.get(i).getDateUtc().replaceAll("T", " ") + "','"
                        + lista_locations.get(i).getAddress() + "',"
                        + "CURRENT_TIMESTAMP" + ")";
                stmt = conn.createStatement();
                // System.out.println("SQL: " + sql);
                stmt.executeUpdate(sql);
                stmt.close();
            }

            sql = "UPDATE TRAMAQ_ENCABEZADO SET NUMERO_UBICACIONES=" + CORRELATIVO + " WHERE ID_TRAMAQ=" + ID_TRAMAQ;
            stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            sql = "SELECT " 
                    + "STR_TO_DATE(A.DATETIME_UBICACION, '%Y-%m-%d %H:%i:%s') FECHA_HORA_UBICACION, " 
                    + "A.IMEI IMEI, " 
                    + "A.LATITUDE LATITUD_UBICACION, " 
                    + "A.LONGITUDE LONGITUD_UBICACION, " 
                    + "A.LOCATIONDESCRIPTION DESCRIPCION_UBICACION " 
                    + "FROM "
                    + "TRAMAQ_DETALLE A";
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
                        + "(V.ID_TRANSPORTISTA IN (4)) AND "
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
                        sql = "INSERT INTO VIAJE_UBICACIONES_TRAMAQ ("
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
                        // System.out.println("TRAMAQ: UBICACION YA EXISTE." + ex.toString());
                    }

                    // this.validar_viajes_cerrados(ID_PAIS, ID_COMPANIA, ID_PLANTA, NUMERO_VIAJE, TIPO_ORDEN_VENTA, NUMERO_ORDEN_VENTA, ID_CLIENTE_DESTINO, conn);

                    sql = "DELETE FROM TRAMAQ_DETALLE WHERE "
                            + "STR_TO_DATE(DATETIME_UBICACION, '%Y-%m-%d %H:%i:%s')='" + GPS_FECHA_HORA_UBICACION + "' AND " 
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
            resultado = gson.toJson(get_location_list_response);
        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;
                }
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:GetLocationList()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:GetLocationList()|ERROR:" + ex.toString());
            } catch (Exception ex1) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:GetLocationList()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:GetLocationList()|ERROR:" + ex.toString());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:GetLocationList()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:GetLocationList()|ERROR:" + ex.toString());
            }
        }

        return resultado;
    }

}
