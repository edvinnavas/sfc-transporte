package Control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Ctrl_GEOTAB_GT implements Serializable {

    private static final long serialVersionUID = 1L;

    public Ctrl_GEOTAB_GT() {
    }

    public String ObtenerUbicaciones() {
        String resultado = "";
        Connection conn = null;

        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            SimpleDateFormat dateFormat_zulu = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssX");
            SimpleDateFormat dateFormat_db = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ClienteRest.Cliente_Rest_GEOTAB cliente_rest_api = new ClienteRest.Cliente_Rest_GEOTAB();
            
            // AUTENTICAR EN EL API DE GEOTAB PARA OBTERNER EL SESSIONID.
            Entidad.GEOTAB.Params params = new Entidad.GEOTAB.Params();
            params.setDatabase("grupoterra_guatemala");
            params.setUserName("lrmolina@uno-ca.com");
            params.setPassword("Pass0512024");

            Entidad.GEOTAB.Authenticate authenticate = new Entidad.GEOTAB.Authenticate();
            authenticate.setMethod("Authenticate");
            authenticate.setParams(params);

            // System.out.println("JSON-AUTHENTICATE: " + gson.toJson(authenticate));
            String json_result = cliente_rest_api.GeoTab_Services(gson.toJson(authenticate));

            Type authenticate_response_type = new TypeToken<Entidad.GEOTAB.Authenticate_Response>() {
            }.getType();

            Entidad.GEOTAB.Authenticate_Response authenticate_response = new Gson().fromJson(json_result, authenticate_response_type);

            // CONSUMIR EL METODO GetFeed PARA OBTENER LAS UBICACIONES DE LOS VEHICULOS.
            Calendar fecha_actual_w = Calendar.getInstance();
            fecha_actual_w.add(Calendar.MINUTE, -30);

            Entidad.GEOTAB.Search search = new Entidad.GEOTAB.Search();
            search.setFromDate(dateFormat.format(fecha_actual_w.getTime()));

            Entidad.GEOTAB.Credentials credentials = new Entidad.GEOTAB.Credentials();
            credentials.setDatabase("grupoterra_guatemala");
            credentials.setUserName("lrmolina@uno-ca.com");
            credentials.setPassword("Pass0512024");
            credentials.setSessionId(authenticate_response.getResult().getCredentials().getSessionId());

            Entidad.GEOTAB.ParamsGetFeed params_get_feed = new Entidad.GEOTAB.ParamsGetFeed();
            params_get_feed.setSearch(search);
            params_get_feed.setTypeName("LogRecord");
            params_get_feed.setCredentials(credentials);

            Entidad.GEOTAB.GetFeed get_feed = new Entidad.GEOTAB.GetFeed();
            get_feed.setMethod("GetFeed");
            get_feed.setParams(params_get_feed);

            // System.out.println("JSON-GET-FEED: " + gson.toJson(get_feed));
            json_result = cliente_rest_api.GeoTab_Services(gson.toJson(get_feed));

            Type get_feed_response_type = new TypeToken<Entidad.GEOTAB.GetFeed_Response>() {
            }.getType();

            Entidad.GEOTAB.GetFeed_Response get_feed_response = new Gson().fromJson(json_result, get_feed_response_type);
            
            List<Entidad.GEOTAB.Data> lst_data_temp = new ArrayList<>();
            for (Integer i = 0; i < get_feed_response.getResult().getData().size(); i++) {
                
                Calendar fecha_consulta_inicial = Calendar.getInstance();
                fecha_consulta_inicial.add(Calendar.HOUR, -1);

                Calendar fecha_consulta_final = Calendar.getInstance();
                fecha_consulta_final.add(Calendar.HOUR, 1);

                Calendar fecha_registro_ws = Calendar.getInstance();
                fecha_registro_ws.setTime(dateFormat_zulu.parse(get_feed_response.getResult().getData().get(i).getDateTime()));

                SimpleDateFormat dateFormat_temp = new SimpleDateFormat("yyyyMMddHHmmss");

                Long fechaInicial = Long.valueOf(dateFormat_temp.format(fecha_consulta_inicial.getTime()));
                Long fechaFinal = Long.valueOf(dateFormat_temp.format(fecha_consulta_final.getTime()));
                Long fechaRegistro = Long.valueOf(dateFormat_temp.format(fecha_registro_ws.getTime()));

                if((fechaRegistro >= fechaInicial) && (fechaRegistro <= fechaFinal)) {
                    Entidad.GEOTAB.Data data = get_feed_response.getResult().getData().get(i);
                    Date datetime = dateFormat_zulu.parse(get_feed_response.getResult().getData().get(i).getDateTime());
                    data.setDateTime(dateFormat_db.format(datetime));
                    lst_data_temp.add(data);
                }
            }
            get_feed_response.getResult().setData(lst_data_temp);
            
            Control_Base_Datos control_base_datos = new Control_Base_Datos();
            conn = control_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            /* String sql = "DELETE FROM GEOTAB_DETALLE_GT WHERE ID_GEOTAB > 0";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close(); */

            Long ID_GEOTAB = control_base_datos.ObtenerLong("SELECT IFNULL(MAX(A.ID_GEOTAB),0)+1 MAX_ID FROM GEOTAB_ENCABEZADO_GT A", conn);

            String sql = "INSERT INTO GEOTAB_ENCABEZADO_GT (ID_GEOTAB, FECHA_ACTUALIZACION, NUMERO_UBICACIONES) VALUES ("
                    + ID_GEOTAB + ","
                    + "CURRENT_TIMESTAMP" + ","
                    + "0" + ")";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            Integer CORRELATIVO = 0;
            for (Integer i = 0; i < get_feed_response.getResult().getData().size(); i++) {
                CORRELATIVO++;
                // Date datetime = dateFormat_zulu.parse(get_feed_response.getResult().getData().get(i).getDateTime());
                sql = "INSERT INTO GEOTAB_DETALLE_GT (" 
                        + "ID_GEOTAB, "
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
                        + ID_GEOTAB + ","
                        + CORRELATIVO + ",'"
                        + get_feed_response.getResult().getData().get(i).getDevice().getId() + "-GT" + "','"
                        + get_feed_response.getResult().getData().get(i).getDevice().getId() + "-GT" + "','"
                        + get_feed_response.getResult().getData().get(i).getSpeed() + "','"
                        + get_feed_response.getResult().getData().get(i).getLatitude() + "','"
                        + get_feed_response.getResult().getData().get(i).getLongitude() + "','"
                        + get_feed_response.getResult().getData().get(i).getDateTime() + "','"
                        + get_feed_response.getResult().getData().get(i).getSpeed() + "','"
                        + "Kmh" + "','"
                        + "0" + "','"
                        + "Sin descripción ubicación" + "','"
                        + "No DriverName" + "','"
                        + "No DriverCode" + "','"
                        + get_feed_response.getResult().getData().get(i).getSpeed() + "','"
                        + get_feed_response.getResult().getData().get(i).getDateTime() + "','"
                        + "Sin descripción ubicación" + "',"
                        + "CURRENT_TIMESTAMP" + ")";
                stmt = conn.createStatement();
                // System.out.println("SQL: " + sql);
                stmt.executeUpdate(sql);
                stmt.close();
            }

            sql = "UPDATE GEOTAB_ENCABEZADO_GT SET NUMERO_UBICACIONES=" + CORRELATIVO + " WHERE ID_GEOTAB=" + ID_GEOTAB;
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
                    + "GEOTAB_DETALLE_GT A";
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
                        + "(V.ID_TRANSPORTISTA IN (3, 36)) AND "
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
                        sql = "INSERT INTO VIAJE_UBICACIONES_GEOTAB_GT ("
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
                        // System.out.println("GEOTAB-GT: UBICACION YA EXISTE." + ex.toString());
                    }

                    // this.validar_viajes_cerrados(ID_PAIS, ID_COMPANIA, ID_PLANTA, NUMERO_VIAJE, TIPO_ORDEN_VENTA, NUMERO_ORDEN_VENTA, ID_CLIENTE_DESTINO, conn);

                    sql = "DELETE FROM GEOTAB_DETALLE_GT WHERE "
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

            resultado = gson.toJson(get_feed_response);
        } catch (Exception ex) {
            try {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString());
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;

                    resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString();
                    System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString());
                }
            } catch (Exception ex1) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones_rollback()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones_rollback()|ERROR:" + ex.toString());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones_finally()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones_finally()|ERROR:" + ex.toString());
            }
        }

        return resultado;
    }

}
