package Control;

import ClienteRest.Cliente_Rest_SFC_JDE;
import Entidad.Respuesta_WS_Viaje;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Objects;

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
                
                Long ID_PAIS = Long.valueOf("0");
                String cadenasql = "SELECT P.ID_PAIS FROM PAIS P WHERE P.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_PAIS() + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    ID_PAIS = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_PAIS, Long.valueOf("0"))) {
                    cadenasql = "SELECT IFNULL(MAX(P.ID_PAIS),0) FROM PAIS P";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while(rs.next()) {
                        ID_PAIS = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_PAIS++;
                    cadenasql = "INSERT INTO PAIS (ID_PAIS, CODIGO, NOMBRE) VALUES ("
                            + ID_PAIS + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_PAIS() + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_PAIS().replaceAll("'", "") + "')";
                    stmt = conn.createStatement();
                    System.out.println(cadenasql);
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
                
                Long ID_COMPANIA = Long.valueOf("0");
                cadenasql = "SELECT C.ID_COMPANIA FROM COMPANIA C WHERE C.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_COMPANIA() + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    ID_COMPANIA = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_COMPANIA, Long.valueOf("0"))) {
                    cadenasql = "SELECT IFNULL(MAX(C.ID_COMPANIA),0) FROM COMPANIA C";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while(rs.next()) {
                        ID_COMPANIA = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_COMPANIA++;
                    cadenasql = "INSERT INTO COMPANIA (ID_COMPANIA, CODIGO, NOMBRE, ID_PAIS) VALUES ("
                            + ID_COMPANIA + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_COMPANIA() + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_COMPANIA().replaceAll("'", "") + "',"
                            + ID_PAIS + ")";
                    stmt = conn.createStatement();
                    System.out.println(cadenasql);
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
                
                Long ID_PLANTA = Long.valueOf("0");
                cadenasql = "SELECT P.ID_PLANTA FROM PLANTA P WHERE P.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_PLANTA() + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    ID_PLANTA = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_PLANTA, Long.valueOf("0"))) {
                    cadenasql = "SELECT IFNULL(MAX(P.ID_PLANTA),0) FROM PLANTA P";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while(rs.next()) {
                        ID_PLANTA = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_PLANTA++;
                    cadenasql = "INSERT INTO PLANTA (ID_PLANTA, CODIGO, NOMBRE, ID_COMPANIA) VALUES ("
                            + ID_PLANTA + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_PLANTA() + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_PLANTA().replaceAll("'", "") + "',"
                            + ID_COMPANIA + ")";
                    stmt = conn.createStatement();
                    System.out.println(cadenasql);
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
                
                Long ID_ESTADO_VIAJE = Long.valueOf("0");
                cadenasql = "SELECT EV.ID_ESTADO_VIAJE FROM ESTADO_VIAJE EV WHERE EV.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_ESTADO_VIAJE() + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    ID_ESTADO_VIAJE = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_ESTADO_VIAJE, Long.valueOf("0"))) {
                    cadenasql = "SELECT IFNULL(MAX(EV.ID_ESTADO_VIAJE),0) FROM ESTADO_VIAJE EV";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while(rs.next()) {
                        ID_ESTADO_VIAJE = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_ESTADO_VIAJE++;
                    cadenasql = "INSERT INTO ESTADO_VIAJE (ID_ESTADO_VIAJE, CODIGO, NOMBRE) VALUES ("
                            + ID_ESTADO_VIAJE + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_ESTADO_VIAJE() + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_ESTADO_VIAJE().replaceAll("'", "") + "')";
                    stmt = conn.createStatement();
                    System.out.println(cadenasql);
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
                
                Long ID_TRANSPORTISTA = Long.valueOf("0");
                cadenasql = "SELECT T.ID_TRANSPORTISTA FROM TRANSPORTISTA T WHERE T.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_TRANSPORTISTA() + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    ID_TRANSPORTISTA = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_TRANSPORTISTA, Long.valueOf("0"))) {
                    cadenasql = "SELECT IFNULL(MAX(T.ID_TRANSPORTISTA),0) FROM TRANSPORTISTA T";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while(rs.next()) {
                        ID_TRANSPORTISTA = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_TRANSPORTISTA++;
                    cadenasql = "INSERT INTO TRANSPORTISTA (ID_TRANSPORTISTA, CODIGO, NOMBRE, ID_PAIS) VALUES ("
                            + ID_TRANSPORTISTA + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_TRANSPORTISTA() + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_TRANSPORTISTA().replaceAll("'", "") + "',"
                            + ID_PAIS + ")";
                    stmt = conn.createStatement();
                    System.out.println(cadenasql);
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
                
                Long ID_VEHICULO = Long.valueOf("0");
                cadenasql = "SELECT V.ID_VEHICULO FROM VEHICULO V WHERE V.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getVEHICULO().replaceAll("'", "") + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    ID_VEHICULO = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_VEHICULO, Long.valueOf("0"))) {
                    cadenasql = "SELECT IFNULL(MAX(V.ID_VEHICULO),0) FROM VEHICULO V";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while(rs.next()) {
                        ID_VEHICULO = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_VEHICULO++;
                    cadenasql = "INSERT INTO VEHICULO (ID_VEHICULO, CODIGO, PLACA, ID_TRANSPORTISTA) VALUES ("
                            + ID_VEHICULO + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getVEHICULO().replaceAll("'", "") + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getPLACA_VEHICULO().replaceAll("'", "") + "',"
                            + ID_TRANSPORTISTA + ")";
                    stmt = conn.createStatement();
                    System.out.println(cadenasql);
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
                
                Long ID_CLIENTE = Long.valueOf("0");
                cadenasql = "SELECT C.ID_CLIENTE FROM CLIENTE C WHERE C.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_CLIENTE() + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    ID_CLIENTE = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_CLIENTE, Long.valueOf("0"))) {
                    cadenasql = "SELECT IFNULL(MAX(C.ID_CLIENTE),0) FROM CLIENTE C";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while(rs.next()) {
                        ID_CLIENTE = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_CLIENTE++;
                    cadenasql = "INSERT INTO CLIENTE (ID_CLIENTE, CODIGO, NOMBRE) VALUES ("
                            + ID_CLIENTE + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_CLIENTE() + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_CLIENTE().replaceAll("'", "") + "')";
                    stmt = conn.createStatement();
                    System.out.println(cadenasql);
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
                
                Long ID_CLIENTE_DESTINO = Long.valueOf("0");
                cadenasql = "SELECT CD.ID_CLIENTE_DESTINO FROM CLIENTE_DESTINO CD WHERE CD.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_CLIENTE_DESTINO() + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    ID_CLIENTE_DESTINO = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_CLIENTE_DESTINO, Long.valueOf("0"))) {
                    cadenasql = "SELECT IFNULL(MAX(CD.ID_CLIENTE_DESTINO),0) FROM CLIENTE_DESTINO CD";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(cadenasql);
                    while(rs.next()) {
                        ID_CLIENTE_DESTINO = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_CLIENTE_DESTINO++;
                    cadenasql = "INSERT INTO CLIENTE_DESTINO (ID_CLIENTE_DESTINO, CODIGO, NOMBRE, ID_CLIENTE) VALUES ("
                            + ID_CLIENTE_DESTINO + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_CLIENTE_DESTINO() + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_CLIENTE_DESTINO().replaceAll("'", "") + "',"
                            + ID_CLIENTE + ")";
                    stmt = conn.createStatement();
                    System.out.println(cadenasql);
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
                
                Boolean exite_viaje = false;
                cadenasql = "SELECT "
                        + "V.ID_PLANTA, "
                        + "V.NUMERO_VIAJE "
                        + "FROM VIAJES V "
                        + "WHERE "
                        + "V.ID_PAIS=" + ID_PAIS + " AND "
                        + "V.ID_COMPANIA=" + ID_COMPANIA + " AND "
                        + "V.ID_PLANTA=" + ID_PLANTA + " AND "
                        + "V.NUMERO_VIAJE=" + respuesta_sfc_transportes.getLista_viajes().get(i).getNUMERO_VIAJE() + " AND "
                        + "V.TIPO_ORDEN_VENTA='" + respuesta_sfc_transportes.getLista_viajes().get(i).getTIPO_ORDEN_VENTA() + "' AND "
                        + "V.NUMERO_ORDEN_VENTA=" + respuesta_sfc_transportes.getLista_viajes().get(i).getNUMERO_ORDEN_VENTA();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(cadenasql);
                while(rs.next()) {
                    exite_viaje = true;
                }
                rs.close();
                stmt.close();
                
                if (exite_viaje) {
                    cadenasql = "UPDATE VIAJES SET "
                            + "ID_ESTADO_VIAJE=" + ID_ESTADO_VIAJE + " "
                            + "WHERE "
                            + "ID_PAIS=" + ID_PAIS + " AND "
                            + "ID_COMPANIA=" + ID_COMPANIA + " AND "
                            + "CODIGO_PLANTA=" + ID_PLANTA + " AND "
                            + "NUMERO_VIAJE=" + respuesta_sfc_transportes.getLista_viajes().get(i).getNUMERO_VIAJE() + " AND "
                            + "TIPO_ORDEN_VENTA='" + respuesta_sfc_transportes.getLista_viajes().get(i).getTIPO_ORDEN_VENTA() + "' AND "
                            + "NUMERO_ORDEN_VENTA=" + respuesta_sfc_transportes.getLista_viajes().get(i).getNUMERO_ORDEN_VENTA();
                    stmt = conn.createStatement();
                    System.out.println(cadenasql);
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                } else {
                    cadenasql = "INSERT INTO VIAJES ("
                            + "ID_PAIS, "
                            + "ID_COMPANIA, "
                            + "ID_PLANTA, "
                            + "NUMERO_VIAJE, "
                            + "FECHA_VIAJE, "
                            + "ID_ESTADO_VIAJE, "
                            + "ID_VEHICULO, "                            
                            + "ID_TRANSPORTISTA, "
                            + "TIPO_ORDEN_VENTA, "
                            + "NUMERO_ORDEN_VENTA, "
                            + "ID_CLIENTE, "
                            + "ID_CLIENTE_DESTINO, "
                            + "TIPO_FLETE_VIAJE) VALUES ("
                            + ID_PAIS + ","
                            + ID_COMPANIA + ","
                            + ID_PLANTA + ","
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNUMERO_VIAJE() + ",'"
                            + dateFormat2.format(dateFormat1.parse(respuesta_sfc_transportes.getLista_viajes().get(i).getFECHA_VIAJE())) + "',"
                            + ID_ESTADO_VIAJE + ","
                            + ID_VEHICULO + ","
                            + ID_TRANSPORTISTA + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getTIPO_ORDEN_VENTA() + "',"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNUMERO_ORDEN_VENTA() + ","
                            + ID_CLIENTE + ","
                            + ID_CLIENTE_DESTINO + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getTIPO_FLETE_VIAJE() + "')";
                    stmt = conn.createStatement();
                    System.out.println(cadenasql);
                    stmt.executeUpdate(cadenasql);
                    stmt.close();
                }
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
