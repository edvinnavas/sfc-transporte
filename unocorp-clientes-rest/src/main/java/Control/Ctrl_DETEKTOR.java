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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Ctrl_DETEKTOR implements Serializable {

    private static final long serialVersionUID = 1L;

    public Ctrl_DETEKTOR() {
    }

    public String Replica() {
        String resultado = "";

        Connection conn = null;

        try {
            String xml_request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:server.replica\">"
                    + "<soapenv:Header/>"
                    + "<soapenv:Body>"
                    + "<urn:replica>"
                    + "<usuario>ABCD</usuario>"
                    + "<clave>2022</clave>"
                    + "<limpiar>true</limpiar>"
                    + "</urn:replica>"
                    + "</soapenv:Body>"
                    + "</soapenv:Envelope>";

            ClienteRest.Cliente_Rest_DETEKTOR cliente_rest_detektor = new ClienteRest.Cliente_Rest_DETEKTOR();
            String response_cliente_rest_detektor = cliente_rest_detektor.Replica(xml_request);
            response_cliente_rest_detektor = response_cliente_rest_detektor.replaceAll("xmlns:ns1=\"urn:server.replica\"", "");
            response_cliente_rest_detektor = response_cliente_rest_detektor.replaceAll("xsi:type=\"xsd:string\"", "");
            response_cliente_rest_detektor = response_cliente_rest_detektor.replaceAll("ns1:", "");

            SOAPMessage soap_response = MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(response_cliente_rest_detektor.getBytes("UTF-8")));
            Unmarshaller unmarshaller = JAXBContext.newInstance(Entidad.DETEKTOR.replicaResponse.class).createUnmarshaller();
            Entidad.DETEKTOR.replicaResponse replica_response = (Entidad.DETEKTOR.replicaResponse) unmarshaller.unmarshal(soap_response.getSOAPBody().extractContentAsDocument());

            String pila = replica_response.getPila();
            pila = pila.substring(1, pila.length() - 1);
            pila = pila.replaceAll("\"", "'");
            pila = pila.replaceAll("\\],\\[", "\\]♣\\[");
            pila = pila.replaceAll("[\\[\\]]", "");
            String[] registros_pila = pila.split("♣");
            List<String> lista_pila = new ArrayList<String>(Arrays.asList(registros_pila));

            List<Entidad.DETEKTOR.Ubicacion> lista_ubicaciones = new ArrayList<>();
            for(Integer i=0; i < lista_pila.size(); i++) {
                String[] vector_ubicacion = lista_pila.get(i).split(",");
                Entidad.DETEKTOR.Ubicacion ubicacion = new Entidad.DETEKTOR.Ubicacion();
                ubicacion.setPlaca(vector_ubicacion[0].replaceAll("'", ""));
                ubicacion.setCodigo_equipo(vector_ubicacion[1].replaceAll("'", ""));
                ubicacion.setMotivo_trasmision(vector_ubicacion[2].replaceAll("'", ""));
                ubicacion.setFecha_ubicacion(vector_ubicacion[3].replaceAll("'", ""));
                ubicacion.setIgnicion(vector_ubicacion[4].replaceAll("'", ""));
                ubicacion.setBateria(vector_ubicacion[5].replaceAll("'", ""));
                ubicacion.setVoltaje_bateria(vector_ubicacion[6].replaceAll("'", ""));
                ubicacion.setGrados_desplazamiento(vector_ubicacion[7].replaceAll("'", ""));
                ubicacion.setLatitud(vector_ubicacion[8].replaceAll("'", ""));
                ubicacion.setLongitud(vector_ubicacion[9].replaceAll("'", ""));
                ubicacion.setVelocidad(vector_ubicacion[10].replaceAll("'", ""));
                ubicacion.setAltitud(vector_ubicacion[11].replaceAll("'", ""));
                ubicacion.setDistancia(vector_ubicacion[12].replaceAll("'", ""));
                ubicacion.setFecha_grabacion(vector_ubicacion[13].replaceAll("'", ""));
                ubicacion.setId_vehiculo(vector_ubicacion[14].replaceAll("'", ""));
                lista_ubicaciones.add(ubicacion);
            }

            List<Entidad.DETEKTOR.Ubicacion> lista_ubicaciones_temp = new ArrayList<>();
            for (Integer i = 0; i < lista_ubicaciones.size(); i++) {
                
                Calendar fecha_consulta_inicial = Calendar.getInstance();
                fecha_consulta_inicial.add(Calendar.HOUR, -1);

                Calendar fecha_consulta_final = Calendar.getInstance();
                fecha_consulta_final.add(Calendar.HOUR, 1);

                Calendar fecha_registro_ws = Calendar.getInstance();
                SimpleDateFormat dateFormat_detek = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                fecha_registro_ws.setTime(dateFormat_detek.parse(lista_ubicaciones.get(i).getFecha_ubicacion()));

                SimpleDateFormat dateFormat_temp = new SimpleDateFormat("yyyyMMddHHmmss");

                Long fechaInicial = Long.valueOf(dateFormat_temp.format(fecha_consulta_inicial.getTime()));
                Long fechaFinal = Long.valueOf(dateFormat_temp.format(fecha_consulta_final.getTime()));
                Long fechaRegistro = Long.valueOf(dateFormat_temp.format(fecha_registro_ws.getTime()));

                if((fechaRegistro >= fechaInicial) && (fechaRegistro <= fechaFinal)) {
                    Entidad.DETEKTOR.Ubicacion ubicacion = lista_ubicaciones.get(i);
                    lista_ubicaciones_temp.add(ubicacion);
                }
            }

            List<Entidad.DETEKTOR.Ubicacion> lista_ubicaciones_temp_2 = new ArrayList<>();
            for (Integer i = 0; i < lista_ubicaciones_temp.size(); i++) {
                Boolean existe = false;
                for(Integer j = 0; j > lista_ubicaciones_temp_2.size(); j++) {
                    if(lista_ubicaciones_temp.get(i).getId_vehiculo().equals(lista_ubicaciones_temp_2.get(j).getId_vehiculo()) && lista_ubicaciones_temp.get(i).getFecha_ubicacion().equals(lista_ubicaciones_temp_2.get(j).getFecha_ubicacion()) && lista_ubicaciones_temp.get(i).getLatitud().equals(lista_ubicaciones_temp_2.get(j).getLatitud()) && lista_ubicaciones_temp.get(i).getLongitud().equals(lista_ubicaciones_temp_2.get(j).getLongitud())) {
                        existe = true;
                    }
                }
                if(!existe) {
                    lista_ubicaciones_temp_2.add(lista_ubicaciones_temp.get(i));
                }
            }

            lista_ubicaciones = lista_ubicaciones_temp_2;

            Control_Base_Datos control_base_datos = new Control_Base_Datos();
            conn = control_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            /* String sql = "DELETE FROM DETEKTOR__DETALLE WHERE ID_DETEKTOR > 0";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close(); */

            Long ID_DETEKTOR = control_base_datos.ObtenerLong("SELECT IFNULL(MAX(A.ID_DETEKTOR),0)+1 MAX_ID FROM DETEKTOR_ENCABEZADO A", conn);

            String sql = "INSERT INTO DETEKTOR_ENCABEZADO (ID_DETEKTOR, FECHA_ACTUALIZACION, NUMERO_UBICACIONES) VALUES ("
                    + ID_DETEKTOR + ","
                    + "CURRENT_TIMESTAMP" + ","
                    + "0" + ")";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            Integer CORRELATIVO = 0;
            for (Integer i = 0; i < lista_ubicaciones.size(); i++) {
                CORRELATIVO++;

                sql = "INSERT INTO DETEKTOR_DETALLE ("
                        + "ID_DETEKTOR, "
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
                        + ID_DETEKTOR + ","
                        + CORRELATIVO + ",'"
                        + lista_ubicaciones.get(i).getPlaca() + "','"
                        + lista_ubicaciones.get(i).getId_vehiculo() + "','"
                        + lista_ubicaciones.get(i).getVelocidad() + "','"
                        + lista_ubicaciones.get(i).getLatitud() + "','"
                        + lista_ubicaciones.get(i).getLongitud() + "','"
                        + lista_ubicaciones.get(i).getFecha_ubicacion() + "','"
                        + lista_ubicaciones.get(i).getVelocidad() + "','"
                        + lista_ubicaciones.get(i).getVelocidad() + "','"
                        + lista_ubicaciones.get(i).getMotivo_trasmision() + "','"
                        + lista_ubicaciones.get(i).getLatitud() + "','"
                        + lista_ubicaciones.get(i).getCodigo_equipo() + "','"
                        + lista_ubicaciones.get(i).getCodigo_equipo() + "','"
                        + lista_ubicaciones.get(i).getIgnicion() + "','"
                        + lista_ubicaciones.get(i).getFecha_grabacion() + "','"
                        + lista_ubicaciones.get(i).getLatitud() + "',"
                        + "CURRENT_TIMESTAMP" + ")";
                stmt = conn.createStatement();
                // System.out.println("SQL: " + sql);
                stmt.executeUpdate(sql);
                stmt.close();
            }

            sql = "UPDATE DETEKTOR_ENCABEZADO SET NUMERO_UBICACIONES=" + CORRELATIVO + " WHERE ID_DETEKTOR=" + ID_DETEKTOR;
            stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            sql = "SELECT " 
                    + "STR_TO_DATE(SUBSTRING(A.DATETIME_UBICACION, 1, LENGTH(A.DATETIME_UBICACION) - 3), '%Y-%m-%d %H:%i:%s') FECHA_HORA_UBICACION, "
                    + "A.IMEI IMEI, " 
                    + "A.LATITUDE LATITUD_UBICACION, " 
                    + "A.LONGITUDE LONGITUD_UBICACION, " 
                    + "A.LOCATIONDESCRIPTION DESCRIPCION_UBICACION " 
                    + "FROM "
                    + "DETEKTOR_DETALLE A";
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
                        + "(V.ID_TRANSPORTISTA IN (22, 319)) AND " 
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
                        sql = "INSERT INTO VIAJE_UBICACIONES_DETEKTOR ("
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
                        // System.out.println("DETEKTOR: UBICACION YA EXISTE." + ex.toString());
                    }

                    // this.validar_viajes_cerrados(ID_PAIS, ID_COMPANIA, ID_PLANTA, NUMERO_VIAJE, TIPO_ORDEN_VENTA, NUMERO_ORDEN_VENTA, ID_CLIENTE_DESTINO, conn);

                    sql = "DELETE FROM DETEKTOR_DETALLE WHERE "
                            + "STR_TO_DATE(SUBSTRING(DATETIME_UBICACION, 1, LENGTH(DATETIME_UBICACION) - 3), '%Y-%m-%d %H:%i:%s')='" + GPS_FECHA_HORA_UBICACION + "' AND " 
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
            resultado = gson.toJson(lista_ubicaciones);
        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;
                }
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:Replica()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:Replica()|ERROR:" + ex.toString());
            } catch (Exception ex1) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:Replica()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:Replica()|ERROR:" + ex.toString());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:Replica()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:Replica()|ERROR:" + ex.toString());
            }
        }

        return resultado;
    }

}
