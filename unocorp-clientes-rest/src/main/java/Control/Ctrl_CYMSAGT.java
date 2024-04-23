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

public class Ctrl_CYMSAGT implements Serializable {

    private static final long serialVersionUID = 1L;

    public Ctrl_CYMSAGT() {
    }

    public String GetLocationList() {
        String resultado = "";

        Connection conn = null;

        try {
            SimpleDateFormat dateFormat_ws = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            Calendar StartUtc = Calendar.getInstance();
            StartUtc.add(Calendar.MINUTE, -30);
            
            Calendar EndUtc = Calendar.getInstance();

            String xml_request = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:service_simiex\">"
                    + "<soapenv:Header/>" 
                    + "<soapenv:Body>" 
                    + "<urn:GetLocationList soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
                    + "<parameters xsi:type=\"urn:SendUTC\">" 
                    + "<StartUtc xsi:type=\"xsd:string\">" + dateFormat_ws.format(StartUtc.getTime()) + "</StartUtc>" 
                    + "<EndUtc xsi:type=\"xsd:string\">" + dateFormat_ws.format(EndUtc.getTime()) + "</EndUtc>" 
                    + "</parameters>" 
                    + "</urn:GetLocationList>" 
                    + "</soapenv:Body>" 
                    + "</soapenv:Envelope>";

            ClienteRest.Cliente_Rest_CYMSAGT cliente_rest_cymsagt = new ClienteRest.Cliente_Rest_CYMSAGT();
            String response_cliente_rest_cymsagt = cliente_rest_cymsagt.GetLocationList(xml_request);
            response_cliente_rest_cymsagt = response_cliente_rest_cymsagt.replaceAll(" xsi:type=\"tns:Location\"", "");
            response_cliente_rest_cymsagt = response_cliente_rest_cymsagt.replaceAll(" xsi:type=\"xsd:string\"", "");
            response_cliente_rest_cymsagt = response_cliente_rest_cymsagt.replaceAll(" xsi:type=\"xsd:float\"", "");
            response_cliente_rest_cymsagt = response_cliente_rest_cymsagt.replaceAll(" xsi:type=\"xsd:dateTime\"", "");
            // response_cliente_rest_cymsagt = response_cliente_rest_cymsagt.replaceAll(" xsi:type=\"SOAP-ENC:Array\" SOAP-ENC:arrayType=\"tns:Location[2727]\"", "");

            SOAPMessage soap_response = MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(response_cliente_rest_cymsagt.getBytes("UTF-8")));
            Unmarshaller unmarshaller = JAXBContext.newInstance(Entidad.CYMSAGT.GetLocationListResponse.class).createUnmarshaller();
            Entidad.CYMSAGT.GetLocationListResponse get_location_list_response = (Entidad.CYMSAGT.GetLocationListResponse) unmarshaller.unmarshal(soap_response.getSOAPBody().extractContentAsDocument());

            List<Entidad.CYMSAGT.Item> lista_items = get_location_list_response.getItems();

            List<Entidad.CYMSAGT.Item> lista_items_temp = new ArrayList<>();
            for (Integer i = 0; i < lista_items.size(); i++) {
                
                Calendar fecha_consulta_inicial = Calendar.getInstance();
                fecha_consulta_inicial.add(Calendar.HOUR, -1);

                Calendar fecha_consulta_final = Calendar.getInstance();
                fecha_consulta_final.add(Calendar.HOUR, 1);

                Calendar fecha_registro_ws = Calendar.getInstance();
                SimpleDateFormat dateFormat_sms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                fecha_registro_ws.setTime(dateFormat_sms.parse(lista_items.get(i).getDateTime()));

                SimpleDateFormat dateFormat_temp = new SimpleDateFormat("yyyyMMddHHmmss");

                Long fechaInicial = Long.valueOf(dateFormat_temp.format(fecha_consulta_inicial.getTime()));
                Long fechaFinal = Long.valueOf(dateFormat_temp.format(fecha_consulta_final.getTime()));
                Long fechaRegistro = Long.valueOf(dateFormat_temp.format(fecha_registro_ws.getTime()));

                if((fechaRegistro >= fechaInicial) && (fechaRegistro <= fechaFinal)) {
                    Entidad.CYMSAGT.Item item = lista_items.get(i);
                    lista_items_temp.add(item);
                }
            }
            
            List<Entidad.CYMSAGT.Item> lista_items_temp_2 = new ArrayList<>();
            for (Integer i = 0; i < lista_items_temp.size(); i++) {
                Boolean existe = false;
                for(Integer j = 0; j > lista_items_temp_2.size(); j++) {
                    if(lista_items_temp.get(i).getImei().equals(lista_items_temp_2.get(j).getImei()) && lista_items_temp.get(i).getDateTime().equals(lista_items_temp_2.get(j).getDateTime()) && lista_items_temp.get(i).getLatitude().equals(lista_items_temp_2.get(j).getLatitude()) && lista_items_temp.get(i).getLongitude().equals(lista_items_temp_2.get(j).getLongitude())) {
                        existe = true;
                    }
                }
                if(!existe) {
                    lista_items_temp_2.add(lista_items_temp.get(i));
                }
            }

            lista_items = lista_items_temp_2;

            get_location_list_response.setItems(lista_items);

            Control_Base_Datos control_base_datos = new Control_Base_Datos();
            conn = control_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            /* String sql = "DELETE FROM CYMSAGT_DETALLE WHERE ID_CYMSAGT > 0";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close(); */

            Long ID_CYMSAGT = control_base_datos.ObtenerLong("SELECT IFNULL(MAX(A.ID_CYMSAGT),0)+1 MAX_ID FROM CYMSAGT_ENCABEZADO A", conn);

            String sql = "INSERT INTO CYMSAGT_ENCABEZADO (ID_CYMSAGT, FECHA_ACTUALIZACION, NUMERO_UBICACIONES) VALUES ("
                    + ID_CYMSAGT + ","
                    + "CURRENT_TIMESTAMP" + ","
                    + "0" + ")";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            Integer CORRELATIVO = 0;
            for (Integer i = 0; i < lista_items.size(); i++) {
                CORRELATIVO++;

                sql = "INSERT INTO CYMSAGT_DETALLE ("
                        + "ID_CYMSAGT, "
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
                        + ID_CYMSAGT + ","
                        + CORRELATIVO + ",'"
                        + lista_items.get(i).getName() + "','"
                        + lista_items.get(i).getImei() + "','"
                        + lista_items.get(i).getOdometer() + "','"
                        + lista_items.get(i).getLatitude() + "','"
                        + lista_items.get(i).getLongitude() + "','"
                        + lista_items.get(i).getDateTime() + "','"
                        + lista_items.get(i).getSpeed() + "','"
                        + lista_items.get(i).getSpeedMeasure() + "','"
                        + lista_items.get(i).getHeading() + "','"
                        + lista_items.get(i).getLocationDescription() + "','"
                        + lista_items.get(i).getDriverName() + "','"
                        + lista_items.get(i).getDriverCode() + "','"
                        + lista_items.get(i).getIgnition() + "','"
                        + lista_items.get(i).getDateUtc() + "','"
                        + lista_items.get(i).getAddress() + "',"
                        + "CURRENT_TIMESTAMP" + ")";
                stmt = conn.createStatement();
                // System.out.println("SQL: " + sql);
                stmt.executeUpdate(sql);
                stmt.close();
            }

            sql = "UPDATE CYMSAGT_ENCABEZADO SET NUMERO_UBICACIONES=" + CORRELATIVO + " WHERE ID_CYMSAGT=" + ID_CYMSAGT;
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
                    + "CYMSAGT_DETALLE A";
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
                        + "(V.ID_TRANSPORTISTA IN (28, 30)) AND "
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
                        sql = "INSERT INTO VIAJE_UBICACIONES_CYMSAGT ("
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
                        // System.out.println("CYMSAGT: UBICACION YA EXISTE." + ex.toString());
                    }

                    // this.validar_viajes_cerrados(ID_PAIS, ID_COMPANIA, ID_PLANTA, NUMERO_VIAJE, TIPO_ORDEN_VENTA, NUMERO_ORDEN_VENTA, ID_CLIENTE_DESTINO, conn);

                    sql = "DELETE FROM CYMSAGT_DETALLE WHERE "
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
