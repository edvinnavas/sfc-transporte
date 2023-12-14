package Control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
            ClienteRest.ClienteRestApi cliente_rest_api = new ClienteRest.ClienteRestApi();

            // AUTENTICAR EN EL API DE GEOTAB PARA OBTERNER EL SESSIONID.
            Entidad.Params params = new Entidad.Params();
            params.setDatabase(database);
            params.setUserName("lrmolina@uno-ca.com");
            params.setPassword("Pass0512024");

            Entidad.Authenticate authenticate = new Entidad.Authenticate();
            authenticate.setMethod("Authenticate");
            authenticate.setParams(params);

            System.out.println("JSON-AUTHENTICATE: " + gson.toJson(authenticate));
            String json_result = cliente_rest_api.GeoTab_Services(gson.toJson(authenticate));

            Type authenticate_response_type = new TypeToken<Entidad.Authenticate_Response>() {
            }.getType();

            Entidad.Authenticate_Response authenticate_response = new Gson().fromJson(json_result, authenticate_response_type);

            // CONSUMIR EL METODO GetFeed PARA OBTENER LAS UBICACIONES DE LOS VEHICULOS.
            Calendar fecha_actual = Calendar.getInstance();
            fecha_actual.add(Calendar.MINUTE, -5);

            Entidad.Search search = new Entidad.Search();
            search.setFromDate(dateFormat.format(fecha_actual.getTime()));

            Entidad.Credentials credentials = new Entidad.Credentials();
            credentials.setDatabase(database);
            credentials.setUserName("lrmolina@uno-ca.com");
            credentials.setPassword("Pass0512024");
            credentials.setSessionId(authenticate_response.getResult().getCredentials().getSessionId());

            Entidad.ParamsGetFeed params_get_feed = new Entidad.ParamsGetFeed();
            params_get_feed.setSearch(search);
            params_get_feed.setTypeName("LogRecord");
            params_get_feed.setCredentials(credentials);

            Entidad.GetFeed get_feed = new Entidad.GetFeed();
            get_feed.setMethod("GetFeed");
            get_feed.setParams(params_get_feed);

            System.out.println("JSON-GET-FEED: " + gson.toJson(get_feed));
            json_result = cliente_rest_api.GeoTab_Services(gson.toJson(get_feed));

            Type get_feed_response_type = new TypeToken<Entidad.GetFeed_Response>() {
            }.getType();

            Entidad.GetFeed_Response get_feed_response = new Gson().fromJson(json_result, get_feed_response_type);

            // resultado = gson.toJson(get_feed_response);
            
            Control_Base_Datos control_base_datos = new Control_Base_Datos();
            conn = control_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            Long ID_GEOTAB = control_base_datos.ObtenerLong("SELECT IFNULL(MAX(A.ID_GEOTAB),0)+1 MAX_ID FROM GEOTAB_ENCABEZADO A", conn);

            String cadenasql = "INSERT INTO GEOTAB_ENCABEZADO (ID_GEOTAB, FECHA_ACTUALIZACION, NUMERO_UBICACIONES) VALUES ("
                    + ID_GEOTAB + ","
                    + "CURRENT_TIMESTAMP" + ","
                    + "0" + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            Integer CORRELATIVO = 0;
            for (Integer i = 0; i < get_feed_response.getResult().getData().size(); i++) {
                CORRELATIVO++;

                cadenasql = "INSERT INTO GEOTAB_DETALLE ("
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
                        + get_feed_response.getResult().getData().get(i).getDevice().getId() + "-CR','"
                        + get_feed_response.getResult().getData().get(i).getDevice().getId() + "-CR','"
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
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }

            cadenasql = "UPDATE GEOTAB_ENCABEZADO SET NUMERO_UBICACIONES=" + CORRELATIVO + " WHERE ID_GEOTAB=" + ID_GEOTAB;
            stmt = conn.createStatement();
            stmt.executeUpdate(cadenasql);
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception ex) {
            try {
                resultado = "PROYECTO:client-rest-geotab|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:client-rest-geotab|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString());
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;

                    resultado = "PROYECTO:client-rest-geotab|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString();
                    System.out.println("PROYECTO:client-rest-geotab|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones()|ERROR:" + ex.toString());
                }
            } catch (Exception ex1) {
                resultado = "PROYECTO:client-rest-geotab|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones_rollback()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:client-rest-geotab|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones_rollback()|ERROR:" + ex.toString());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO:client-rest-geotab|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones_finally()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:client-rest-geotab|CLASE:" + this.getClass().getName() + "|METODO:ObtenerUbicaciones_finally()|ERROR:" + ex.toString());
            }
        }

        return resultado;
    }

}
