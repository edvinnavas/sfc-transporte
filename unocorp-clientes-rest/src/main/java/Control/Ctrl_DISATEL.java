package Control;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;

public class Ctrl_DISATEL implements Serializable {

    private static final long serialVersionUID = 1L;

    public Ctrl_DISATEL() {
    }

    public String ListaVehiculos() {
        String resultado = "";

        Connection conn = null;

        try {
            String xml_request = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://srv.disatelavl.net/ws/\">"
                    + "<soapenv:Header/>"
                    + "<soapenv:Body>"
                    + "<ws:ListaVehiculos soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
                    + "<country xsi:type=\"xsd:string\">NI</country>"
                    + "<usr xsi:type=\"xsd:string\">TH1</usr>"
                    + "<pwd xsi:type=\"xsd:string\">8451</pwd>"
                    + "</ws:ListaVehiculos>"
                    + "</soapenv:Body>"
                    + "</soapenv:Envelope>";

            ClienteRest.Cliente_Rest_DISATEL cliente_rest_disatel = new ClienteRest.Cliente_Rest_DISATEL();
            String response_cliente_rest_disatel = cliente_rest_disatel.ListaVehiculos(xml_request);
            
            response_cliente_rest_disatel = response_cliente_rest_disatel.replaceAll(" xsi:type=\"tns:VehicleInfo\"", "");
            response_cliente_rest_disatel = response_cliente_rest_disatel.replaceAll(" xsi:type=\"xsd:string\"", "");
            // response_cliente_rest_disatel = response_cliente_rest_disatel.replaceAll(" xsi:type=\"SOAP-ENC:Array\" SOAP-ENC:arrayType=\"tns:VehicleInfo[89]\"", "");

            SOAPMessage soap_response = MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(response_cliente_rest_disatel.getBytes("UTF-8")));
            Unmarshaller unmarshaller = JAXBContext.newInstance(Entidad.DISATEL.ListaVehiculosResponse.class).createUnmarshaller();
            Entidad.DISATEL.ListaVehiculosResponse lista_vehiculos_response = (Entidad.DISATEL.ListaVehiculosResponse) unmarshaller.unmarshal(soap_response.getSOAPBody().extractContentAsDocument());

            List<Entidad.DISATEL.Item> lista_items_temp = new ArrayList<>();
            for(Integer i = 0; i < lista_vehiculos_response.getItems().size(); i++) {

                Calendar fecha_consulta_inicial = Calendar.getInstance();
                fecha_consulta_inicial.add(Calendar.HOUR, -1);

                Calendar fecha_consulta_final = Calendar.getInstance();
                fecha_consulta_final.add(Calendar.HOUR, 1);

                Calendar fecha_registro_ws = Calendar.getInstance();
                SimpleDateFormat dateFormat_sms = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                fecha_registro_ws.setTime(dateFormat_sms.parse(lista_vehiculos_response.getItems().get(i).getDate()));

                SimpleDateFormat dateFormat_temp = new SimpleDateFormat("yyyyMMddHHmmss");

                Long fechaInicial = Long.valueOf(dateFormat_temp.format(fecha_consulta_inicial.getTime()));
                Long fechaFinal = Long.valueOf(dateFormat_temp.format(fecha_consulta_final.getTime()));
                Long fechaRegistro = Long.valueOf(dateFormat_temp.format(fecha_registro_ws.getTime()));

                if((fechaRegistro >= fechaInicial) && (fechaRegistro <= fechaFinal)) {
                    Entidad.DISATEL.Item item = lista_vehiculos_response.getItems().get(i);
                    lista_items_temp.add(item);
                }
            }
            lista_vehiculos_response.setItems(lista_items_temp);

            Control_Base_Datos control_base_datos = new Control_Base_Datos();
            conn = control_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            String sql = "DELETE FROM DISATEL_DETALLE WHERE ID_DISATEL > 0";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            Long ID_DISATEL = control_base_datos.ObtenerLong("SELECT IFNULL(MAX(A.ID_DISATEL),0)+1 MAX_ID FROM DISATEL_ENCABEZADO A", conn);

            sql = "INSERT INTO DISATEL_ENCABEZADO (ID_DISATEL, FECHA_ACTUALIZACION, NUMERO_UBICACIONES) VALUES ("
                    + ID_DISATEL + ","
                    + "CURRENT_TIMESTAMP" + ","
                    + "0" + ")";
            stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            Integer CORRELATIVO = 0;
            for (Integer i = 0; i < lista_vehiculos_response.getItems().size(); i++) {
                CORRELATIVO++;

                sql = "INSERT INTO DISATEL_DETALLE ("
                        + "ID_DISATEL, "
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
                        + ID_DISATEL + ","
                        + CORRELATIVO + ",'"
                        + lista_vehiculos_response.getItems().get(i).getName() + "','"
                        + lista_vehiculos_response.getItems().get(i).getIMEI() + "','"
                        + lista_vehiculos_response.getItems().get(i).getSpeed() + "','"
                        + lista_vehiculos_response.getItems().get(i).getLatitude() + "','"
                        + lista_vehiculos_response.getItems().get(i).getLongitude() + "','"
                        + lista_vehiculos_response.getItems().get(i).getDate() + "','"
                        + lista_vehiculos_response.getItems().get(i).getSpeed() + "','"
                        + "Kmh" + "','"
                        + "0" + "','"
                        + lista_vehiculos_response.getItems().get(i).getAddress() + "','"
                        + lista_vehiculos_response.getItems().get(i).getDriver() + "','"
                        + lista_vehiculos_response.getItems().get(i).getDriver() + "','"
                        + lista_vehiculos_response.getItems().get(i).getSpeed() + "','"
                        + lista_vehiculos_response.getItems().get(i).getDate() + "','"
                        + lista_vehiculos_response.getItems().get(i).getAddress() + "',"
                        + "CURRENT_TIMESTAMP" + ")";
                stmt = conn.createStatement();
                // System.out.println("SQL: " + sql);
                stmt.executeUpdate(sql);
                stmt.close();
            }

            sql = "UPDATE DISATEL_ENCABEZADO SET NUMERO_UBICACIONES=" + CORRELATIVO + " WHERE ID_DISATEL=" + ID_DISATEL;
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
                    + "DISATEL_DETALLE A";
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
                        + "(V.ID_TRANSPORTISTA IN (2, 31)) AND "
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
                    Long ID_CLIENTE_DESTINO = rs1.getLong(7);
                    String ETA_HORAS = "0.00";
                    String EDA_KMS = "0.00";

                    try {
                        sql = "INSERT INTO VIAJE_UBICACIONES_DISATEL ("
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
                        // System.out.println("DISATEL: UBICACION YA EXISTE." + ex.toString());
                    }

                    // this.validar_viajes_cerrados(ID_PAIS, ID_COMPANIA, ID_PLANTA, NUMERO_VIAJE, TIPO_ORDEN_VENTA, NUMERO_ORDEN_VENTA, ID_CLIENTE_DESTINO, conn);

                    sql = "DELETE FROM DISATEL_DETALLE WHERE "
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
            resultado = gson.toJson(lista_vehiculos_response);
        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;
                }
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ListaVehiculos()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ListaVehiculos()|ERROR:" + ex.toString());
            } catch (Exception ex1) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ListaVehiculos_rollback()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ListaVehiculos_rollback()|ERROR:" + ex.toString());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ListaVehiculos_finally()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ListaVehiculos_finally()|ERROR:" + ex.toString());
            }
        }

        return resultado;
    }

    private void validar_viajes_cerrados(Long ID_PAIS, Long ID_COMPANIA, Long ID_PLANTA, Long NUMERO_VIAJE, String TIPO_ORDEN_VENTA, Long NUMERO_ORDEN_VENTA, Long ID_CLIENTE_DESTINO, Connection conn) {
        try {
            String sql = "SELECT "
                    + "A.LATITUDE, "
                    + "A.LONGITUDE, "
                    + "A.FECHA_HORA "
                    + "FROM "
                    + "VIAJE_UBICACIONES_DISATEL A "
                    + "WHERE "
                    + "A.ID_PAIS=" + ID_PAIS + " AND "
                    + "A.ID_COMPANIA=" + ID_COMPANIA + " AND "
                    + "A.ID_PLANTA=" + ID_PLANTA + " AND "
                    + "A.NUMERO_VIAJE=" + NUMERO_VIAJE + " AND "
                    + "A.TIPO_ORDEN_VENTA='" + TIPO_ORDEN_VENTA + "' AND "
                    + "A.NUMERO_ORDEN_VENTA=" + NUMERO_ORDEN_VENTA;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
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
                        // System.out.println("GEO-ZONA: {"
                                // + "(" + ZONA_LATITUD_1 + "," + ZONA_LONGITUD_1 + ");"
                                // + "(" + ZONA_LATITUD_2 + "," + ZONA_LONGITUD_2 + ");"
                                // + "(" + ZONA_LATITUD_3 + "," + ZONA_LONGITUD_3 + ");"
                                // + "(" + ZONA_LATITUD_4 + "," + ZONA_LONGITUD_4 + ");"
                                // + "(" + ZONA_LATITUD_5 + "," + ZONA_LONGITUD_5 + ")"
                                // + "}"); 
                        // System.out.println("UBICACIÓN-ACTUAL: {(" + LATITUDE + "," + LONGITUDE + ")}");

                        sql = "UPDATE "
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
                        // System.out.println("SQL: " + sql);
                        stmt1.executeUpdate(sql);
                        stmt1.close();
                    }
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:validar_viajes_cerrados()|ERROR:" + ex.toString());
        }
    }

}
