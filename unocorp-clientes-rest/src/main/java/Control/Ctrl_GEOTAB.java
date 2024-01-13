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

public class Ctrl_GEOTAB implements Serializable {

    private static final long serialVersionUID = 1L;

    public Ctrl_GEOTAB() {
    }

    public String ObtenerUbicaciones(String database) {
        String resultado = "";

        Connection conn = null;

        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            SimpleDateFormat dateFormat_zulu = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssX");
            SimpleDateFormat dateFormat_db = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ClienteRest.Cliente_Rest_GEOTAB cliente_rest_api = new ClienteRest.Cliente_Rest_GEOTAB();
            
            String database_geotab;
            String lista_transportista;
            Integer periodo_consulta;
            switch (database) {
                case "CR": {
                    database_geotab = "grupoterra_costarica";
                    lista_transportista = "5";
                    periodo_consulta = -30;
                    break;
                }
                case "GT": {
                    database_geotab = "grupoterra_guatemala";
                    lista_transportista = "3, 36";
                    periodo_consulta = -30;
                    break;
                }
                default: {
                    database_geotab = "";
                    lista_transportista = "";
                    periodo_consulta = 0;
                    break;
                }
            }
            
            // AUTENTICAR EN EL API DE GEOTAB PARA OBTERNER EL SESSIONID.
            Entidad.GEOTAB.Params params = new Entidad.GEOTAB.Params();
            params.setDatabase(database_geotab);
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
            fecha_actual_w.add(Calendar.MINUTE, periodo_consulta);

            Entidad.GEOTAB.Search search = new Entidad.GEOTAB.Search();
            // System.out.println("GEOTAB: FECHA-CONSULTA: " + dateFormat.format(fecha_actual_w.getTime()));
            search.setFromDate(dateFormat.format(fecha_actual_w.getTime()));

            Entidad.GEOTAB.Credentials credentials = new Entidad.GEOTAB.Credentials();
            credentials.setDatabase(database_geotab);
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

            System.out.println("JSON-GET-FEED: " + gson.toJson(get_feed));
            json_result = cliente_rest_api.GeoTab_Services(gson.toJson(get_feed));

            Type get_feed_response_type = new TypeToken<Entidad.GEOTAB.GetFeed_Response>() {
            }.getType();

            Entidad.GEOTAB.GetFeed_Response get_feed_response = new Gson().fromJson(json_result, get_feed_response_type);
            System.out.println("NUMERO-REGISTROS-INICIAL:" + get_feed_response.getResult().getData().size());
            
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
            System.out.println("NUMERO-REGISTROS-FINAL:" + get_feed_response.getResult().getData().size());

            resultado = gson.toJson(get_feed_response);
            
            Control_Base_Datos control_base_datos = new Control_Base_Datos();
            conn = control_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            Long ID_GEOTAB = control_base_datos.ObtenerLong("SELECT IFNULL(MAX(A.ID_GEOTAB),0)+1 MAX_ID FROM GEOTAB_ENCABEZADO A", conn);

            String sql = "INSERT INTO GEOTAB_ENCABEZADO (ID_GEOTAB, FECHA_ACTUALIZACION, NUMERO_UBICACIONES) VALUES ("
                    + ID_GEOTAB + ","
                    + "CURRENT_TIMESTAMP" + ","
                    + "0" + ")";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            System.out.println("INICIA-INSERT-GEOTAB-DETALLE:" + new Date());
            Integer CORRELATIVO = 0;
            for (Integer i = 0; i < get_feed_response.getResult().getData().size(); i++) {
                CORRELATIVO++;
                // Date datetime = dateFormat_zulu.parse(get_feed_response.getResult().getData().get(i).getDateTime());
                sql = "INSERT INTO GEOTAB_DETALLE (" 
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
                        + get_feed_response.getResult().getData().get(i).getDevice().getId() + "-" + database + "','"
                        + get_feed_response.getResult().getData().get(i).getDevice().getId() + "-" + database + "','"
                        + get_feed_response.getResult().getData().get(i).getSpeed() + "','"
                        + get_feed_response.getResult().getData().get(i).getLatitude() + "','"
                        + get_feed_response.getResult().getData().get(i).getLongitude() + "','"
                        // + dateFormat_db.format(dateFormat_zulu.parse(get_feed_response.getResult().getData().get(i).getDateTime())) + "','"
                        + get_feed_response.getResult().getData().get(i).getDateTime() + "','"
                        + get_feed_response.getResult().getData().get(i).getSpeed() + "','"
                        + "Kmh" + "','"
                        + "0" + "','"
                        + "Sin descripción ubicación" + "','"
                        + "No DriverName" + "','"
                        + "No DriverCode" + "','"
                        + get_feed_response.getResult().getData().get(i).getSpeed() + "','"
                        // + dateFormat_db.format(dateFormat_zulu.parse(get_feed_response.getResult().getData().get(i).getDateTime())) + "','"
                        + get_feed_response.getResult().getData().get(i).getDateTime() + "','"
                        + "Sin descripción ubicación" + "',"
                        + "CURRENT_TIMESTAMP" + ")";
                stmt = conn.createStatement();
                // System.out.println("SQL: " + sql);
                stmt.executeUpdate(sql);
                stmt.close();
            }
            System.out.println("FINALIZA-INSERT-GEOTAB-DETALLE:" + new Date());

            sql = "UPDATE GEOTAB_ENCABEZADO SET NUMERO_UBICACIONES=" + CORRELATIVO + " WHERE ID_GEOTAB=" + ID_GEOTAB;
            stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();
            
            Calendar fecha_actual = Calendar.getInstance();
            
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            sql = "SELECT DISTINCT "
                    + "V.ID_PAIS, "
                    + "V.ID_COMPANIA, "
                    + "V.ID_PLANTA, "
                    + "V.NUMERO_VIAJE, "
                    + "V.TIPO_ORDEN_VENTA, "
                    + "V.NUMERO_ORDEN_VENTA, "
                    + "STR_TO_DATE(SOD.DATETIME_UBICACION, '%Y-%m-%d %H:%i:%s') FECHA_HORA_UBICACION, "
                    + "SOD.IMEI, "
                    + "SOD.LATITUDE LATITUD_UBICACION, "
                    + "SOD.LONGITUDE LONGITUD_UBICACION, "
                    + "SOD.LOCATIONDESCRIPTION DESCRIPCION_UBICACION, "
                    + "V.ID_CLIENTE_DESTINO "
                    + "FROM "
                    + "VIAJES V "
                    + "LEFT JOIN TRANSPORTISTA T ON (V.ID_TRANSPORTISTA=T.ID_TRANSPORTISTA) "
                    + "LEFT JOIN DISPONIBILIDAD D ON (V.ID_TRANSPORTISTA=D.ID_TRANSPORTISTA AND V.ID_VEHICULO=D.ID_VEHICULO AND D.FECHA BETWEEN V.FECHA_VIAJE AND V.FECHA_VIAJE) "
                    + "LEFT JOIN CABEZAL CD ON (D.ID_CABEZAL=CD.ID_CABEZAL) "
                    + "LEFT JOIN GEOTAB_DETALLE SOD ON (CD.IMEI=SOD.IMEI AND STR_TO_DATE(SOD.DATETIME_UBICACION, '%Y-%m-%d %H:%i:%s') BETWEEN DATE_FORMAT(V.FECHA_VIAJE, '%Y-%m-%d %H:%i:%s') AND '" + dateFormat1.format(fecha_actual.getTime()) + " 23:59:59') "
                    + "WHERE "
                    + "V.ID_ESTADO_VIAJE NOT IN (2, 5, 10) AND "
                    + "T.ID_TRANSPORTISTA IN (" + lista_transportista + ") AND "
                    + "T.RASTREABLE=1 AND "
                    + "SOD.ID_GEOTAB IS NOT NULL";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            System.out.println("INICIA-INSERT-VIAJE-UBICACIONES:" + new Date());
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
                    sql = "INSERT INTO VIAJE_UBICACIONES ("
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
                    System.out.println("SQL: " + sql);
                    stmt1.executeUpdate(sql);
                    stmt1.close();

                    this.validar_viajes_cerrados(ID_PAIS, ID_COMPANIA, ID_PLANTA, NUMERO_VIAJE, TIPO_ORDEN_VENTA, NUMERO_ORDEN_VENTA, ID_CLIENTE_DESTINO, conn);
                } catch(Exception ex) {
                    // System.out.println("GEOTAB: UBICACION YA EXISTE." + ex.toString());
                }
            }
            rs.close();
            stmt.close();
            
            conn.commit();
            conn.setAutoCommit(true);
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

    private void validar_viajes_cerrados(Long ID_PAIS, Long ID_COMPANIA, Long ID_PLANTA, Long NUMERO_VIAJE, String TIPO_ORDEN_VENTA, Long NUMERO_ORDEN_VENTA, Long ID_CLIENTE_DESTINO, Connection conn) {
        try {
            String sql = "SELECT "
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
