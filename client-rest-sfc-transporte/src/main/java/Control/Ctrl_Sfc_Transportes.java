package Control;

import ClienteRest.Cliente_Rest_SFC_JDE;
import Entidad.Respuesta_WS_Viaje;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class Ctrl_Sfc_Transportes implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String user_rest_sfc;
    private final String pass_rest_sfc;

    public Ctrl_Sfc_Transportes(String user_rest_sfc, String pass_rest_sfc) {
        this.user_rest_sfc = user_rest_sfc;
        this.pass_rest_sfc = pass_rest_sfc;
    }

    public String obtner_viajes(String fecha) {
        String resultado = "";
        
        Connection conn = null;

        try {
            Control_Base_Datos control_base_datos = new Control_Base_Datos();
            conn = control_base_datos.obtener_conexion_mysql();
            
            conn.setAutoCommit(false);
            
            Cliente_Rest_SFC_JDE cliente_rest_sfc_jde = new Cliente_Rest_SFC_JDE(user_rest_sfc, pass_rest_sfc);
            String response_obtener_viajes = cliente_rest_sfc_jde.obtener_viajes(fecha);

            Type respuesta_ws_viaje_type = new TypeToken<Respuesta_WS_Viaje>() {
            }.getType();
            Respuesta_WS_Viaje respuesta_sfc_transportes = new Gson().fromJson(response_obtener_viajes, respuesta_ws_viaje_type);
            
            for (Integer i = 0; i < respuesta_sfc_transportes.getLista_viajes().size(); i++) {
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                
                String cadenasql = "INSERT INTO VIAJES ("
                        + "CODIGO_PAIS, "
                        + "NOMBRE_PAIS, "
                        + "CODIGO_COMPANIA, "
                        + "NOMBRE_COMPANIA, "
                        + "CODIGO_PLANTA, "
                        + "NOMBRE_PLANTA, "
                        + "NUMERO_VIAJE, "
                        + "FECHA_VIAJE, "
                        + "CODIGO_ESTADO_VIAJE, "
                        + "NOMBRE_ESTADO_VIAJE, "
                        + "VEHICULO, "
                        + "PLACA_VEHICULO, "
                        + "CODIGO_TRANSPORTISTA, "
                        + "NOMBRE_TRANSPORTISTA, "
                        + "TIPO_ORDEN_VENTA, "
                        + "NUMERO_ORDEN_VENTA, "
                        + "CODIGO_CLIENTE, "
                        + "NOMBRE_CLIENTE, "
                        + "CODIGO_CLIENTE_DESTINO, "
                        + "NOMBRE_CLIENTE_DESTINO) VALUES ('"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_PAIS() + "','"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_PAIS() + "','"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_COMPANIA() + "','"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_COMPANIA() + "',"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_PLANTA() + ",'"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_PLANTA() + "',"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getNUMERO_VIAJE() + ",'"
                        + dateFormat2.format(dateFormat1.parse(respuesta_sfc_transportes.getLista_viajes().get(i).getFECHA_VIAJE())) + "',"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_ESTADO_VIAJE() + ",'"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_ESTADO_VIAJE() + "','"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getVEHICULO() + "','"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getPLACA_VEHICULO() + "',"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_TRANSPORTISTA() + ",'"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_TRANSPORTISTA() + "','"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getTIPO_ORDEN_VENTA() + "',"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getNUMERO_ORDEN_VENTA() + ","
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_CLIENTE() + ",'"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_CLIENTE() + "',"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_CLIENTE_DESTINO() + ",'"
                        + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_CLIENTE_DESTINO() + "')";
                Statement stmt = conn.createStatement();
                System.out.println(cadenasql);
                stmt.executeUpdate(cadenasql);
                stmt.close();
            }
            
            conn.commit();
            conn.setAutoCommit(true);
            
            resultado = "VIAJES ACTUALIZADOS EN TRANSPORTE-GPS.";
        } catch (Exception ex) {
            try {
                if(conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;
                    
                    resultado = "PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:obtner_viajes()|ERROR:" + ex.toString();
                    System.out.println("PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:obtner_viajes()|ERROR:" + ex.toString());
                }
            } catch (Exception ex1) {
                resultado = "PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:obtner_viajes_rollback()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:obtner_viajes_rollback()|ERROR:" + ex.toString());
            }
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:obtner_viajes_finally()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:obtner_viajes_finally()|ERROR:" + ex.toString());
            }
        }

        return resultado;
    }

}
