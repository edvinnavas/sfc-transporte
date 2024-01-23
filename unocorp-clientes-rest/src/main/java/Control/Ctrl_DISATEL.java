package Control;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

            Long ID_DISATEL = control_base_datos.ObtenerLong("SELECT IFNULL(MAX(A.ID_DISATEL),0)+1 MAX_ID FROM DISATEL_ENCABEZADO A", conn);

            String sql = "INSERT INTO DISATEL_ENCABEZADO (ID_DISATEL, FECHA_ACTUALIZACION, NUMERO_UBICACIONES) VALUES ("
                    + ID_DISATEL + ","
                    + "CURRENT_TIMESTAMP" + ","
                    + "0" + ")";
            Statement stmt = conn.createStatement();
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

            conn.commit();

            Integer en_ejecucion = control_base_datos.ObtenerEntero("SELECT SUM(A.ESTADO) EN_EJEUCCION FROM AMBIENTE_EJECUCION A", conn);
            if(en_ejecucion == 0) {
                sql = "UPDATE AMBIENTE_EJECUCION SET ESTADO=1, FECHA_HORA=CURRENT_TIMESTAMP WHERE ID_EJECUCION=3";
                stmt = conn.createStatement();
                // System.out.println("SQL: " + sql);
                stmt.executeUpdate(sql);
                stmt.close();

                conn.commit();

                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                Calendar fecha_actual = Calendar.getInstance();
                sql = "SELECT DISTINCT "
                        + "V.ID_PAIS, " 
                        + "V.ID_COMPANIA, " 
                        + "V.ID_PLANTA, " 
                        + "V.NUMERO_VIAJE, " 
                        + "V.TIPO_ORDEN_VENTA, " 
                        + "V.NUMERO_ORDEN_VENTA, " 
                        + "STR_TO_DATE(SOD.DATETIME_UBICACION, '%Y/%m/%d %H:%i:%s') FECHA_HORA_UBICACION, " 
                        + "SOD.IMEI, " 
                        + "SOD.LATITUDE LATITUD_UBICACION, " 
                        + "SOD.LONGITUDE LONGITUD_UBICACION, " 
                        + "SOD.LOCATIONDESCRIPTION DESCRIPCION_UBICACION, " 
                        + "V.ID_CLIENTE_DESTINO " 
                        + "FROM " 
                        + "VIAJES V " 
                        + "LEFT JOIN DISPONIBILIDAD D ON (V.ID_TRANSPORTISTA=D.ID_TRANSPORTISTA AND V.ID_VEHICULO=D.ID_VEHICULO AND V.FECHA_VIAJE=D.FECHA) "
                        + "LEFT JOIN CABEZAL CD ON (D.ID_CABEZAL=CD.ID_CABEZAL) "
                        + "LEFT JOIN DISATEL_DETALLE SOD ON (CD.IMEI=SOD.IMEI AND STR_TO_DATE(SOD.DATETIME_UBICACION, '%Y/%m/%d %H:%i:%s') BETWEEN DATE_FORMAT(V.FECHA_VIAJE, '%Y-%m-%d %H:%i:%s') AND '"+ dateFormat1.format(fecha_actual.getTime()) + " 23:59:59') "
                        + "WHERE "
                        + "(V.ID_ESTADO_VIAJE NOT IN (2, 5, 10)) AND "
                        + "(V.ID_TRANSPORTISTA IN (2, 31)) AND "
                        + "(SOD.IMEI IS NOT NULL) AND "
                        + "((V.ID_PAIS, V.ID_COMPANIA, V.ID_PLANTA, V.NUMERO_VIAJE, V.TIPO_ORDEN_VENTA, V.NUMERO_ORDEN_VENTA, STR_TO_DATE(SOD.DATETIME_UBICACION, '%Y/%m/%d %H:%i:%s'), SOD.IMEI) NOT IN (SELECT F.ID_PAIS, F.ID_COMPANIA, F.ID_PLANTA, F.NUMERO_VIAJE, F.TIPO_ORDEN_VENTA, F.NUMERO_ORDEN_VENTA, F.FECHA_HORA, F.IMEI FROM VIAJE_UBICACIONES_DISATEL F))";
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
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
                                + dateFormat2.format(FECHA_HORA) + "','"
                                + IMEI + "','"
                                + LATITUDE + "','"
                                + LONGITUDE + "','"
                                + LOCATIONDESCRIPTION + "','"
                                + ETA_HORAS + "','"
                                + EDA_KMS + "')";
                        Statement stmt1 = conn.createStatement();
                        // System.out.println("SQL: " + sql);
                        stmt1.executeUpdate(sql);
                        stmt1.close();

                        this.validar_viajes_cerrados(ID_PAIS, ID_COMPANIA, ID_PLANTA, NUMERO_VIAJE, TIPO_ORDEN_VENTA, NUMERO_ORDEN_VENTA, ID_CLIENTE_DESTINO, conn);
                    } catch(Exception ex) {
                        // System.out.println("DISATEL: UBICACION YA EXISTE." + ex.toString());
                    }
                }
                rs.close();
                stmt.close();

                sql = "UPDATE AMBIENTE_EJECUCION SET ESTADO=0, FECHA_HORA=CURRENT_TIMESTAMP WHERE ID_EJECUCION=3";
                stmt = conn.createStatement();
                // System.out.println("SQL: " + sql);
                stmt.executeUpdate(sql);
                stmt.close();

                conn.commit();
            }

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
