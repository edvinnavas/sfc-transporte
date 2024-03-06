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
import java.util.Calendar;
import java.util.List;

public class Ctrl_SEC_MOVIL implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String user_sec_movil;
    private final String pass_sec_movil;

    public Ctrl_SEC_MOVIL(String user_sec_movil, String pass_sec_movil) {
        this.user_sec_movil = user_sec_movil;
        this.pass_sec_movil = pass_sec_movil;
    }

    public String fleet_status() {
        String resultado = "";

        Connection conn = null;

        try {
            ClienteRest.Cliente_Rest_SEC_MOVIL cliente_rest_sec_movil = new ClienteRest.Cliente_Rest_SEC_MOVIL(user_sec_movil, pass_sec_movil);
            String response_fleet_status = cliente_rest_sec_movil.fleet_status();

            Type respuesta_fleet_status_type = new TypeToken<List<Entidad.SEC_MOVIL.fleet_status>>() {
            }.getType();
            List<Entidad.SEC_MOVIL.fleet_status> respuesta_fleet_status = new Gson().fromJson(response_fleet_status, respuesta_fleet_status_type);

            Control_Base_Datos control_base_datos = new Control_Base_Datos();
            conn = control_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            /* String sql = "DELETE FROM SEC_MOVIL_DETALLE WHERE ID_SEC_MOVIL > 0";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close(); */

            Long ID_SEC_MOVIL = control_base_datos.ObtenerLong("SELECT IFNULL(MAX(A.ID_SEC_MOVIL),0)+1 MAX_ID FROM SEC_MOVIL_ENCABEZADO A", conn);

            String sql = "INSERT INTO SEC_MOVIL_ENCABEZADO (ID_SEC_MOVIL, FECHA_ACTUALIZACION, NUMERO_UBICACIONES) VALUES ("
                    + ID_SEC_MOVIL + ","
                    + "CURRENT_TIMESTAMP" + ","
                    + "0" + ")";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            Integer CORRELATIVO = 0;
            for (Integer i = 0; i < respuesta_fleet_status.size(); i++) {
                CORRELATIVO++;
                Calendar fecha_registro_ws = Calendar.getInstance();
                SimpleDateFormat dateFormat_db = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                sql = "INSERT INTO SEC_MOVIL_DETALLE ("
                        + "ID_SEC_MOVIL, "
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
                        + ID_SEC_MOVIL + ","
                        + CORRELATIVO + ",'"
                        + respuesta_fleet_status.get(i).getVehDescr() + "','"
                        + respuesta_fleet_status.get(i).getVehId() + "','"
                        + respuesta_fleet_status.get(i).getOdometer() + "','"
                        + respuesta_fleet_status.get(i).getY() + "','"
                        + respuesta_fleet_status.get(i).getX() + "','"
                        + dateFormat_db.format(fecha_registro_ws.getTime()) + "','"
                        + respuesta_fleet_status.get(i).getSpeed() + "','"
                        + respuesta_fleet_status.get(i).getEventDescription() + "','"
                        + respuesta_fleet_status.get(i).getHeading() + "','"
                        + respuesta_fleet_status.get(i).getPlace() + "','"
                        + respuesta_fleet_status.get(i).getDriverId() + "','"
                        + respuesta_fleet_status.get(i).getDriverId() + "','"
                        + respuesta_fleet_status.get(i).getIgnOn() + "','"
                        + dateFormat_db.format(fecha_registro_ws.getTime()) + "','"
                        + respuesta_fleet_status.get(i).getPlace() + "',"
                        + "CURRENT_TIMESTAMP" + ")";
                stmt = conn.createStatement();
                // System.out.println("SQL: " + sql);
                stmt.executeUpdate(sql);
                stmt.close();
            }

            sql = "UPDATE SEC_MOVIL_ENCABEZADO SET NUMERO_UBICACIONES=" + CORRELATIVO + " WHERE ID_SEC_MOVIL=" + ID_SEC_MOVIL;
            stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            sql = "SELECT " 
                    + "STR_TO_DATE(A.DATETIME_UBICACION, '%d-%m-%Y %H:%i:%s') FECHA_HORA_UBICACION, " 
                    + "A.IMEI IMEI, " 
                    + "A.LATITUDE LATITUD_UBICACION, " 
                    + "A.LONGITUDE LONGITUD_UBICACION, " 
                    + "A.LOCATIONDESCRIPTION DESCRIPCION_UBICACION " 
                    + "FROM "
                    + "SEC_MOVIL_DETALLE A";
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
                        + "(V.ID_TRANSPORTISTA IN (59, 102)) AND "
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
                        sql = "INSERT INTO VIAJE_UBICACIONES_SEC_MOVIL ("
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
                        // System.out.println("SEC-MOVIL: UBICACION YA EXISTE." + ex.toString());
                    }

                    // this.validar_viajes_cerrados(ID_PAIS, ID_COMPANIA, ID_PLANTA, NUMERO_VIAJE, TIPO_ORDEN_VENTA, NUMERO_ORDEN_VENTA, ID_CLIENTE_DESTINO, conn);

                    sql = "DELETE FROM SEC_MOVIL_DETALLE WHERE "
                            + "STR_TO_DATE(DATETIME_UBICACION, '%d-%m-%Y %H:%i:%s')='" + GPS_FECHA_HORA_UBICACION + "' AND " 
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
            resultado = gson.toJson(respuesta_fleet_status);
        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;

                    resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:fleet_status()|ERROR:" + ex.toString();
                    System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:fleet_status()|ERROR:" + ex.toString());
                }
            } catch (Exception ex1) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:fleet_status_rollback()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:fleet_status_rollback()|ERROR:" + ex.toString());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:fleet_status_finally()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:fleet_status_finally()|ERROR:" + ex.toString());
            }
        }

        return resultado;
    }

}
