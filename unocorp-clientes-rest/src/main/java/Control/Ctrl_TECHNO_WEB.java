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
import java.util.List;

public class Ctrl_TECHNO_WEB implements Serializable {

    private static final long serialVersionUID = 1L;

    public Ctrl_TECHNO_WEB() {
        
    }

    public String svc() {
        String resultado = "";

        Connection conn = null;

        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            SimpleDateFormat dateFormat_ws = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            
            Calendar date_ini = Calendar.getInstance();
            date_ini.add(Calendar.MINUTE, -30);
            
            Calendar date_end = Calendar.getInstance();

            Entidad.TECHNO_WEB.Request_Json_Svc request_json_Svc = new Entidad.TECHNO_WEB.Request_Json_Svc();
            request_json_Svc.setPassword("asista2022!");
            request_json_Svc.setDateIni(dateFormat_ws.format(date_ini.getTime()) + "Z");
            request_json_Svc.setDateEnd(dateFormat_ws.format(date_end.getTime()) + "Z");

            ClienteRest.Cliente_Rest_TECHNO_WEB cliente_rest_techno_web = new ClienteRest.Cliente_Rest_TECHNO_WEB();
            String response_svc = cliente_rest_techno_web.svc(gson.toJson(request_json_Svc));

            Type respuesta_svc_type = new TypeToken<Entidad.TECHNO_WEB.Response_Json_Svc>() {
            }.getType();
            Entidad.TECHNO_WEB.Response_Json_Svc respuesta_svc = new Gson().fromJson(response_svc, respuesta_svc_type);

            List<Entidad.TECHNO_WEB.Values> lst_values_temp = new ArrayList<>();
            for (Integer i = 0; i < respuesta_svc.getValues().size(); i++) {
                
                Calendar fecha_consulta_inicial = Calendar.getInstance();
                fecha_consulta_inicial.add(Calendar.HOUR, -1);

                Calendar fecha_consulta_final = Calendar.getInstance();
                fecha_consulta_final.add(Calendar.HOUR, 1);

                Calendar fecha_registro_ws = Calendar.getInstance();
                SimpleDateFormat dateFormat_techno = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                fecha_registro_ws.setTime(dateFormat_techno.parse(respuesta_svc.getValues().get(i).getDateTime()));

                SimpleDateFormat dateFormat_temp = new SimpleDateFormat("yyyyMMddHHmmss");

                Long fechaInicial = Long.valueOf(dateFormat_temp.format(fecha_consulta_inicial.getTime()));
                Long fechaFinal = Long.valueOf(dateFormat_temp.format(fecha_consulta_final.getTime()));
                Long fechaRegistro = Long.valueOf(dateFormat_temp.format(fecha_registro_ws.getTime()));

                if((fechaRegistro >= fechaInicial) && (fechaRegistro <= fechaFinal)) {
                    Entidad.TECHNO_WEB.Values values = respuesta_svc.getValues().get(i);
                    lst_values_temp.add(values);
                }
            }

            List<Entidad.TECHNO_WEB.Values> lst_values_temp_2 = new ArrayList<>();
            for (Integer i = 0; i < lst_values_temp.size(); i++) {
                Boolean existe = false;
                for(Integer j = 0; j > lst_values_temp_2.size(); j++) {
                    if(lst_values_temp.get(i).getImei().equals(lst_values_temp_2.get(j).getImei()) && lst_values_temp.get(i).getDateTime().equals(lst_values_temp_2.get(j).getDateTime()) && lst_values_temp.get(i).getLatitude().equals(lst_values_temp_2.get(j).getLatitude()) && lst_values_temp.get(i).getLongitude().equals(lst_values_temp_2.get(j).getLongitude())) {
                        existe = true;
                    }
                }
                if(!existe) {
                    lst_values_temp_2.add(lst_values_temp.get(i));
                }
            }

            respuesta_svc.setValues(lst_values_temp_2);

            Control_Base_Datos control_base_datos = new Control_Base_Datos();
            conn = control_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            /* String sql = "DELETE FROM TECHNO_WEB_DETALLE WHERE ID_TECHNO_WEB > 0";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close(); */

            Long ID_TECHNO_WEB = control_base_datos.ObtenerLong("SELECT IFNULL(MAX(A.ID_TECHNO_WEB),0)+1 MAX_ID FROM TECHNO_WEB_ENCABEZADO A", conn);

            String sql = "INSERT INTO TECHNO_WEB_ENCABEZADO (ID_TECHNO_WEB, FECHA_ACTUALIZACION, NUMERO_UBICACIONES) VALUES ("
                    + ID_TECHNO_WEB + ","
                    + "CURRENT_TIMESTAMP" + ","
                    + "0" + ")";
            Statement stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            Integer CORRELATIVO = 0;
            for (Integer i = 0; i < respuesta_svc.getValues().size(); i++) {
                String date_time = respuesta_svc.getValues().get(i).getDateTime();
                date_time = date_time.replaceAll("T", " ");
                date_time = date_time.replaceAll("Z", "");

                CORRELATIVO++;
                sql = "INSERT INTO TECHNO_WEB_DETALLE ("
                        + "ID_TECHNO_WEB, "
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
                        + ID_TECHNO_WEB + ","
                        + CORRELATIVO + ",'"
                        + respuesta_svc.getValues().get(i).getName() + "','"
                        + respuesta_svc.getValues().get(i).getImei() + "','"
                        + respuesta_svc.getValues().get(i).getOdometer() + "','"
                        + respuesta_svc.getValues().get(i).getLatitude() + "','"
                        + respuesta_svc.getValues().get(i).getLongitude() + "','"
                        + date_time + "','"
                        + respuesta_svc.getValues().get(i).getSpeedAVG() + "','"
                        + respuesta_svc.getValues().get(i).getSpeedMeasure() + "','"
                        + "SIN DESCRIPCION" + "','"
                        + "SIN DESCRIPCION" + "','"
                        + respuesta_svc.getValues().get(i).getLicensePlate() + "','"
                        + respuesta_svc.getValues().get(i).getLicensePlate() + "','"
                        + respuesta_svc.getValues().get(i).getEngineStatus() + "','"
                        + date_time + "','"
                        + "SIN DESCRIPCION" + "',"
                        + "CURRENT_TIMESTAMP" + ")";
                stmt = conn.createStatement();
                // System.out.println("SQL: " + sql);
                stmt.executeUpdate(sql);
                stmt.close();
            }

            sql = "UPDATE TECHNO_WEB_ENCABEZADO SET NUMERO_UBICACIONES=" + CORRELATIVO + " WHERE ID_TECHNO_WEB=" + ID_TECHNO_WEB;
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
                    + "TECHNO_WEB_DETALLE A";
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
                        + "(V.ID_TRANSPORTISTA IN (21, 272)) AND "
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
                        sql = "INSERT INTO VIAJE_UBICACIONES_TECHNO_WEB ("
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
                        // System.out.println("TECHNO_WEB: UBICACION YA EXISTE." + ex.toString());
                    }

                    // this.validar_viajes_cerrados(ID_PAIS, ID_COMPANIA, ID_PLANTA, NUMERO_VIAJE, TIPO_ORDEN_VENTA, NUMERO_ORDEN_VENTA, ID_CLIENTE_DESTINO, conn);

                    sql = "DELETE FROM TECHNO_WEB_DETALLE WHERE "
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

            resultado = gson.toJson(respuesta_svc);
        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;

                    resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:svc()|ERROR:" + ex.toString();
                    System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:svc()|ERROR:" + ex.toString());
                }
            } catch (Exception ex1) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:svc_rollback()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:svc_rollback()|ERROR:" + ex.toString());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:svc_finally()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:svc_finally()|ERROR:" + ex.toString());
            }
        }

        return resultado;
    }

}
